package rs.ac.uns.ftn.informatika.spring.security.controller;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.ftn.informatika.spring.security.model.*;
import rs.ac.uns.ftn.informatika.spring.security.service.*;
import rs.ac.uns.ftn.informatika.spring.security.view.*;

import javax.imageio.ImageIO;


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

	@Autowired
	private  EmailService emailService;

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
	public ResponseEntity<?> addAllergie(@PathVariable(name="id") String id,@RequestBody UserRegisterView userRequest) {
		User existUser = this.userService.findByUsername(userRequest.getEmail());
		Patient patient = this.patientService.findPatientByUser(existUser);
		for(Medicine s:this.medicineService.findAll()) {
			if(s.getName().equalsIgnoreCase(id)) {
				patient.getAllergies().add(s);
			}
		}
		this.patientService.savePatient(patient);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	//mili for loyaltyScale
	@PostMapping("/saveLoyaltyScale")
	@PreAuthorize("hasRole('ADMIN_SYSTEM')")
	public ResponseEntity<?> saveLoyaltyScale(@RequestBody LoyaltyScaleView loyaltyScaleView) {
		this.loyaltyScaleService.editLoyalty(loyaltyScaleView);
		this.loyaltyScaleService.updateCathegoryOfPatients();
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
	@GetMapping("/getPatientsDiscount/{email}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public int getPatientsDiscount(@PathVariable(name="email") String email) {
		User user = this.userService.findByEmail(email);
		Patient patient = this.patientService.findPatientByUser(user);
		return patientService.getPatientsDiscount(patient);
	}


	@PostMapping(value = "/sendQrCode",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<EPrescriptionPharmacyView>> sendQrCode(@RequestParam("file") MultipartFile  file) throws IOException {
		String path = new File("src/main/resources/qrCodes").getAbsolutePath();
		Path filepath = Paths.get(path, file.getOriginalFilename());

		try (OutputStream os = Files.newOutputStream(filepath)) {
			os.write(file.getBytes());
		} catch (IOException e2) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e2.getMessage());
		}
		String codesAndCount = decodeQrCode(filepath);
		return new ResponseEntity<>(this.patientService.findPharmacyForEPrescription(codesAndCount),HttpStatus.OK);
	}

	@PostMapping(value = "/buyEPrescription")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> buyEPrescription(@RequestBody EPrescriptionBuyPharmacyView ePrescriptionBuyPharmacyView){
		User user = this.userService.findByUsername(ePrescriptionBuyPharmacyView.getPatientEmail());
		Patient patient = this.patientService.findPatientByUser(user);

		Pharmacy pharmacy = this.pharmacyService.findById(Long.valueOf(ePrescriptionBuyPharmacyView.getPharmacyId())).get();
		try {

			emailService.sendEmail(patient.getUser().getEmail(),"Successfully bought medicine by ePrescription","You have successfullbought medicine by ePrescription");
			System.out.println("Mejl je poslat");
			this.patientService.buyEPrescriptionInPharmacy(patient, pharmacy, ePrescriptionBuyPharmacyView.getMedicineCodes(), ePrescriptionBuyPharmacyView.getMedicineCodesQuantity());

		}catch (Exception e){
			e.printStackTrace();
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
	public static String decodeQrCode(Path filePath) throws IOException {
		File img = new File(filePath.toString());
		BufferedImage bufferedImage = ImageIO.read(img);
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		try {
			Result result = new MultiFormatReader().decode(bitmap);
			return result.getText();
		} catch (NotFoundException e) {
			System.out.println("There is no QR code in the image");
			return null;
		}
	}
}
