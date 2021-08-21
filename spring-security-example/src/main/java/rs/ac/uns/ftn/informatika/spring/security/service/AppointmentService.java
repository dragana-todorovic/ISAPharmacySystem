package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.view.PredefinedAppointmentDTO;

public interface AppointmentService {
	List<DermatologistAppointment> findAll();

	Boolean createPredefinedExamination(String email, PredefinedAppointmentDTO predefinedAppointment);
}
