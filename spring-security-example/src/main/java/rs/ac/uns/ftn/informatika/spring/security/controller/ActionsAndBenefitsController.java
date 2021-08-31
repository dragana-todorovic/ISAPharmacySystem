package rs.ac.uns.ftn.informatika.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.spring.security.model.ActionAndBenefit;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.view.ActionAndBenefitDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.EditPharmacyView;

@RestController
@RequestMapping(value = "/actionsAndBenefits", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActionsAndBenefitsController {
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@PostMapping("/addNew")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> editPharmacy(@RequestBody ActionAndBenefitDTO actionAndBenefit) {
		
		ActionAndBenefit ab = this.pharmacyService.addNew(actionAndBenefit);
		if(ab == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(ab, HttpStatus.CREATED);
		
	}

	

}
