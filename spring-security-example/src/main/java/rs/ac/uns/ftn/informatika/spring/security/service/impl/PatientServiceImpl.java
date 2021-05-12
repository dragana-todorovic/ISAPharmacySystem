package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.repository.PatientRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
	@Autowired
	private PatientRepository patientRepository;
	
	@Override
	public ArrayList<String> findPatientsAllergies(Long user_id) {
		System.out.println("//////////////////////////////");
		System.out.println("Service"+ findPatientsAllergies(user_id));
		return this.patientRepository.findPatientsAllergies(user_id);
	}

}
