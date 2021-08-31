package rs.ac.uns.ftn.informatika.spring.security.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MyPatientDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.PatientAndDermatologistDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.PredefinedAppointmentScheduleDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.StartDateTimeDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.WorkCalendarDTO;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistAppointmentRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.UserRepository;

import java.util.regex.Pattern;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.spring.security.model.ChangePassword;
import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistComplaint;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservation;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.Rating;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.WorkingTime;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.AppointmentDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.AppointmentScheduleDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.HolidayRequestDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MedicineReservationDTO;
import rs.ac.uns.ftn.informatika.spring.security.service.*;
import rs.ac.uns.ftn.informatika.spring.security.view.ComplaintView;
import rs.ac.uns.ftn.informatika.spring.security.view.LoyaltyProgramView;
import rs.ac.uns.ftn.informatika.spring.security.view.RatingView;

@RestController
@RequestMapping(value = "/derm", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
public class DermatologistController {
	@Autowired
	private DermatologistService dermatologistService;
	@Autowired
	private MedicineRepository medicineRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private PharmacyService pharmacyService;
	@Autowired
	private EmailService emailService;

	@Autowired
	private DermatologistAppointmentRepository dermatologistAppointmentRepository;
	@Autowired
	private DermatologistAppointmentService dermatologistAppointmentService;

	@Autowired
	private ComplaintService complaintService;
	
	@RequestMapping(value = "/holidayRequest" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public ResponseEntity<?> requestHoliday(@RequestBody HolidayRequestDTO holidayRequest) {
		if(this.dermatologistService.saveHolidayRequest(holidayRequest)) {
		return new ResponseEntity<>(HttpStatus.OK);}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	@RequestMapping(value = "/getStartDateTime" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public LocalDateTime getStartDateTime(@RequestBody StartDateTimeDTO startDateTimeDTO) {
		for(DermatologistAppointment d: dermatologistAppointmentService.findAll()) {
			if(d.getPatient()!=null) {
			if(d.getDermatologist().getUser().getEmail().equals(startDateTimeDTO.getDermatologistEmail()) && d.getPatient().getUser().getEmail().equals(startDateTimeDTO.getPatientEmail()))
				return d.getStartDateTime();
		}}
		return null;
	}

	
	@GetMapping("/getPatientsForAppointment/{email}")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public List<MyPatientDTO> getPatientsForAppointment(@PathVariable String email) {
		System.out.println(email);
		List<MyPatientDTO> myPatientsDtos;
		myPatientsDtos=this.dermatologistService.getPatientsForAppointment(email);
		return myPatientsDtos;
		
	}
	@GetMapping("/getPharmaciesForDermatologist/{email}")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public List<Pharmacy> getPharmaciesForDermatologist(@PathVariable String email) {
		Dermatologist dermatologist = null;
		List<Pharmacy> result = new ArrayList<Pharmacy>();
		for(Dermatologist d: dermatologistService.findAll()) {
			if(d.getUser().getEmail().equals(email)) {
			dermatologist=d;	
			}
		}
		for(WorkingTime w:dermatologist.getWorkingTimes()) {
			result.add(w.getPharmacy());
		}
		return result;
		
	}

	@GetMapping("/getMedicinesOnWhichPatientIsNotAllergic/{id}")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public List<Medicine> getMedicinesOnWhichPatientIsNotAllergic(@PathVariable String id) {
		List<Medicine> medicines=dermatologistService.getMedicines();	
       	Long ID = Long.parseLong(id);
       	List<Medicine> drugsOnWhichPatientIsNotAlergic= medicines;
       	List<Medicine> result = new ArrayList<Medicine>();
       	List<String> elements = new ArrayList<String>();
       	User u =userService.findById(ID);
       	
       	for(Patient p: patientService.findAll()) {
       		if(p.getUser().getId().equals(u.getId())) {
       		 	try {
       		       System.out.println("Usao u try");
       		       	Set<Medicine> elem = p.getAllergies();
       		       	for(Medicine m:elem) {
       		       		elements.add(m.getName());
       		       		
       		       	}
       		     for(Medicine m:medicines) {
       				for(String el:elements) {
       					if(m.getName().toLowerCase().contains(el.toLowerCase())) {
       						if(!result.contains(m)) {
       						result.add(m);}
       					}
       				}
       				
       			}
       			for(Medicine m: result) {
       				if(drugsOnWhichPatientIsNotAlergic.contains(m)) {
       					drugsOnWhichPatientIsNotAlergic.remove(m);
       				}
       			}

       			return drugsOnWhichPatientIsNotAlergic;
       		       	
       		       	} catch (Exception e) {
       		    		return medicines; 
       		    	}
       		}
       	}

		return null;
		
	}
	@GetMapping("/getPatientById/{id}")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public User getPatientById(@PathVariable String id) {
		String pom1 = id.substring(8,id.length());
		String pom=pom1.split("k")[0];
		String startDate = pom1.split("k")[1];
       	Long ID = Long.parseLong(pom);
       	Patient p = patientService.findPatientById(ID);
		return p.getUser();
		
	}
	@GetMapping("/getMyPatients/{email}")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public List<MyPatientDTO> getMyPatients(@PathVariable String email) {
		System.out.println(email);
		List<MyPatientDTO> myPatientsDtos;
		myPatientsDtos=this.dermatologistService.myPatients(email);
		return myPatientsDtos;
		
	}


	@GetMapping("/getAllPatients")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public List<String> getAllPatients() {
		List<Patient> patients = patientService.findAll();
		List<String> result= new ArrayList<String>();
		for(Patient p :patients) {
			result.add(p.getUser().getEmail());
		}
		return result;
		
	}
	@GetMapping("/getAllPredefinedAppointments/{email}")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public List<DermatologistAppointment> getAllPredefinedAppointments(@PathVariable String email) {
		Dermatologist dermatologist = null;
		for(Dermatologist d :dermatologistService.findAll()) {
			if(d.getUser().getEmail().equals(email)){
				dermatologist=d;
			}
		}
		List <DermatologistAppointment> appointments = dermatologistAppointmentService.findAll();
		if(appointments==null) {
			return new ArrayList<DermatologistAppointment>();
		}
		List<DermatologistAppointment> result = new ArrayList<DermatologistAppointment>();
		for(DermatologistAppointment da:appointments) {
			if(da.getDermatologist().getId().equals(dermatologist.getId()) && da.getPatient()==null) {
				result.add(da);			
			}
		}
		
		return result;
		
	}
	@RequestMapping(value = "/scheduleAnAppointment" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public ResponseEntity<?> scheduleAnAppointment(@RequestBody AppointmentScheduleDTO dto)  {
		String startDate;
		startDate = dto.getStartDate().replace('/', '-');	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try{
		String start = LocalDate.parse(startDate, formatter).format(formatter2);
	
		LocalDate datePart = LocalDate.parse(start);
		LocalTime timePart = LocalTime.parse(dto.getStartTime());
		LocalDateTime dt = LocalDateTime.of(datePart, timePart);
		if(dt.isBefore(LocalDateTime.now())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Dermatologist dermatologist=null;
		LocalDate startDateDermatologist=dt.toLocalDate();
		
		for(Dermatologist d: dermatologistService.findAll()) {
			if(d.getUser().getEmail().equals(dto.getDermatologistEmail())) {
				dermatologist= d;
			}
			}
		Patient patient=null;	
		for(Patient p:patientService.findAll()) {
			System.out.println("Email pacijenta"+p.getUser().getEmail());
			if(p.getUser().getEmail().equals(dto.getPatientEmail())) {
				patient = p;
			}
		}
		//AKO JE PHARMACY NULL ZNACI DA NE RADI TRENUTNO U NJOJ
		Pharmacy pharmacy=pharmacyService.getPharmacyByDermatologistAndStartDate(dermatologist, LocalDateTime.now());
		if(pharmacy==null) {
			System.out.println("Apoteka je null");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		System.out.println("Local date"+startDateDermatologist);
		System.out.println("Pharmacy"+pharmacy);
		 if(dermatologistService.isAppointmentAvailableForScheduling(dermatologist,patient,Integer.parseInt(dto.getDuration()), pharmacy, datePart, dt, dt.plusMinutes(Integer.parseInt((dto.getDuration()))))) {
			 System.out.println("Datum je u redu");
			 try {
				 
				 emailService.sendEmail(dto.getPatientEmail(),"Appointment","You have successfully scheduled an appointment");
				 System.out.println("Mejl je poslat");
				 dermatologistAppointmentService.saveAppointment(dto, patient, dt,pharmacy);
				 
			 }catch (Exception e){
		            e.printStackTrace();
		        }
		 }else {
			 System.out.println("Datum NIIIIIJEEE OK");
			 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		 }
		
		return new ResponseEntity<>(HttpStatus.OK);		}
		 catch (Exception e){
	         e.printStackTrace();
	         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
	     }
	}
	@RequestMapping(value = "/scheduleAPredefinedAppointment" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public ResponseEntity<?> scheduleAPredefinedAppointment(@RequestBody PredefinedAppointmentScheduleDTO dto)  {
	
		String startDateTime = dto.getStartDateTimeId().split(" sifra ")[0];
		LocalDateTime dt = LocalDateTime.of(LocalDate.parse(startDateTime.split(" ")[0]), LocalTime.parse(startDateTime.split(" ")[1]));
		if(dt.isBefore(LocalDateTime.now())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		}
		System.out.println(dt);
	

		Dermatologist dermatologist=null;
		
		
		for(Dermatologist d: dermatologistService.findAll()) {
			if(d.getUser().getEmail().equals(dto.getDermatologistEmail())) {
				dermatologist= d;
			}
			}
		Patient patient=null;	
		for(Patient p:patientService.findAll()) {
			System.out.println("Email pacijenta"+p.getUser().getEmail());
			if(p.getUser().getEmail().equals(dto.getPatientEmail())) {
				patient = p;
			}
		}
		//AKO JE PHARMACY NULL ZNACI DA NE RADI TRENUTNO U NJOJ
		Pharmacy pharmacy=pharmacyService.getPharmacyByDermatologistAndStartDate(dermatologist, LocalDateTime.now());
		if(pharmacy==null) {
			System.out.println("Apoteka je null");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		 if(dermatologistService.isAppointmentAvailableForScheduling(dermatologist,patient,Integer.parseInt(dto.getDuration()), pharmacy, LocalDate.parse(startDateTime.split(" ")[0]), dt, dt.plusMinutes(Integer.parseInt((dto.getDuration()))))) {
			 System.out.println("Datum je u redu");
			 try {
				 
				 emailService.sendEmail(dto.getPatientEmail(),"Appointment","You have successfully scheduled an appointment");
				 System.out.println("Mejl je poslat");
				 dermatologistAppointmentService.savePredefinedAppointment(dto, patient);
				 
			 }catch (Exception e){
		            e.printStackTrace();
		        }
		 }else {
			 System.out.println("Datum NIIIIIJEEE OK");
			 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		 }
		
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@PostMapping("/giveOnePenalForPatient/{id}")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public ResponseEntity<?> giveOnePenalForPatient(@PathVariable String id) {
		System.out.println("ID"+id);
		String pom = id.substring(9,id.length()).split("k")[0];
		String startDateTime =id.substring(9,id.length()).split("k")[1]; 
		System.out.println("start"+startDateTime);
       	Long ID = Long.parseLong(pom);
       	Patient p = patientService.findPatientById(ID);
       	patientService.giveOnePenalForPatient(p);
       	List<DermatologistAppointment> appointments = new ArrayList<DermatologistAppointment>();
       	appointments = dermatologistAppointmentService.findAll();
       	for(DermatologistAppointment ap: appointments) {
       		if(ap.getPatient()!=null) {
	       		if(ap.getPatient().getId().equals(p.getId()) && ap.getStartDateTime().toString().equals(startDateTime)) {      			
	       			ap.setStartDateTime(LocalDateTime.now());
	       			dermatologistAppointmentRepository.save(ap);
	       			
	       		}
       		}
       	}
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	@GetMapping("/getPatientWhoIsAtAppointment/{idStart}")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public User getPatientWhoIsAtAppointment(@PathVariable String idStart) {
		String id = idStart.substring(8,idStart.length()).split("k")[0];
		for (Patient p: patientService.findAll()) {
			if(p.getId().equals(Long.parseLong(id))) {
				return p.getUser();
			}
		}
		return null;
		
	}
	@PostMapping("/checkMedicineAvailability/{medicineId}/{email}")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public Boolean checkMedicineAvailability(@PathVariable String medicineId,@PathVariable String email) {
		Dermatologist dermatologist=null;
		for(Dermatologist d: dermatologistService.findAll()) {
			if(d.getUser().getEmail().equals(email)) {
				dermatologist= d;
			}
			}
		Pharmacy pharmacy=pharmacyService.getPharmacyByDermatologistAndStartDate(dermatologist, LocalDateTime.now());
		if(pharmacy==null) {
			System.out.println("APOTEKA JE NULL");
			return false;
		}
		System.out.println("Pharmacy"+pharmacy.getName()+pharmacy.getId());
		Boolean result = dermatologistService.isMedicineAvailable(pharmacy,medicineId);
		System.out.println("Result"+result);
		return result;
		
	}
	@GetMapping("/getSubstituteMedicine/{medicineId}/{idStart}")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public List<Medicine> getSubstituteMedicine(@PathVariable String medicineId,@PathVariable String idStart) {
		List<Medicine> medicines=new ArrayList<Medicine>();
		Optional<Medicine> medicine = medicineRepository.findById(Long.parseLong(medicineId));
		Medicine med = medicine.get();
		for(String code:med.getSubstituteMedicineCodes() ) {
			Medicine m= medicineRepository.findByCode(code);
			if(m!=null) {
			medicines.add(m);}
		}
		
			
       	Long ID = Long.parseLong(idStart.substring(8,idStart.length()).split("k")[0]);
       	System.out.println(idStart);
       	List<Medicine> drugsOnWhichPatientIsNotAlergic= medicines;
       	List<Medicine> result = new ArrayList<Medicine>();
    	List<String> elements = new ArrayList<String>();
       	User u =userService.findById(ID);
       	System.out.println(u.getId());
       	for(Patient p: patientService.findAll()) {
       		if(p.getId().equals(u.getId())) {
       			
       		 	try {
       		       System.out.println("Usao u try***************");
       		       	Set<Medicine> elem = p.getAllergies();
       		       	System.out.println(p.getUser().getFirstName()+p.getUser().getEmail());
       		       	for(Medicine m:elem) {
       		       		System.out.println("Medicine na koji je alergican*****************"+m.getName());
       		       		elements.add(m.getName());
       		       		
       		       	}
       		     for(Medicine m:medicines) {
       				for(String el:elements) {
       					if(m.getName().toLowerCase().contains(el.toLowerCase())) {
       						if(!result.contains(m)) {
       						result.add(m);}
       					}
       				}
       				
       			}
       			for(Medicine m: result) {
       				if(drugsOnWhichPatientIsNotAlergic.contains(m)) {
       					drugsOnWhichPatientIsNotAlergic.remove(m);
       				}
       			}

       			return drugsOnWhichPatientIsNotAlergic;
       		       	
       		       	} catch (Exception e) {
       		    		return medicines; 
       		    	}
       		}
       	}

		return null;
		
	}
	@GetMapping("/getSpecificationForMedicine/{medicineId}")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public Optional<Medicine> getSpecificationForMedicine(@PathVariable String medicineId) {
		
		Optional<Medicine> medicine = medicineRepository.findById(Long.parseLong(medicineId));
		return medicine;
		
	}
	
	@RequestMapping(value = "/saveAppointment" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public ResponseEntity<?> saveAppointment(@RequestBody AppointmentDTO appointmentDTO) {
		Dermatologist dermatologist=null;
		for(Dermatologist d:dermatologistService.findAll()) {
			if(d.getUser().getEmail().equals(appointmentDTO.getDermatologistEmail())) {
				dermatologist = d;
			}
		}
		Pharmacy pharmacy=pharmacyService.getPharmacyByDermatologistAndStartDate(dermatologist, LocalDateTime.now());
		if(pharmacy==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		System.out.println("Appointmant"+ appointmentDTO.getPatientEmail()+appointmentDTO.getPatientId());
		this.dermatologistService.saveAppointment(appointmentDTO,pharmacy);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@GetMapping("/getAllPatientsForSearch")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public List<User> getAllPatientsForSearch() {
		List<Patient> result = patientService.findAll();
		List<User> users = new ArrayList<User>();
		for(Patient p:result) {
			System.out.println("User"+p.getUser());
			users.add(p.getUser());
		}
		return users;
		
	}
	@GetMapping("/getDermatologistAppointments/{email}/{pharmacy}")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public List<WorkCalendarDTO> getDermatologistAppointments(@PathVariable String email,@PathVariable String pharmacy) {
	Pharmacy choosenPharmacy = pharmacyService.findById(Long.parseLong(pharmacy.split(",sifra ")[1])).get();
	Dermatologist dermatologist=null;
	for(Dermatologist d:dermatologistService.findAll()) {
		if(d.getUser().getEmail().equals(email)) {
			dermatologist = d;
		}
	}
	return dermatologistService.getAppointmentsForCalendar(dermatologist,choosenPharmacy);
		
	}
	
	@GetMapping("/getAllDermPatientCanEvaluate/{email}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public List<RatingView> getAllDermPatientCanEvaluate(@PathVariable String email) {
		User user = this.userService.findByUsername(email);
		Patient patient=this.patientService.findPatientByUser(user);
		return dermatologistService.getAllDermPatientCanEvaluate(patient);
		
	}
	
	 @PostMapping("/changeRating/{rating}/{email}/{id}")
	 @PreAuthorize("hasRole('ROLE_PATIENT')")
	 public ResponseEntity<?> changeRating(@PathVariable int rating,@PathVariable String email,@PathVariable Long id){
		 User user = this.userService.findByUsername(email);
		 Patient patient=this.patientService.findPatientByUser(user);
		 this.dermatologistService.changeRating(rating,patient,id);
		 return new ResponseEntity<>(HttpStatus.OK);
	 }

	@PostMapping("/saveDermatologistComplaint")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> saveDermatologistComplaint(@RequestBody ComplaintView complaintView) {
		// pronadjem po kategoriji i posaljem dva podatka za cuvanje  i setovanje
		User user = this.userService.findByUsername(complaintView.getUserName());
		Patient patient=this.patientService.findPatientByUser(user);
// u ovom slucaju complaint on name saljem id dermatologa
		Dermatologist dermatologist= null;
		for(Dermatologist d: dermatologistService.findAll()) {
			if(d.getId().equals(Long.parseLong( complaintView.getComplainedOnName()))) {
				dermatologist= d;
			}
		}

		DermatologistComplaint dermatologistComplaint = new DermatologistComplaint();
		dermatologistComplaint.setDermatologist(dermatologist);
		dermatologistComplaint.setContent(complaintView.getContent());
		dermatologistComplaint.setAnswered(false);
		dermatologistComplaint.setPatient(patient);
		this.complaintService.saveDerm(dermatologistComplaint);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
