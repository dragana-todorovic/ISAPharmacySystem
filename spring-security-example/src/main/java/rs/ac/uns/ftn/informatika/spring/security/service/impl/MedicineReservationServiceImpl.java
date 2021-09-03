package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservation;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservationStatus;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
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
	public MedicineReservation saveReservation(MedicineReservationDTO medicineReservationDto,Long v) {
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
		 mR.setIsPenalGiven(false);
		 mR.setVersion(1L);

		 
		 for(MedicineReservation reservation : medicineReservationRepository.findAll()) {
			 if(numberOfReservation==reservation.getNumberOfReservation()) {
				 return null;
			 }
		 }
		 
		 mR.setNumberOfReservation(numberOfReservation);
		 
		 Pharmacy pharmacy=pharmacyRepository.findPharmacyById(medicineReservationDto.getPharmacyId());
			for (MedicineWithQuantity m : pharmacy.getMedicineWithQuantity()) {
				if(m.getVersion() != v) {
					throw new ObjectOptimisticLockingFailureException("Versions don't match", MedicineWithQuantity.class);
				}
				
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
	@Transactional(readOnly=false)
	public void editReservation(MedicineReservation medicineReservation) {
		for(Pharmacy ph: pharmacyRepository.findAll()) {
			for(MedicineReservation res:ph.getMedicineReservations()) {
				if(res.getId()==medicineReservation.getId()) {
							MedicineWithQuantity m = this.medicineWithQuantityRepository.findByMedicineId(medicineReservation.getMedicineWithQuantity().getMedicine().getId());
								m.setQuantity(m.getQuantity() + medicineReservation.getMedicineWithQuantity().getQuantity());
								this.medicineWithQuantityRepository.save(m);	
						
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

	@Override
	public List<MedicineReservation> getAll() {
		return this.medicineReservationRepository.findAll();
	}

	@Override
	public void saveReservation(MedicineReservation medicineReservation) {
		MedicineReservation mr = new MedicineReservation();
		mr.setIsPenalGiven(medicineReservation.getIsPenalGiven());
		mr.setVersion(medicineReservation.getVersion());
		mr.setStatus(medicineReservation.getStatus());
		mr.setPatient(medicineReservation.getPatient());
		mr.setNumberOfReservation(medicineReservation.getNumberOfReservation());
		mr.setMedicineWithQuantity(medicineReservation.getMedicineWithQuantity());
		mr.setDueToTime(medicineReservation.getDueToTime());
		mr.setDueTo(medicineReservation.getDueTo());
		mr.setId(medicineReservation.getId());
		this.medicineReservationRepository.save(mr);
		
	}

}
