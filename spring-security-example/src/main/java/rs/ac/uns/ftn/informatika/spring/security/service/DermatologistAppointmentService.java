package rs.ac.uns.ftn.informatika.spring.security.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.AppointmentScheduleDTO;

public interface DermatologistAppointmentService {
	List<DermatologistAppointment> findById(Long id);
	List<DermatologistAppointment> findAll ();
	List<DermatologistAppointment> findByPatientId(Long id);
	void saveAppointment(AppointmentScheduleDTO dto,Patient patient, LocalDateTime startDateTime);
}
