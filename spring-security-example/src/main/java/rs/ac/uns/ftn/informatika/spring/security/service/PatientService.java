package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.view.UserRegisterView;



public interface PatientService {
	List<Patient> findAll ();
	ArrayList<String> findPatientsAllergies(Long user_id);

	Patient findPatientByUser(User user);
	Patient savePatient(Patient patient);
	Patient findPatientById(Long id);
	void giveOnePenalForPatient(Patient p);
	

}
