package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.List;
import java.util.Optional;

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;

public interface DermatologistAppointmentService {
	List<DermatologistAppointment> findById(Long id);
	List<DermatologistAppointment> findAll ();
}
