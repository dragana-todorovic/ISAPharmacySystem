package rs.ac.uns.ftn.informatika.spring.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.service.AppointmentService;

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
	

}
