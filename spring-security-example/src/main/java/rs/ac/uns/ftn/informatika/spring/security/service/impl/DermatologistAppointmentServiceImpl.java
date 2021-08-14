package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistAppointmentRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.DermatologistAppointmentService;

@Service
public class DermatologistAppointmentServiceImpl implements DermatologistAppointmentService {
	
	@Autowired
	private DermatologistAppointmentRepository dermatologistAppointmentRepository;
	@Override
	public List<DermatologistAppointment> findAll() {
		List<DermatologistAppointment> result = dermatologistAppointmentRepository.findAll();
		return result;
	}
	@Override
	public List<DermatologistAppointment> findById(Long id) {
		List <DermatologistAppointment> appointments = new ArrayList<DermatologistAppointment>();
		for (DermatologistAppointment d:dermatologistAppointmentRepository.findAll()) {
			if(d.getDermatologist().getId().equals(id)) {
				appointments.add(d);
			}
		}
		return appointments;
	}



}
