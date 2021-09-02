package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservation;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservationStatus;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MedicineReservationDTO;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineReservationRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineWithQuantityRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineReservationService;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineService;
import rs.ac.uns.ftn.informatika.spring.security.service.PatientService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
@Service
public class MedicineReservationServiceImpl implements MedicineReservationService {
	@Autowired
	private UserService userService;
	
	@Autowired
	private MedicineService medicineService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private MedicineReservationRepository medicineReservationRepository;
	
	@Autowired
	private MedicineWithQuantityRepository medicineWithQuantityRepository;
	
	@Override
	public MedicineReservation saveReservation(MedicineReservationDTO medicineReservationDto) {
		 UUID numberOfReservation=UUID.randomUUID();
		 User user = this.userService.findByUsername(medicineReservationDto.getPatientEmail());
		 MedicineWithQuantity med = new MedicineWithQuantity();
		 
		 Medicine medicine=this.medicineService.findById(medicineReservationDto.getMedicineId());
		 
		 med.setMedicine(medicine);
		 med.setQuantity(medicineReservationDto.getQuantity());		
		 LocalDate localDueToDate = LocalDate.parse(medicineReservationDto.getDueTo().split("T")[0]);
		 LocalTime localDueToTime=LocalTime.parse(medicineReservationDto.getDueTo().split("T")[1]);
		 
		 MedicineReservation mR=new MedicineReservation();
		 mR.setDueTo(localDueToDate);
		 mR.setMedicineWithQuantity(med);
		 mR.setPatient(patientService.findPatientByUser(user));
		 mR.setStatus(MedicineReservationStatus.RESERVED);
		 mR.setDueToTime(localDueToTime);
		 mR.setVersion(1L);
		 
		 for(MedicineReservation reservation : medicineReservationRepository.findAll()) {
			 if(numberOfReservation==reservation.getNumberOfReservation()) {
				 return null;
			 }
		 }
		 
		 mR.setNumberOfReservation(numberOfReservation);
		 
		 Pharmacy pharmacy=pharmacyRepository.findPharmacyById(medicineReservationDto.getPharmacyId());
			for (MedicineWithQuantity m : pharmacy.getMedicineWithQuantity()) {
				if (medicine.getId() == m.getMedicine().getId()) {	
					if(m.getQuantity()<medicineReservationDto.getQuantity()) {
						return null;
					}
					else{
					m.setQuantity(m.getQuantity() - medicineReservationDto.getQuantity());
					this.medicineWithQuantityRepository.save(m);
					break;
					}
				}
			}
		 
		 pharmacy.getMedicineReservations().add(mR);
		 this.pharmacyRepository.save(pharmacy);
		 return mR;
	
	}

	@Override
	public void editReservation(MedicineReservation medicineReservation) {
		for(Pharmacy ph: pharmacyRepository.findAll()) {
			for(MedicineReservation res:ph.getMedicineReservations()) {
				if(res.getId()==medicineReservation.getId()) {
						for (MedicineWithQuantity m : ph.getMedicineWithQuantity()) {
							if (medicineReservation.getMedicineWithQuantity().getMedicine().getId() == m.getMedicine().getId()) {			
								m.setQuantity(m.getQuantity() + medicineReservation.getMedicineWithQuantity().getQuantity());
								this.medicineWithQuantityRepository.save(m);
								break;
							}
						}
				}
			}
		}
		medicineReservation.setStatus(MedicineReservationStatus.CANCELLED);
		this.medicineReservationRepository.save(medicineReservation);
	}

	@Override
	public MedicineReservation findById(Long id) {
		return this.medicineReservationRepository.getMedicineReservationById(id);
	}

}
