package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.AppoitmentPrice;
import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.AppointmentScheduleDTO;
import rs.ac.uns.ftn.informatika.spring.security.repository.AppointmentPriceRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistAppointmentRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.UserRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.DermatologistAppointmentService;

@Service
public class DermatologistAppointmentServiceImpl implements DermatologistAppointmentService {
	
	@Autowired
	private DermatologistAppointmentRepository dermatologistAppointmentRepository;
	@Autowired
	private AppointmentPriceRepository appoitmentPriceRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DermatologistRepository dermatologistRepository;
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
	@Override
	public void saveAppointment(AppointmentScheduleDTO appointmentDTO,Patient patient, LocalDateTime startDateTime) {
		// TODO Auto-generated method stub
		try{
			User u = userRepository.findByEmail(appointmentDTO.getDermatologistEmail());
			for (Dermatologist d: dermatologistRepository.findAll()) {
			
				if(d.getUser().getEmail().equals(u.getEmail()))
				{
			System.out.println("Usao u if###########");
			System.out.println(d.getId());
			DermatologistAppointment ap = new DermatologistAppointment();
			ap.setDermatologist(d);
			
			ap.setDuration(Integer.parseInt(appointmentDTO.getDuration()));
			ap.setPatient(patient);
			ap.setStartDateTime(startDateTime);
			
			System.out.println("Usao prije save");
			dermatologistAppointmentRepository.save(ap);
			AppoitmentPrice price = new AppoitmentPrice();
			price.setAppoitment(ap);
			appoitmentPriceRepository.save(price);
			
			
			break;
			
				}}} catch (Exception e) {
			return;
		}
			
	}
	@Override
	public List<DermatologistAppointment> findByPatientId(Long id) {
		List <DermatologistAppointment> appointments = new ArrayList<DermatologistAppointment>();
		for (DermatologistAppointment d:dermatologistAppointmentRepository.findAll()) {
			if(d.getPatient().getId().equals(id)) {
				appointments.add(d);
			}
		}
		return appointments;
	}



}
