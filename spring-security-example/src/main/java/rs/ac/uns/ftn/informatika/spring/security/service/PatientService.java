package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.model.Patient;


public interface PatientService {
	List<Patient> findAll ();
	ArrayList<String> findPatientsAllergies(Long user_id);
}
