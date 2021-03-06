package rs.ac.uns.ftn.informatika.spring.security.controller;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.util.UriComponentsBuilder;

import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.repository.PatientRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyAdminRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.PatientService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.view.UserRegisterView;

// Primer kontrolera cijim metodama mogu pristupiti samo autorizovani korisnici
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class  UserController {

	@Autowired
	private UserService userService;
	private PharmacyAdminService pharmacyAdminService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private PharmacyAdminRepository pharmacyAdminRepository;
	
	@Autowired
	private PatientRepository patientRepostitory;



	// Za pristup ovoj metodi neophodno je da ulogovani korisnik ima ADMIN ulogu
	// Ukoliko nema, server ce vratiti gresku 403 Forbidden
	// Korisnik jeste autentifikovan, ali nije autorizovan da pristupi resursu
	@GetMapping("/user/{userId}")
	@PreAuthorize("hasRole('ADMIN')"  )
	public User loadById(@PathVariable Long userId) {
		return this.userService.findById(userId);
	}
	
	@GetMapping("/getByEmail/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY') || hasRole('ADMIN_SYSTEM') || hasRole('ROLE_DERMATOLOGIST') || hasRole('ROLE_PHARMACIST') || hasRole('ROLE_SUPPLIER')")
	public User getByEmail(@PathVariable String email) {
		System.out.println(email);
		return this.userService.findByEmail(email);
	}


	@GetMapping("/user/all")
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> loadAll() {
		return this.userService.findAll();
	}

	@GetMapping("/whoami")
	@PreAuthorize("hasRole('USER')")
	public User user(Principal user) {
		return this.userService.findByUsername(user.getName());
	}
	
	@GetMapping("/foo")
    public Map<String, String> getFoo() {
        Map<String, String> fooObj = new HashMap<>();
        fooObj.put("foo", "bar");
        return fooObj;
    }

	@GetMapping("/profilePatient/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')  || hasRole('ADMIN_SYSTEM')"+"|| hasRole('ROLE_PHARMACIST')"
	        +
	        "|| hasRole('ROLE_DERMATOLOGIST')")
	public ResponseEntity<User> pharmacistDetails(@PathVariable(name="id") String id)  {
		User existUser = this.userService.findByUsername(id);
		
		return new ResponseEntity<User>(existUser,HttpStatus.OK);
	}
	
	
	@PostMapping("/editProfile")
	public ResponseEntity<?> editUser(@RequestBody UserRegisterView userRequest) {
		userService.editPersonalData(userRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
