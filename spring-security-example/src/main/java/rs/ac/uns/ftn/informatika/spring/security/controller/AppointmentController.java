package rs.ac.uns.ftn.informatika.spring.security.controller;

import java.util.ArrayList;
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
import rs.ac.uns.ftn.informatika.spring.security.service.AppointmentPriceService;
import rs.ac.uns.ftn.informatika.spring.security.service.AppointmentService;
import rs.ac.uns.ftn.informatika.spring.security.service.DermatologistService;
import rs.ac.uns.ftn.informatika.spring.security.service.WorkingTimeService;
import rs.ac.uns.ftn.informatika.spring.security.view.AvaliableDermatologistAppointmentsView;
import rs.ac.uns.ftn.informatika.spring.security.view.PredefinedAppointmentDTO;

@Controller
@RestController
@RequestMapping(value="/appointment", produces=MediaType.APPLICATION_JSON_VALUE)
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private AppointmentPriceService appointmentPriceService;
	@Autowired
	private DermatologistService dermatologistService;
	
	
	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ROLE_PATIENT')  || hasRole('ROLE_DERMATOLOGIST')")
	public  List<DermatologistAppointment> loadAll() {
		return this.appointmentService.findAll();
	}
	
	@GetMapping("/getAvailableAppointmentsByPharmacyId/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')  || hasRole('ROLE_DERMATOLOGIST')")
	public  List<AvaliableDermatologistAppointmentsView> getAvailableAppointmentsByPharmacyId(@PathVariable(name="id") Long id) {
		System.out.println("pogodjena metoda");
		List<AvaliableDermatologistAppointmentsView> result=new ArrayList<AvaliableDermatologistAppointmentsView>();
		for(DermatologistAppointment da: appointmentService.getAvailableAppointmentsByPharmacyId(id)) {
			String date=da.getStartDateTime().toString().split("T")[0];
			String time=da.getStartDateTime().toString().split("T")[1];
			String duration=Integer.toString(da.getDuration());
			String price=Double.toString(appointmentPriceService.getPriceByAppointment(da));
			String rating=Double.toString(dermatologistService.getAvrageGrade(da.getDermatologist()));
			AvaliableDermatologistAppointmentsView view=new AvaliableDermatologistAppointmentsView(da.getId(),da.getDermatologist().getUser().getFirstName(),da.getDermatologist().getUser().getLastName(),date,time,duration,price,rating);
			result.add(view);
		}
		return result;
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
	
	@PostMapping("/scheduleDermatologistAppointment/{pom}")
	@PreAuthorize("hasRole('USER_PATIENT')")
	public  ResponseEntity<?> scheduleDermatologistAppointment(@PathVariable(name="pom") Long pom,@RequestBody String patient) {
		if(appointmentService.scheduleDermatologistAppointment(pom,patient))
			return new ResponseEntity<>(HttpStatus.OK);
		else 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	

}
