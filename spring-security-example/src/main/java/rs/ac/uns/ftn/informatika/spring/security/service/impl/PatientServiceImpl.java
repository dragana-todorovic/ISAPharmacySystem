package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.Authority;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.repository.PatientRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
	@Autowired
	private PatientRepository patientRepository;
	
	@Override
	public List<Patient> findAll() {
		List<Patient> result = patientRepository.findAll();
		return result;
	}
	@Override
	public ArrayList<String> findPatientsAllergies(Long user_id) {
		System.out.println("//////////////////////////////");
		System.out.println("Service"+ this.patientRepository.findPatientsAllergies(user_id));
		return this.patientRepository.findPatientsAllergies(user_id);
	}
	@Override
	public Patient findPatientByUser(User user) {
		System.out.println(this.patientRepository.findByUser(user));
		return this.patientRepository.findByUser(user);
	}
	@Override
	public Patient savePatient(Patient patient) {
		Patient p = new Patient();
		p.setAllergies(patient.getAllergies());
		p.setCategory(patient.getCategory());
		p.setPoints(patient.getPoints());
		p.setUser(patient.getUser());
		p.setId(patient.getId());

		p = this.patientRepository.save(p);
		return p;
	}

}
