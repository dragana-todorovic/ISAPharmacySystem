package rs.ac.uns.ftn.informatika.spring.security.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MyPatientDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.PatientAndDermatologistDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.StartDateTimeDTO;
import rs.ac.uns.ftn.informatika.spring.security.repository.UserRepository;

import java.util.regex.Pattern;

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

import rs.ac.uns.ftn.informatika.spring.security.model.ChangePassword;
import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistComplaint;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.AppointmentDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.AppointmentScheduleDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.HolidayRequestDTO;
import rs.ac.uns.ftn.informatika.spring.security.service.DermatologistAppointmentService;
import rs.ac.uns.ftn.informatika.spring.security.service.DermatologistService;
import rs.ac.uns.ftn.informatika.spring.security.service.EmailService;
import rs.ac.uns.ftn.informatika.spring.security.service.PatientService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;

@RestController
@RequestMapping(value = "/derm", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
public class DermatologistController {
	@Autowired
	private DermatologistService dermatologistService;
	@Autowired
	private UserService userService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private PharmacyService pharmacyService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private DermatologistAppointmentService dermatologistAppointmentService;
	
	@RequestMapping(value = "/holidayRequest" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> requestHoliday(@RequestBody HolidayRequestDTO holidayRequest) {
		this.dermatologistService.saveHolidayRequest(holidayRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@RequestMapping(value = "/getStartDateTime" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public LocalDateTime getStartDateTime(@RequestBody StartDateTimeDTO startDateTimeDTO) {
		for(DermatologistAppointment d: dermatologistAppointmentService.findAll()) {
			if(d.getDermatologist().getUser().getEmail().equals(startDateTimeDTO.getDermatologistEmail()) && d.getPatient().getUser().getEmail().equals(startDateTimeDTO.getPatientEmail()))
				return d.getStartDateTime();
		}
		return null;
	}

	
	@GetMapping("/getPatientsForAppointment/{email}")
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public List<MyPatientDTO> getPatientsForAppointment(@PathVariable String email) {
		System.out.println(email);
		List<MyPatientDTO> myPatientsDtos;
		myPatientsDtos=this.dermatologistService.getPatientsForAppointment(email);
		System.out.println("PACIJENTIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII "+myPatientsDtos);
		return myPatientsDtos;
		
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
		String pom = id.substring(8,id.length());
       	//int ID = Integer.parseInt(pom);
       	System.out.println(pom);
       	Long ID = Long.parseLong(pom);
       	Patient p = patientService.findPatientById(ID);
       	System.out.println(p.getUser().getFirstName());
       	System.out.println(p.getUser().getEmail());
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

	@RequestMapping(value = "/saveAppointment" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_DERMATOLOGIST')")
	public ResponseEntity<?> saveAppointment(@RequestBody AppointmentDTO appointmentDTO) {
		System.out.println("Appointmant"+ appointmentDTO.getPatientEmail()+appointmentDTO.getPatientId());
		this.dermatologistService.saveAppointment(appointmentDTO);
		return new ResponseEntity<>(HttpStatus.OK);
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
	@RequestMapping(value = "/scheduleAnAppointment" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> scheduleAnAppointment(@RequestBody AppointmentScheduleDTO dto)  {
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
				 
				 emailService.sendEmailForRecoveryOfAccount(dto.getPatientEmail());
				 System.out.println("Mejl je poslat");
				 dermatologistAppointmentService.saveAppointment(dto, patient, dt);
				 
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
