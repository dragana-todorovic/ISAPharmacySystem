package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.AppoitmentPrice;
import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.AppointmentScheduleDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.PredefinedAppointmentScheduleDTO;
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
		System.out.println("Usao u find all");
		List<DermatologistAppointment> result = dermatologistAppointmentRepository.findAll();
		System.out.println("Broj"+result.size());
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
	public void saveAppointment(AppointmentScheduleDTO appointmentDTO,Patient patient, LocalDateTime startDateTime,Pharmacy pharmacy) {
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
			ap.setPharmacy(pharmacy);
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
	@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
	@Override
	public void savePredefinedAppointment(PredefinedAppointmentScheduleDTO appointmentDTO,Patient patient) {
		// TODO Auto-generated method stub
		try{
			Long appId =Long.parseLong( appointmentDTO.getStartDateTimeId().split(" sifra ")[1]);
			for(DermatologistAppointment da: dermatologistAppointmentRepository.findAll()) {
				if(da.getId().equals(appId)) {
					da.setDuration(Integer.parseInt(appointmentDTO.getDuration()));
					da.setPatient(patient);
					dermatologistAppointmentRepository.save(da);
					break;
				}
			}
		} catch (Exception e) {
			return;
		}
			
			
	}
	@Override
	public List<DermatologistAppointment> findByPatientId(Long id) {
		List <DermatologistAppointment> appointments = new ArrayList<DermatologistAppointment>();
		for (DermatologistAppointment d:dermatologistAppointmentRepository.findAll()) {
			if(d.getPatient()!=null) {
				if(d.getPatient().getId().equals(id)) {
					appointments.add(d);
				}
			}
			else {
				continue;
			}

		}

		return appointments;
	}



}
