package rs.ac.uns.ftn.informatika.spring.security.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.spring.security.model.*;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.AppointmentDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.CounselingDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.HolidayRequestDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MyPatientDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.StartDateTimeDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.WorkCalendarDTO;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineReservationRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistCounselingRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.UserRepository;

import rs.ac.uns.ftn.informatika.spring.security.service.*;
import rs.ac.uns.ftn.informatika.spring.security.view.ComplaintView;

import rs.ac.uns.ftn.informatika.spring.security.service.DermatologistAppointmentService;
import rs.ac.uns.ftn.informatika.spring.security.service.DermatologistService;
import rs.ac.uns.ftn.informatika.spring.security.service.EmailService;
import rs.ac.uns.ftn.informatika.spring.security.service.PatientService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacistCounselingService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacistService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import javax.persistence.LockModeType;

import rs.ac.uns.ftn.informatika.spring.security.view.PharamcistForCounselingView;
import rs.ac.uns.ftn.informatika.spring.security.view.RatingView;

@RestController
@RequestMapping(value = "/pharm", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('ROLE_PHARMACIST')")
public class PharmacistController {
	@Autowired
	private PharmacistService pharmacistService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private UserService userService;
	@Autowired
	private MedicineRepository medicineRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private PharmacistRepository pharmacistRepository;
	@Autowired
	private PharmacistCounselingRepository pharmacistCounselingRepository;
	@Autowired
	private PharmacistCounselingService pharmacistCounselingService;
	@Autowired
	private MedicineReservationRepository medicineReservationRepository;
	@Autowired
	private ComplaintService complaintService;
	
	@RequestMapping(value = "/holidayRequest" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public ResponseEntity<?> requestHoliday(@RequestBody HolidayRequestDTO holidayRequest) {
		System.out.println("Usao u kontroler");
		if(this.pharmacistService.saveHolidayRequest(holidayRequest))
		{
		return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
	
	}
	@RequestMapping(value = "/getStartDateTime" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public LocalDateTime getStartDateTime(@RequestBody StartDateTimeDTO startDateTimeDTO) {
		for(PharmacistCounseling d: pharmacistCounselingService.findAll()) {
			if(d.getPharmacist().getUser().getEmail().equals(startDateTimeDTO.getDermatologistEmail()) && d.getPatient().getUser().getEmail().equals(startDateTimeDTO.getPatientEmail()))
				return d.getStartDateTime();
		}
		return null;
	}
	@GetMapping("/getPharmacistsCounseling/{email}")
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public List<WorkCalendarDTO> getPharmacistsCounseling(@PathVariable String email) {
	Pharmacist pharmacist=null;
	for(Pharmacist d:pharmacistService.findAll()) {
		if(d.getUser().getEmail().equals(email)) {
			pharmacist = d;
		}
	}
	
	return pharmacistService.getPharmacistsCounseling(pharmacist);
		
	}
	
	@GetMapping("/getMedicinesOnWhichPatientIsNotAllergic/{id}")
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public List<Medicine> getMedicinesOnWhichPatientIsNotAllergic(@PathVariable String id) {
		List<Medicine> medicines=pharmacistService.getMedicines();	
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
       			System.out.println("Drugs"+drugsOnWhichPatientIsNotAlergic.size());
       			return drugsOnWhichPatientIsNotAlergic;
       		       	
       		       	} catch (Exception e) {
       		    		return medicines; 
       		    	}
       		}
       	}

		return null;
		
	}
	@GetMapping(value = "/searchReservedMedicines/{resNumber}/{email}",produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public List<MedicineReservation> searchMedicine(@PathVariable("resNumber") String resNumber,@PathVariable("email") String email) {
		System.out.println("Usao u rezervaciju");
		Pharmacist pharmacist =null;
		for(Pharmacist p: pharmacistService.findAll()) {
			System.out.println(p);
			System.out.println(p.getUser().getEmail());
			if(p.getUser().getEmail().equals(email)) {
				pharmacist=p;
			}
		}
		System.out.println(pharmacist);

		if(pharmacist.getWorkingTimes()==null) {
			return new ArrayList<MedicineReservation>();
		}
		Pharmacy pharmacy = pharmacist.getWorkingTimes().getPharmacy();
		if(pharmacy==null) {
			return new ArrayList<MedicineReservation>();
		}
		
		return pharmacistService.searchReservedMedicnes(resNumber,pharmacy);
	}
	@GetMapping("/getMyPatients/{email}")
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public List<MyPatientDTO> getMyPatients(@PathVariable String email) {
		System.out.println(email);
		List<MyPatientDTO> myPatientsDtos;
		myPatientsDtos=this.pharmacistService.myPatients(email);
		return myPatientsDtos;
		
	}
	@GetMapping("/getPatientById/{id}")
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public User getPatientById(@PathVariable String id) {
		System.out.println("IDDDDDDDDDD"+id);
		String pom1 = id.substring(8,id.length());
		String pom=pom1.split("k")[0];
		String startDate = pom1.split("k")[1];
		
       	//int ID = Integer.parseInt(pom);
       	System.out.println("POOM"+pom);
       	System.out.println(startDate);
       	Long ID = Long.parseLong(pom);
       	Patient p = patientService.findPatientById(ID);
       	System.out.println(p.getUser().getFirstName());
       	System.out.println(p.getUser().getEmail());
		return p.getUser();
		
	}
	@GetMapping("/getPatientsForAppointment/{email}")
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public List<MyPatientDTO> getPatientsForAppointment(@PathVariable String email) {
		System.out.println(email);
		List<MyPatientDTO> myPatientsDtos;
		myPatientsDtos=this.pharmacistService.getPatientsForAppointment(email);
		System.out.println("PACIJENTI "+myPatientsDtos);
		return myPatientsDtos;
		
	}
	@GetMapping("/getVersion/{id}")
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public MedicineReservation getVersion(@PathVariable String id) {
		System.out.println("MEDICINE RESERVATION");
		Long ID = Long.parseLong(id.substring(8,id.length()));
		return medicineReservationRepository.findById(ID).get();		
	}
	
	@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT) 
	@GetMapping("/takeReservedMedicine/{id}/{version}")
	public ResponseEntity<?> takeReservedMedicine(@PathVariable String id,@PathVariable String version) {
		System.out.println("ID"+id);
		String pom = id.substring(8,id.length());
       	Long ID = Long.parseLong(pom);
       	MedicineReservation reservation = medicineReservationRepository.findById(ID).get();
       	if(reservation.getVersion()!=Long.parseLong(version)) {
       		return new ResponseEntity<>(HttpStatus.CONFLICT);
       	}
       	reservation.setStatus(MedicineReservationStatus.TAKEN);	
       	LocalDateTime dt = LocalDateTime.of(reservation.getDueTo(), reservation.getDueToTime());
       	if(LocalDateTime.now().isBefore(dt.minus(Period.ofDays(1)))) {
        try {
			 
			 emailService.sendEmail(reservation.getPatient().getUser().getEmail(),"Reservation","Raservation has been taken.");
			 System.out.println("Mejl je poslat");
			 medicineReservationRepository.save(reservation);
			 
		 }catch (Exception e){
	            e.printStackTrace();
	        }
		return new ResponseEntity<>(HttpStatus.OK);
	}else {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}}
	@RequestMapping(value = "/saveAppointment" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public ResponseEntity<?> saveAppointment(@RequestBody AppointmentDTO appointmentDTO) {
		Pharmacist pharmacist=null;
		for(Pharmacist d:pharmacistService.findAll()) {
			if(d.getUser().getEmail().equals(appointmentDTO.getDermatologistEmail())) {
				pharmacist = d;
			}
		}
		if(pharmacist.getWorkingTimes()==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Pharmacy pharmacy=pharmacist.getWorkingTimes().getPharmacy();
		if(pharmacy==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		System.out.println("Appointmant"+ appointmentDTO.getPatientEmail()+appointmentDTO.getPatientId());
		this.pharmacistService.saveAppointment(appointmentDTO,pharmacy);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@GetMapping("/getAllPatients")
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public List<String> getAllPatients() {
		List<Patient> patients = patientService.findAll();
		List<String> result= new ArrayList<String>();
		for(Patient p :patients) {
			result.add(p.getUser().getEmail());
		}
		return result;
		
	}
	@PreAuthorize("hasRole('ROLE_PHARMACIST') || hasRole('ROLE_PATIENT') ")
	@RequestMapping(value = "/scheduleAnAppointment" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(readOnly=false)
	public ResponseEntity<?> scheduleAnAppointment(@RequestBody CounselingDTO dto)  {
		System.out.println(dto.getDuration());
		System.out.println(dto.getStartDate());
		String startDate;
		String start;
		if(dto.getStartDate().contains("/")) {
			System.out.println("sadrzi /");
			startDate = dto.getStartDate().replace('/', '-');
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
			DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			start = LocalDate.parse(startDate, formatter).format(formatter2);
		}
		else
			start=dto.getStartDate();
		try {
		LocalDate datePart = LocalDate.parse(start);
		System.out.println(datePart);
		LocalTime timePart = LocalTime.parse(dto.getStartTime());
		LocalDateTime dt = LocalDateTime.of(datePart, timePart);
		if(dt.isBefore(LocalDateTime.now())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		LocalDate startDateDPharmacist=dt.toLocalDate();
		System.out.println("Farmaceut"+dto.getPharmacistEmail());
	
		Long ID=0L;
		for(Pharmacist d: pharmacistService.findAll()) {
			System.out.println("*******************************");
			System.out.println(d.getUser().getFirstName());
			System.out.println(d.getUser().getEmail());
			System.out.println(dto.getPharmacistEmail());
			if(d.getUser().getEmail().equals(dto.getPharmacistEmail())) {
				System.out.println("Email farmaceuta"+d.getUser().getEmail());
				ID= d.getId();
			}
			}
		Pharmacist pharmacist=this.pharmacistRepository.findPharmacistByID(ID);
		
		Patient patient=null;	
		for(Patient p:patientService.findAll()) {
			System.out.println("Email pacijenta"+p.getUser().getEmail());
			if(p.getUser().getEmail().equals(dto.getPatientEmail())) {
				patient = p;
			}
		}
		//AKO JE PHARMACY NULL ZNACI DA NE RADI TRENUTNO U NJOJ
		System.out.println(pharmacist);
		if(pharmacist.getWorkingTimes()==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Pharmacy pharmacy=pharmacist.getWorkingTimes().getPharmacy();
		if(pharmacy==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		 if(pharmacistService.isAppointmentAvailableForScheduling(pharmacist,patient,Integer.parseInt(dto.getDuration()), pharmacy, datePart, dt, dt.plusMinutes(Integer.parseInt((dto.getDuration()))))) {
			 try {
				 
				 emailService.sendEmail(dto.getPatientEmail(),"Counseling","You have successfully scheduled an counseling");
				 System.out.println("Mejl je poslat");
				 pharmacistCounselingService.saveAppointment(dto, patient, dt);
				 
			 }catch (Exception e){
		            e.printStackTrace();
		        }
		 }else {
			 System.out.println("Datum NIIIIIJEEE OK");
			 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		 }
		
		return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		
	}
	@PostMapping("/giveOnePenalForPatient/{id}")
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public ResponseEntity<?> giveOnePenalForPatient(@PathVariable String id) {
		String pom = id.substring(9,id.length()).split("k")[0];
		String startDateTime =id.substring(9,id.length()).split("k")[1]; 
		System.out.println("start"+startDateTime);
       	Long ID = Long.parseLong(pom);
       	Patient p = patientService.findPatientById(ID);
       	patientService.giveOnePenalForPatient(p);
       	for(PharmacistCounseling ap: pharmacistCounselingService.findAll()) {
       		if(ap.getPatient().getId().equals(p.getId()) && ap.getStartDateTime().toString().equals(startDateTime)) {      			      	
       			ap.setStartDateTime(LocalDateTime.now());
       			pharmacistCounselingRepository.save(ap);
       			
       		}
       	}
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	@GetMapping("/getPatientWhoIsAtAppointment/{idStart}")
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public User getPatientWhoIsAtAppointment(@PathVariable String idStart) {
		String id = idStart.substring(8,idStart.length()).split("k")[0];
		for (Patient p: patientService.findAll()) {
			if(p.getId().equals(Long.parseLong(id))) {
				System.out.println("Usao u return"+p.getUser().getEmail());
				return p.getUser();
			}
		}
		return null;
		
	}
	@PostMapping("/checkMedicineAvailability/{medicineId}/{email}")
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public Boolean checkMedicineAvailability(@PathVariable String medicineId,@PathVariable String email) {
		System.out.println("Medicine availability");
		Pharmacist pharmacist=null;
		for(Pharmacist d: pharmacistService.findAll()) {
			if(d.getUser().getEmail().equals(email)) {
				pharmacist= d;
			}
			}
		//ne mora localDateTime.now() jer on radi samo u jednoj apoteci
		if(pharmacist.getWorkingTimes()==null) {
			return false;
		}
		Pharmacy pharmacy=pharmacist.getWorkingTimes().getPharmacy(); 
		if(pharmacy==null) {
			System.out.println("APOTEKA JE NULL");
			return false;
		}
		System.out.println("Pharmacy"+pharmacy.getName());
		Boolean result = pharmacistService.isMedicineAvailable(pharmacy,medicineId);
		System.out.println("Result"+result);
		return result;
		
	}
	@GetMapping("/getSpecificationForMedicine/{medicineId}")
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public Optional<Medicine> getSpecificationForMedicine(@PathVariable String medicineId) {
		
		Optional<Medicine> medicine = medicineRepository.findById(Long.parseLong(medicineId));
		return medicine;
		
	}
	@GetMapping("/getSubstituteMedicine/{medicineId}/{idStart}")
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
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
	@GetMapping("/getAllPatientsForSearch")
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public List<User> getAllPatientsForSearch() {
		List<Patient> result = patientService.findAll();
		List<User> users = new ArrayList<User>();
		for(Patient p:result) {
			System.out.println("User"+p.getUser());
			users.add(p.getUser());
		}
		return users;
		
	}
	@GetMapping("/getAvailablePharmacistsByPharmacy/{pharmacy}/{term}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public List<PharamcistForCounselingView> getAvailablePharmacistsByPharmacy(@PathVariable Long pharmacy,@PathVariable String term) {
		System.out.println("pogodjena metoda");
		System.out.println(this.pharmacistCounselingService.getAvailablePharmacistsByPharmacy(pharmacy, term));
		return this.pharmacistCounselingService.getAvailablePharmacistsByPharmacy(pharmacy, term);
		
	}
	
	@GetMapping("/getPharmacistsById/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public Pharmacist getPharmacistsById(@PathVariable Long id) {
		System.out.println("pogodjena metoda");
		System.out.println(this.pharmacistService.getPharmacistsById(id));
		return this.pharmacistService.getPharmacistsById(id);
		
	}
	
	@GetMapping("/getAllPharmacistsPatientCanEvaluate/{email}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public List<RatingView> getAllPharmacistsPatientCanEvaluate(@PathVariable String email) {
		User user = this.userService.findByUsername(email);
		Patient patient=this.patientService.findPatientByUser(user);
		return pharmacistService.getAllPharmacistsPatientCanEvaluate(patient.getId());
		
	}
	 @PostMapping("/changeRating/{rating}/{email}/{id}")
	 @PreAuthorize("hasRole('ROLE_PATIENT')")
	 public ResponseEntity<?> changeRating(@PathVariable int rating,@PathVariable String email,@PathVariable Long id){
		 User user = this.userService.findByUsername(email);
		 Patient patient=this.patientService.findPatientByUser(user);
		 this.pharmacistService.changeRating(rating,patient.getId(),id);
		 return new ResponseEntity<>(HttpStatus.OK);
	 }

	@PostMapping("/savePharmacistComplaint")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> savePharmacistComplaint(@RequestBody ComplaintView complaintView) {
		// pronadjem po kategoriji i posaljem dva podatka za cuvanje  i setovanje
		User user = this.userService.findByUsername(complaintView.getUserName());
		Patient patient=this.patientService.findPatientByUser(user);
// u ovom slucaju complaint on name saljem id dermatologa
		Pharmacist pharmacist=null;
		for(Pharmacist d:pharmacistService.findAll()) {
			if(d.getId().equals(Long.parseLong( complaintView.getComplainedOnName()))) {
				pharmacist= d;
			}
		}

		PharmacistComplaint pharmacistComplaint = new PharmacistComplaint();
		pharmacistComplaint.setPharmacist(pharmacist);
		pharmacistComplaint.setContent(complaintView.getContent());
		pharmacistComplaint.setAnswered(false);
		pharmacistComplaint.setPatient(patient);
		this.complaintService.savePharmacist(pharmacistComplaint);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
