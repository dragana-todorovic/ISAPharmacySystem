package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.ArrayList;


public interface PatientService {
	ArrayList<String> findPatientsAllergies(Long user_id);
}
