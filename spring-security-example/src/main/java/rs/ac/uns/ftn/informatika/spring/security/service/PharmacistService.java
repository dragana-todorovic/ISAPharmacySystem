package rs.ac.uns.ftn.informatika.spring.security.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacist;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.AppointmentDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.CounselingDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.HolidayRequestDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MyPatientDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.WorkCalendarDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservation;

public interface PharmacistService {
	void saveHolidayRequest(HolidayRequestDTO holidayRequest);
	List<Pharmacist> findAll ();
	List<MyPatientDTO> myPatients(String email);
	List<MyPatientDTO> getPatientsForAppointment(String email);
	List<MedicineReservation>searchReservedMedicnes(String resNumber,Pharmacy pharmacy);
	List<Medicine> getMedicines();
	Boolean isMedicineAvailable(Pharmacy pharmacy, String medicineId);
	void saveAppointment(AppointmentDTO appointmantDTO);
	void saveAppointment(AppointmentDTO appointmantDTO,Pharmacy pharmacy);
	List<WorkCalendarDTO> getPharmacistsCounseling(Pharmacist pharmacist);
	Boolean isAppointmentAvailableForScheduling(Pharmacist pharmacist,Patient patient,Integer duration,Pharmacy pharmacy,LocalDate startDate, LocalDateTime startDateTime,LocalDateTime endDateTime); 
	double getAvrageGrade(Pharmacist pharmacist);
	Pharmacist getPharmacistsById(Long id);
}
