package rs.ac.uns.ftn.informatika.spring.security.service;

import java.time.LocalDateTime;
import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacist;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounseling;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.CounselingDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.PharamcistForCounselingView;
import rs.ac.uns.ftn.informatika.spring.security.view.PharmacyForCounselingView;

public interface PharmacistCounselingService {
	List<PharmacistCounseling> findById(Long id);
	List<PharmacistCounseling> findAll ();
	List<PharmacistCounseling> findByPatientId(Long id);
	void saveAppointment(CounselingDTO appointmentDTO,Patient patient, LocalDateTime startDateTime);
	List<PharmacyForCounselingView> getPharamciesWithAvailablePharmacists(String term);
	List<PharamcistForCounselingView> getAvailablePharmacistsByPharmacy(Long id, String term);
}
