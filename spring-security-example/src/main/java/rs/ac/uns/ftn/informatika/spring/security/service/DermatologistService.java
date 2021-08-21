package rs.ac.uns.ftn.informatika.spring.security.service;

 
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.AppointmentDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.HolidayRequestDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MyPatientDTO;

public interface DermatologistService {
	void saveHolidayRequest(HolidayRequestDTO holidayRequest);
	List<Dermatologist> findAll ();
	List<MyPatientDTO> myPatients(String email);
	List<MyPatientDTO> getPatientsForAppointment(String email);
	List<Medicine> getMedicines();
	void saveAppointment(AppointmentDTO appointmantDTO);
	Dermatologist save(Dermatologist dermatologist);

	Boolean isMedicineAvailable(Pharmacy pharmacy, String medicineId);
	void saveAppointment(AppointmentDTO appointmantDTO,Pharmacy pharmacy);
	Boolean isAppointmentAvailableForScheduling(Dermatologist dermatologist,Patient patient,Integer duration,Pharmacy pharmacy,LocalDate startDate, LocalDateTime startDateTime,LocalDateTime endDateTime);

}
