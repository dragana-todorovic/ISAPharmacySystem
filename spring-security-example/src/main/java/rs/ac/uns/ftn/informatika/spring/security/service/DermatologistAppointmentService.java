package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.List;
import java.util.Optional;

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppoitment;

public interface DermatologistAppointmentService {
	List<DermatologistAppoitment> findById(Long id);
	List<DermatologistAppoitment> findAll ();
}
