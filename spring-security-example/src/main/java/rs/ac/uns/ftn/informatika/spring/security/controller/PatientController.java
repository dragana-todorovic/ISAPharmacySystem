package rs.ac.uns.ftn.informatika.spring.security.controller;


import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.spring.security.model.*;
import rs.ac.uns.ftn.informatika.spring.security.service.*;
import rs.ac.uns.ftn.informatika.spring.security.view.LoyaltyProgramView;
import rs.ac.uns.ftn.informatika.spring.security.view.LoyaltyScaleView;
import rs.ac.uns.ftn.informatika.spring.security.view.SubscribePatientOnPharmacyView;
import rs.ac.uns.ftn.informatika.spring.security.view.UserRegisterView;


@RestController
@RequestMapping(value = "/patient", produces = MediaType.APPLICATION_JSON_VALUE)
public class PatientController {
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private MedicineService medicineService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private LoyaltyScaleService loyaltyScaleService;

	@Autowired
	private LoyaltyProgramService loyaltyProgramService;

	@Autowired
	private PharmacyService pharmacyService;

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
	@GetMapping("/getPatientById/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT') ")
	public ResponseEntity<Patient> patientDetails(@PathVariable(name="id") String id)  {
		User existUser = this.userService.findByUsername(id);
		Patient patient = this.patientService.findPatientByUser(existUser);
		return new ResponseEntity <Patient>(patient,HttpStatus.OK);
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
	
	@GetMapping("/getAllMedicineForAllergies/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public List<Medicine> getAllMedicine(@PathVariable(name="id") String id)   {
		List<Medicine> allergies= this.medicineService.findAll();
		List<Medicine> newall=new ArrayList<Medicine>();
		User existUser = this.userService.findByUsername(id);
		ArrayList<String> result = this.patientService.findPatientsAllergies(existUser.getId());
			for(String a: result) {
				for(Medicine al: allergies) {
					if(!al.getName().equalsIgnoreCase(a)) {
						newall.add(al);
						//continue;
					}
			}
				
		}
		return newall;
	}
	
	@PostMapping("/removeAllergie/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')  || hasRole('ADMIN_SYSTEM')"+"|| hasRole('ROLE_PHARMACIST')"
	        +
	        "|| hasRole('ROLE_DERMATOLOGIST')")
	public ResponseEntity<ArrayList<String>> removeAllerige(@PathVariable(name="id") String id,@RequestBody UserRegisterView userRequest) {
		User existUser = this.userService.findByUsername(userRequest.getEmail());
		Patient patient = this.patientService.findPatientByUser(existUser);
		Set<Medicine> allergies = patient.getAllergies();
		for(Medicine s:patient.getAllergies()) {
			if(s.getName().equalsIgnoreCase(id)) {
				allergies.remove(s);
			}
		}
		patient.setAllergies(allergies);
		this.patientService.savePatient(patient);
		return null;
	}
	
	@PostMapping("/addAllergie/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')  || hasRole('ADMIN_SYSTEM')"+"|| hasRole('ROLE_PHARMACIST')"
	        +
	        "|| hasRole('ROLE_DERMATOLOGIST')")
	public ResponseEntity<ArrayList<String>> addAllergie(@PathVariable(name="id") String id,@RequestBody UserRegisterView userRequest) {
		User existUser = this.userService.findByUsername(userRequest.getEmail());
		Patient patient = this.patientService.findPatientByUser(existUser);
		List<Medicine> medicine=this.medicineService.findAll();
		Set<Medicine> allergies = patient.getAllergies();
		for(Medicine s:this.medicineService.findAll()) {
			if(s.getName().equalsIgnoreCase(id)) {
				allergies.add(s);
				System.out.println(s.getName());
			}
		}
		patient.setAllergies(allergies);
		this.patientService.savePatient(patient);
		return null;
	}

	//mili for loyaltyScale
	@PostMapping("/saveLoyaltyScale")
	@PreAuthorize("hasRole('ADMIN_SYSTEM')")
	public ResponseEntity<?> saveLoyaltyScale(@RequestBody LoyaltyScaleView loyaltyScaleView) {
		// pronadjem po kategoriji i posaljem dva podatka za cuvanje  i setovanje
		this.loyaltyScaleService.editLoyalty(loyaltyScaleView);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/getLoyalty")
	@PreAuthorize("hasRole('ADMIN_SYSTEM')")
	public List<LoyaltyScale> getLoyalty()   {
		return this.loyaltyScaleService.findAll();
	}

	@GetMapping("/getLoyaltyProgram")
	@PreAuthorize("hasRole('ADMIN_SYSTEM')")
	public List<LoyaltyProgram> getLoyaltyProgram()   {
		return this.loyaltyProgramService.findAll();
	}

	@PostMapping("/saveLoyaltyProgram")
	@PreAuthorize("hasRole('ADMIN_SYSTEM')")
	public ResponseEntity<?> saveLoyaltyProgram(@RequestBody LoyaltyProgramView loyaltyProgramView) {
		// pronadjem po kategoriji i posaljem dva podatka za cuvanje  i setovanje
		this.loyaltyProgramService.editLoyaltyProgram(loyaltyProgramView);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/subscribePatientOnActionsAndBenefits")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> subscribePatientOnAB(@RequestBody SubscribePatientOnPharmacyView subscribePatientOnPharmacyView) {
		User user = this.userService.findByEmail(subscribePatientOnPharmacyView.getPatientEmail());
		Patient patient = this.patientService.findPatientByUser(user);

		Set<Long> patientSubscriptions = patient.getPatientSubscriptions();
		if(patient.getPatientSubscriptions() != null) {
			//provera da li je vec subscribovan
			for (Long existsId : patientSubscriptions) {
				System.out.println(existsId);
				if (existsId == Long.valueOf(subscribePatientOnPharmacyView.getPharmacyId())) {
					//ALREADY SUBSCRIBED
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
		}
		patientSubscriptions.add(Long.valueOf(subscribePatientOnPharmacyView.getPharmacyId()));
		patient.setPatientSubscriptions(patientSubscriptions);

		this.patientService.savePatient(patient);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/getUserAllSubscribedPharmacies/{email}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<Pharmacy>> getUserAllSubscribedPharmacies(@PathVariable(name="email") String email) {
		User user = this.userService.findByEmail(email);
		Patient patient = this.patientService.findPatientByUser(user);

		Set<Long> patientSubscriptions = patient.getPatientSubscriptions();
		List<Pharmacy> pharmacies = new ArrayList<>();
		for (Long pharmacyId : patientSubscriptions) {
			pharmacies.add(this.pharmacyService.findById(pharmacyId).get());
		}

		return new  ResponseEntity<>(pharmacies,HttpStatus.OK);
	}

	@PostMapping("/unsubscribePatientOnActionsAndBenefits")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> unsubscribePatientOnAB(@RequestBody SubscribePatientOnPharmacyView subscribePatientOnPharmacyView) {
		User user = this.userService.findByEmail(subscribePatientOnPharmacyView.getPatientEmail());
		Patient patient = this.patientService.findPatientByUser(user);

		Set<Long> patientSubscriptions = patient.getPatientSubscriptions();

		patientSubscriptions.remove(Long.valueOf(subscribePatientOnPharmacyView.getPharmacyId()));
		patient.setPatientSubscriptions(patientSubscriptions);

		this.patientService.savePatient(patient);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
