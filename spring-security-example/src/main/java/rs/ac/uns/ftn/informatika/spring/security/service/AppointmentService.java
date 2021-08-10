package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;

public interface AppointmentService {
	List<DermatologistAppointment> findAll();
}
