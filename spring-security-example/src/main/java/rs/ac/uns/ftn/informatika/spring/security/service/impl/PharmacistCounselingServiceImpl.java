package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacist;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounseling;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.CounselingDTO;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistAppointmentRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistCounselingRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.UserRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacistCounselingService;

@Service
public class PharmacistCounselingServiceImpl implements PharmacistCounselingService {
	
	@Autowired
	private PharmacistCounselingRepository pharmacistCounselingRepository;	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PharmacistRepository pharmacistRepository;
	@Override
	public List<PharmacistCounseling> findById(Long id) {
		List <PharmacistCounseling> appointments = new ArrayList<PharmacistCounseling>();
		for (PharmacistCounseling d:pharmacistCounselingRepository.findAll()) {
			if(d.getPharmacist().getId().equals(id)) {
				appointments.add(d);
			}
		}
		return appointments;
	}

	@Override
	public List<PharmacistCounseling> findAll() {
		List<PharmacistCounseling> result = pharmacistCounselingRepository.findAll();
		return result;
	
	}
	@Override
	public List<PharmacistCounseling> findByPatientId(Long id) {
		System.out.println("PATIENT ID"+id);
		List <PharmacistCounseling> appointments = new ArrayList<PharmacistCounseling>();
		for (PharmacistCounseling d:pharmacistCounselingRepository.findAll()) {
			if(d.getPatient().getId().equals(id)) {
			
				appointments.add(d);
			}
		}
		
		return appointments;
	}
	@Override
	public void saveAppointment(CounselingDTO appointmentDTO,Patient patient, LocalDateTime startDateTime) {
		// TODO Auto-generated method stub
		try{
			User u = userRepository.findByEmail(appointmentDTO.getPharmacistEmail());
			for (Pharmacist d: pharmacistRepository.findAll()) {	
				if(d.getUser().getEmail().equals(u.getEmail()))
				{
			System.out.println("Usao u if###########");
			System.out.println(d.getId());
			PharmacistCounseling ap = new PharmacistCounseling();
			ap.setPharmacist(d);
			
			ap.setDuration(Integer.parseInt(appointmentDTO.getDuration()));
			ap.setPatient(patient);
			ap.setStartDateTime(startDateTime);
			System.out.println("Usao prije save");
			pharmacistCounselingRepository.save(ap);
			break;
			
				}}} catch (Exception e) {
			return;
		}
	}

}
