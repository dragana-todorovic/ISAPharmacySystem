package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservation;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservationStatus;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MedicineReservationDTO;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineReservationRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineWithQuantityRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineService;
import rs.ac.uns.ftn.informatika.spring.security.service.PatientService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.view.MedicineReservationView;

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
		System.out.println(p.getMedicineWithQuantity());

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
	public void saveReservation(MedicineReservationDTO medicineReservationDto) {

		 User user = this.userService.findByUsername(medicineReservationDto.getPatientEmail());
		 MedicineWithQuantity med = new MedicineWithQuantity();
		 
		 Medicine medicine=findById(medicineReservationDto.getMedicineId());
		 
		 med.setMedicine(medicine);
		 med.setQuantity(medicineReservationDto.getQuantity());
		 
		 LocalDate localDueToDate = LocalDate.parse(medicineReservationDto.getDueTo());
		 
		 MedicineReservation mR=new MedicineReservation();
		 mR.setDueTo(localDueToDate);
		 mR.setMedicineWithQuantity(med);
		 mR.setPatient(patientService.findPatientByUser(user));
		 mR.setStatus(MedicineReservationStatus.RESERVED);
		 
		 Pharmacy pharmacy=pharmacyRepository.findPharmacyById(medicineReservationDto.getPharmacyId());
		 pharmacy.getMedicineReservations().add(mR);
		 this.pharmacyRepository.save(pharmacy);
		
	
		

	public Optional<Medicine> findById(Long id) {
		// TODO Auto-generated method stub
		return medicineRepository.findById(id);

	}

}
