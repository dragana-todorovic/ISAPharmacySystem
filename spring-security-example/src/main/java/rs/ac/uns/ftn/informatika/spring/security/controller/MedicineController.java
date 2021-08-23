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
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MedicineReservationDTO;
import rs.ac.uns.ftn.informatika.spring.security.service.EmailService;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineReservationService;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyService;
import rs.ac.uns.ftn.informatika.spring.security.view.MedicineReservationView;
import rs.ac.uns.ftn.informatika.spring.security.view.MedicineView;

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
    
    @GetMapping("/getAllMedicine")
    @PreAuthorize("hasRole('ADMIN_SYSTEM') || hasRole('ADMIN_PHARMACY')")
    public List<Medicine> getAllMedicine()   {
        return this.medicineService.findAll();
    }


    @PostMapping("/addNewMedicine")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<?> addNewMedicine(@RequestBody MedicineView medicineView) {
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

}
