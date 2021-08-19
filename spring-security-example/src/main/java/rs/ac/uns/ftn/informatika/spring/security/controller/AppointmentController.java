package rs.ac.uns.ftn.informatika.spring.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.service.AppointmentService;
import rs.ac.uns.ftn.informatika.spring.security.view.PredefinedAppointmentDTO;

@Controller
@RestController
@RequestMapping(value="/appointment", produces=MediaType.APPLICATION_JSON_VALUE)
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	
	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ROLE_PATIENT')  || hasRole('ROLE_DERMATOLOGIST')")
	public  List<DermatologistAppointment> loadAll() {
		return this.appointmentService.findAll();
	}
	
	@GetMapping("/getAllByPharmacyId/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')  || hasRole('ROLE_DERMATOLOGIST')")
	public  List<DermatologistAppointment> getAllByPharmacyId(@PathVariable(name="id") Long id) {
		System.out.println("pogodjena metoda");
		return null;
	}
	
	@PostMapping("/createPredefinedAppointmet/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public  ResponseEntity<?> createPredefinedAppointmet(@PathVariable(name="email") String email,
			@RequestBody PredefinedAppointmentDTO dto) {
		if(this.appointmentService.createPredefinedExamination(email, dto)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	

}
