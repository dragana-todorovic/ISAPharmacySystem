package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.repository.AppointmentRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService{
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Override
	public List<DermatologistAppointment> findAll() {
		List<DermatologistAppointment> result=appointmentRepository.findAll();
		System.out.println("Servis");
		return result;
	}

}