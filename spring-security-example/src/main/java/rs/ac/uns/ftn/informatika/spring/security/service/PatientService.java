package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.User;


public interface PatientService {
	List<Patient> findAll ();
	ArrayList<String> findPatientsAllergies(Long user_id);
	Patient findPatientByUser(User user);
}
