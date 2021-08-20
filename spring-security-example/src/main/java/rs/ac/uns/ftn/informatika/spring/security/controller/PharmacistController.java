package rs.ac.uns.ftn.informatika.spring.security.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacist;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounseling;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.AppointmentDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.CounselingDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.HolidayRequestDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MyPatientDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.StartDateTimeDTO;
import rs.ac.uns.ftn.informatika.spring.security.service.DermatologistAppointmentService;
import rs.ac.uns.ftn.informatika.spring.security.service.DermatologistService;
import rs.ac.uns.ftn.informatika.spring.security.service.EmailService;
import rs.ac.uns.ftn.informatika.spring.security.service.PatientService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacistCounselingService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacistService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;

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
	private EmailService emailService;
	@Autowired
	private PharmacistCounselingService pharmacistCounselingService;
	
	@RequestMapping(value = "/holidayRequest" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> requestHoliday(@RequestBody HolidayRequestDTO holidayRequest) {
		System.out.println("Usao u kontroler");
		this.pharmacistService.saveHolidayRequest(holidayRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	
	
	}
	@RequestMapping(value = "/getStartDateTime" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public LocalDateTime getStartDateTime(@RequestBody StartDateTimeDTO startDateTimeDTO) {
		for(PharmacistCounseling d: pharmacistCounselingService.findAll()) {
			if(d.getPharmacist().getUser().getEmail().equals(startDateTimeDTO.getDermatologistEmail()) && d.getPatient().getUser().getEmail().equals(startDateTimeDTO.getPatientEmail()))
				return d.getStartDateTime();
		}
		return null;
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

       			return drugsOnWhichPatientIsNotAlergic;
       		       	
       		       	} catch (Exception e) {
       		    		return medicines; 
       		    	}
       		}
       	}

		return null;
		
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
		String pom = id.substring(8,id.length());
       	//int ID = Integer.parseInt(pom);
       	System.out.println(pom);
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
		System.out.println("PACIJENTIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII "+myPatientsDtos);
		return myPatientsDtos;
		
	}
	@RequestMapping(value = "/saveAppointment" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public ResponseEntity<?> saveAppointment(@RequestBody AppointmentDTO appointmentDTO) {
		System.out.println("Appointmant"+ appointmentDTO.getPatientEmail()+appointmentDTO.getPatientId());
		this.pharmacistService.saveAppointment(appointmentDTO);
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
	@RequestMapping(value = "/scheduleAnAppointment" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> scheduleAnAppointment(@RequestBody CounselingDTO dto)  {
		String startDate;
		startDate = dto.getStartDate().replace('/', '-');	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String start = LocalDate.parse(startDate, formatter).format(formatter2);
		LocalDate datePart = LocalDate.parse(start);
		LocalTime timePart = LocalTime.parse(dto.getStartTime());
		LocalDateTime dt = LocalDateTime.of(datePart, timePart);
		if(dt.isBefore(LocalDateTime.now())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Pharmacist pharmacist=null;
		LocalDate startDateDPharmacist=dt.toLocalDate();
		
		for(Pharmacist d: pharmacistService.findAll()) {
			if(d.getUser().getEmail().equals(dto.getPharmacistEmail())) {
				pharmacist= d;
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
		Pharmacy pharmacy=pharmacist.getWorkingTimes().getPharmacy();
		if(pharmacy==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
		 if(pharmacistService.isAppointmentAvailableForScheduling(pharmacist,patient,Integer.parseInt(dto.getDuration()), pharmacy, datePart, dt, dt.plusMinutes(Integer.parseInt((dto.getDuration()))))) {
			 System.out.println("Datum je u redu");
			 try {
				 
				 emailService.sendEmailForRecoveryOfAccount(dto.getPatientEmail());
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
	}
	
}
