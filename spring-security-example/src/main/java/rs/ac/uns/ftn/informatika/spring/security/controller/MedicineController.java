package rs.ac.uns.ftn.informatika.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservation;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineShape;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineType;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MedicineReservationDTO;
import rs.ac.uns.ftn.informatika.spring.security.service.EmailService;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineReservationService;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineService;
import rs.ac.uns.ftn.informatika.spring.security.service.PatientService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.view.MedicineReservationView;
import rs.ac.uns.ftn.informatika.spring.security.view.MedicineView;
import rs.ac.uns.ftn.informatika.spring.security.view.RatingView;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

@RestController
@RequestMapping(value = "/medicine", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicineController {
    @Autowired
    private MedicineService medicineService;
    
    @Autowired
    private PharmacyService pharmacyService;
    
    @Autowired
	private EmailService emailService;
    
    @Autowired
    private MedicineReservationService medicineReservationService;

    
    @Autowired
	private UserService userService;
    
    @Autowired
	private PatientService patientService;
    
    
    @GetMapping("/getAllMedicine")
    @PreAuthorize("hasRole('ADMIN_SYSTEM') || hasRole('ADMIN_PHARMACY') || hasRole('ROLE_PATIENT') || hasRole('ROLE_SUPPLIER')")
    public List<Medicine> getAllMedicine()   {
        return this.medicineService.findAll();
    }

    @GetMapping("/getAllMedicineForSearch")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public List<Medicine> getAllMedicineForSearch()   {
        return this.medicineService.findAll();
    }

    @GetMapping("/getMedicineFromPharmacy/{email}")
    @PreAuthorize("hasRole('ADMIN_PHARMACY')")
    public Set<MedicineWithQuantity> getMedicinePriceList(@PathVariable("email") String email)   {
        return this.medicineService.getMedicinesByPharmacy(email);
    }



    @PostMapping("/addNewMedicine")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<?> addNewMedicine(@RequestBody MedicineView medicineView) {

        if(this.medicineService.findByCode(medicineView.getCode()) != null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Medicine medicine = new Medicine();
        medicine.setCode(medicineView.getCode());
        medicine.setName(medicineView.getName());
        //type shape
        if(medicineView.getType().equals("ANTIBIOTIC")) {
            medicine.setType(MedicineType.ANTIBIOTIC);
        }else if(medicineView.getType().equals("ANESTHETIC")) {
            medicine.setType(MedicineType.ANESTHETIC);
        }else
            medicine.setType(MedicineType.ANTIHISTAMINE);

        if(medicineView.getShape().equals("CAPSULE")) {
            medicine.setShape(MedicineShape.CAPSULE);
        }
        else if(medicineView.getShape().equals("TABLET"))
            medicine.setShape(MedicineShape.TABLET);
        else if(medicineView.getShape().equals("POWDER"))
            medicine.setShape(MedicineShape.POWDER);
        else if(medicineView.getShape().equals("CREAM"))
            medicine.setShape(MedicineShape.CREAM);
        else if(medicineView.getShape().equals("OIL"))
            medicine.setShape(MedicineShape.OIL);
        else
            medicine.setShape(MedicineShape.SYRUP);
        medicine.setContent(medicineView.getContent());
        medicine.setProducer(medicineView.getProducer());
        medicine.setWithprescription(medicineView.isWithPrescription());
        //substituteMedicineCodes
        Set<String> subMedicine = new HashSet<String>(medicineView.getSubstituteMedicineCodes());
        medicine.setSubstituteMedicineCodes(subMedicine);
        medicine.setNotes(medicineView.getNotes());
        medicine.setContradiction(medicineView.getContradiction());
        medicine.setAdviseddailydose(medicineView.getAdviseddailydose());
        medicine.setBuyingPoints(medicineView.getBuyingpoints());
        this.medicineService.save(medicine);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    
	@GetMapping(value = "/searchMedicine/{let}",produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Medicine> searchMedicine(@PathVariable("let") String let) {
		return medicineService.searchMedicine(let);
	}
	
	 @PostMapping("/makeReservation")
	 @PreAuthorize("hasRole('ROLE_PATIENT')")
	 public ResponseEntity<?> makeReservation(@RequestBody MedicineReservationDTO medicineReservation){
		 MedicineReservation reservation= medicineReservationService.saveReservation(medicineReservation);
		if(reservation!=null) {
			try {
				System.out.println(medicineReservation.getPatientEmail());
				emailService.sendEmail(medicineReservation.getPatientEmail(), "Medicine Reservation", "You have successfully reserved medicine with number of reservation "+reservation.getNumberOfReservation());
			} catch (MailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new  ResponseEntity<>(HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	 }
	 
	 @GetMapping(value = "/getReservationsByPatient/{email}",produces = MediaType.APPLICATION_JSON_VALUE)
	 @PreAuthorize("hasRole('ROLE_PATIENT')")
		public Collection<MedicineReservationView> getReservationsByPatient(@PathVariable("email") String email) {
			return pharmacyService.getReservationsByPatientsEmail(email);
		}
		@PostMapping("/cancelReservation/{pom}")
		public ResponseEntity<?> cancelReservation(@PathVariable("pom") Long id) {
			MedicineReservation reservation=medicineReservationService.findById(id);
			medicineReservationService.editReservation(reservation);
			return new ResponseEntity<>(HttpStatus.OK);
		}

    @GetMapping("/getMedicineById/{trid}")
    @PreAuthorize("hasRole('ROLE_PATIENT') || hasRole('ROLE_ADMIN_SYSTEM')")
    public Medicine getMedicineById(@PathVariable("trid") String trid)   {
        return this.medicineService.findById(Long.valueOf(trid));
    }
    
	@GetMapping("/getAllMedicinePatientCanEvaluate/{email}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public List<RatingView> getAllMedicinePatientCanEvaluate(@PathVariable String email) {
		User user = this.userService.findByUsername(email);
		Patient patient=this.patientService.findPatientByUser(user);
		return medicineService.getAllMedicinePatientCanEvaluate(patient.getId());
		
	}
	
	 @PostMapping("/changeRating/{rating}/{email}/{id}")
	 @PreAuthorize("hasRole('ROLE_PATIENT')")
	 public ResponseEntity<?> changeRating(@PathVariable int rating,@PathVariable String email,@PathVariable Long id){
		 User user = this.userService.findByUsername(email);
		 Patient patient=this.patientService.findPatientByUser(user);
		 this.medicineService.changeRating(rating,patient.getId(),id);
		 return new ResponseEntity<>(HttpStatus.OK);
	 }

}
