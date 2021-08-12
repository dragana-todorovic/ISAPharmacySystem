package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacist;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.AppointmentDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.HolidayRequestDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MyPatientDTO;

public interface PharmacistService {
	void saveHolidayRequest(HolidayRequestDTO holidayRequest);
	List<Pharmacist> findAll ();
	List<MyPatientDTO> myPatients(String email);
	List<MyPatientDTO> getPatientsForAppointment(String email);
	List<Medicine> getMedicines();
	void saveAppointment(AppointmentDTO appointmantDTO);
}
