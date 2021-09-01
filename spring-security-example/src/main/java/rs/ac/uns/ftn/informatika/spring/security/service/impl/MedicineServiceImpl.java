package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.EPrescription;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicinePrice;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservation;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservationStatus;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacist;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounseling;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.PriceList;
import rs.ac.uns.ftn.informatika.spring.security.model.Rating;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MedicineReservationDTO;
import rs.ac.uns.ftn.informatika.spring.security.repository.EPrescriptionRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineReservationRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineWithQuantityRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PriceListRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineService;
import rs.ac.uns.ftn.informatika.spring.security.service.PatientService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.view.MedicineReservationView;
import rs.ac.uns.ftn.informatika.spring.security.view.RatingView;

@Service
public class MedicineServiceImpl implements MedicineService{

	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	@Autowired
	private MedicineRepository medicineRepository;
	@Autowired
	private MedicineReservationRepository medicineReservationRepository;
	@Autowired
	private MedicineWithQuantityRepository medicineWithQuantityRepository;
	
	@Autowired
	private PriceListRepository priceListRepository;
	
	@Autowired
	private EPrescriptionRepository ePrescriptionRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Override
	public List<Medicine> findAll() {
		List<Medicine> result = medicineRepository.findAll();
		return result;
	}

	@Override
	public Collection<Medicine> searchMedicine(String p) {
		   ArrayList<Medicine> medicines = new ArrayList<>();
		   for(Medicine medicine : medicineRepository.findAll()){
			   if(medicine.getName().toLowerCase().contains(p.toLowerCase())) {
				   medicines.add(medicine);
			   }
		   }
	        return medicines;
	}

