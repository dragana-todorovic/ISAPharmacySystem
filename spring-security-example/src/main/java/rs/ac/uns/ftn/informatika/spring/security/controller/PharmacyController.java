package rs.ac.uns.ftn.informatika.spring.security.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyAdminRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.view.UserRegisterView;

@RestController
@RequestMapping(value = "/pharmacy", produces = MediaType.APPLICATION_JSON_VALUE)
public class PharmacyController {

	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	@Autowired
	private PharmacyService pharmacyService;
	
	@PostMapping("/getPharmacyByAdmin")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Optional<Pharmacy> getBy(@RequestBody User user) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(user);
		System.out.println(pa);
		return pharmacyService.findById(pa.getPharmacy().getId());
		
	}
	
	@PostMapping("/editPharmacy")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> editPharmacy(@RequestBody Pharmacy pharmacy) {
		
		pharmacyService.editPharmacy(pharmacy);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}

}
	
