package rs.ac.uns.ftn.informatika.spring.security.service;

import java.time.LocalDateTime;
import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounseling;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.CounselingDTO;

public interface PharmacistCounselingService {
	List<PharmacistCounseling> findById(Long id);
	List<PharmacistCounseling> findAll ();
	List<PharmacistCounseling> findByPatientId(Long id);
	void saveAppointment(CounselingDTO appointmentDTO,Patient patient, LocalDateTime startDateTime);
}
