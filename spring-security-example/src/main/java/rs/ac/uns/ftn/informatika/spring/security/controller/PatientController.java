package rs.ac.uns.ftn.informatika.spring.security.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineService;
import rs.ac.uns.ftn.informatika.spring.security.service.PatientService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;


@RestController
@RequestMapping(value = "/patient", produces = MediaType.APPLICATION_JSON_VALUE)
public class PatientController {
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private MedicineService medicineService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ROLE_PHARMACIST')  || hasRole('ROLE_DERMATOLOGIST')")
	public List<Patient> loadAll() {
		return this.patientService.findAll();
	}
	@GetMapping("/profileP/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')  || hasRole('ADMIN_SYSTEM')"+"|| hasRole('ROLE_PHARMACIST')"
	        +
	        "|| hasRole('ROLE_DERMATOLOGIST')")
	public ResponseEntity<ArrayList<String>> pharmacistDetailsP(@PathVariable(name="id") String id)  {
		User existUser = this.userService.findByUsername(id);
		ArrayList<String> result = this.patientService.findPatientsAllergies(existUser.getId());
		return new ResponseEntity<ArrayList<String>>(result,HttpStatus.OK);
	}
	@GetMapping("/getDrugs/{drugs}")
	@PreAuthorize("hasRole('ROLE_PHARMACIST')"
	        +
	        "|| hasRole('ROLE_DERMATOLOGIST')")
	public ResponseEntity<String> getDrugs(@PathVariable(name="drugs") String drugs)  {
		List<Medicine> allDrugs =  this.medicineService.findAll();
		List<Medicine> result;
		System.out.println("Svi lijekovi"+allDrugs.toString());
		System.out.println(drugs);
		String[] elements = drugs.split("\\*");
		System.out.println("Prosao elemente"+elements.toString());
		for(Medicine m:allDrugs) {
			for(String e:elements) {
				if(m.getName().toLowerCase().contains(e.toLowerCase())) {
					
				}else {
					
				}
			}
		}
	
		System.out.println("Poslijee"+allDrugs);
		return new ResponseEntity<String>("ok",HttpStatus.OK);
	}
	
	@GetMapping("/getAllMedicine")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public List<Medicine> getAllMedicine()   {
		return this.medicineService.findAll();
	}

}