	@Override
	public Medicine save(Medicine medicine) {
		Medicine medicineSaved = this.medicineRepository.save(medicine);
		return medicineSaved;
	}
	public Set<MedicineWithQuantity> getMedicinesByPharmacy(String email) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));

		Pharmacy p = pa.getPharmacy();

		return p.getMedicineWithQuantity();
	}
	public Medicine findByName(String name) {
		Medicine m = medicineRepository.findByName(name);
		return m;

	}

	@Override
	public Set<Medicine> getAllMedicinesExceptExisted(String email) {
		Set<MedicineWithQuantity> alreadyExisted = getMedicinesByPharmacy(email);
		List<Medicine> all = findAll();
		Set<Medicine> result = new HashSet<Medicine>();
		Set<Medicine> pomocna = new HashSet<Medicine>();
		
		for(MedicineWithQuantity mq : alreadyExisted) {
			pomocna.add(mq.getMedicine());
		}		
		
		for(Medicine m : all) {	
				if(!pomocna.contains(m)) {
					result.add(m);
				}
			}
		
		return result;
	}

	@Override
	public void addMedicineWithQuatityInPharmacy(String email, String medicineName, int quantity) {
		System.out.println("MEDICINE NAME"+ medicineName);
		System.out.println("MEDICINE QUANTITY"+ quantity);
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		
		Pharmacy pharmacy = pa.getPharmacy();
		
		Medicine medicine = medicineRepository.findfindByName(medicineName);
		
		MedicineWithQuantity medicineWithQuantity = new MedicineWithQuantity();
		medicineWithQuantity.setMedicine(medicine);
		medicineWithQuantity.setQuantity(quantity);

		
		pharmacy.getMedicineWithQuantity().add(medicineWithQuantity);
		
		this.pharmacyRepository.save(pharmacy);
		
	}

	@Override
	public Boolean deleteMedicineFromPharmacy(Long id, String email) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		
		Pharmacy pharmacy = pa.getPharmacy();
		Set<MedicineReservation> pharmacyMedicineReservations = pharmacy.getMedicineReservations();
		
		MedicineWithQuantity medicineWithQuantity = medicineWithQuantityRepository.getOne(id);
		List<MedicineReservation> medicineReservation = medicineReservationRepository.getMedicineReservationByMedicineWithQuantity(id);
		
		for(MedicineReservation mr : medicineReservation) {
			if(pharmacyMedicineReservations.contains(mr) && mr.getStatus().equals(MedicineReservationStatus.RESERVED)) {
				return false;
			}
		}
		
		
		pharmacy.getMedicineWithQuantity().remove(medicineWithQuantity);
		this.pharmacyRepository.save(pharmacy);
		return true;
		
		
	}

	@Override
	public Medicine findById(Long id) {
		return medicineRepository.findMedicineById(id);
	}

	@Override
	public Medicine findByCode(String code) {
		Medicine m = medicineRepository.findByCode(code);
		return m;
	}


	
	@Override
	public void editMedicineWithQuatityInPharmacy(String email, long id, int quantity) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		
		Pharmacy pharmacy = pa.getPharmacy();
		
	//	MedicineWithQuantity medicineWithQuantity = this.medicineWithQuantityRepository.findByMedicineId(id);
		MedicineWithQuantity medicineWithQuantity = this.medicineWithQuantityRepository.findById(id).get();

		for(MedicineWithQuantity mq : pharmacy.getMedicineWithQuantity()) {
			if(mq.equals(medicineWithQuantity)) {
				mq.setQuantity(quantity);
			}
		}
		
		this.pharmacyRepository.save(pharmacy);
		
	}

	@Override
	public Set<Medicine> getAllMedicinePricesExpectedExsitedInPriceList(String email,List<Long> existed, String priceListId) {
		PriceList priceList = this.priceListRepository.findById(Long.parseLong(priceListId)).get();
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		
		Pharmacy pharmacy = pa.getPharmacy();
		
		Set<Medicine> medicines = new HashSet<Medicine>();
		
		for(MedicineWithQuantity m : pharmacy.getMedicineWithQuantity()) {
			if(!existed.contains(m.getMedicine().getId())) {
				medicines.add(m.getMedicine());
			}
			}
		
		return medicines;
	
		
	}

	@Override
	public List<RatingView> getAllMedicinePatientCanEvaluate(long patient) {
		List<RatingView> result=new ArrayList<RatingView>();
		List<Medicine> pom=new ArrayList<Medicine>();
		for(EPrescription ep : ePrescriptionRepository.findAll()) {
			if(ep.getPatient()==null) {
				continue;
			}
			if(ep.getPatient().getId() == patient) {
				for(MedicineWithQuantity m : ep.getMedicines()) {
					if(!pom.contains(m.getMedicine())) {
						pom.add(m.getMedicine());
					}
				}
			}
		}
		for(MedicineReservation mr : medicineReservationRepository.findAll()) {
			if(mr.getPatient()==null) {
				continue;
			}
			if(mr.getPatient().getId() == patient) {
				if(mr.getStatus().equals(MedicineReservationStatus.TAKEN) && !pom.contains(mr.getMedicineWithQuantity().getMedicine())) {
					
					pom.add(mr.getMedicineWithQuantity().getMedicine());
					}
				}
			}
		for(Medicine d : pom) {
			System.out.println(d.getName());
			RatingView rdw=new RatingView(d.getId(),d.getName(),
					"");
						
		
			for(Rating ra :d.getRatings()){
				if(ra.getPatient() == patient) {
					rdw.setPatientsGrade(ra.getRating());
				} 
			}
			result.add(rdw);	
		}
		
		return result;
	}

	@Override
	public void changeRating(int rating, long patient_id, Long id) {
		Medicine med=medicineRepository.findMedicineById(id);
		Rating rat=new Rating();
		if(med.getRatings().isEmpty()) {
			rat.setPatient(patient_id);
			rat.setRating(rating);
			med.getRatings().add(rat);
		}
		for(Rating r : med.getRatings()) {
			if(r.getPatient() == (patient_id)) {
				r.setRating(rating);
			}
		}
		this.medicineRepository.save(med);
		
	}


}
