package rs.ac.uns.ftn.informatika.spring.security.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rs.ac.uns.ftn.informatika.spring.security.model.UserTokenState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import rs.ac.uns.ftn.informatika.spring.security.exception.ResourceConflictException;
import rs.ac.uns.ftn.informatika.spring.security.model.Authority;
import rs.ac.uns.ftn.informatika.spring.security.model.ChangePassword;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.service.AuthorityService;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyService;
import rs.ac.uns.ftn.informatika.spring.security.view.UserRegisterView;
import rs.ac.uns.ftn.informatika.spring.security.model.UserTokenState;
import rs.ac.uns.ftn.informatika.spring.security.security.TokenUtils;
import rs.ac.uns.ftn.informatika.spring.security.security.auth.JwtAuthenticationRequest;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.service.impl.CustomUserDetailsService;
import rs.ac.uns.ftn.informatika.spring.security.model.UserTokenState;

//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MedicineService medicineService;
	
	@Autowired
	private PharmacyService pharmacyService;

	@Autowired
	private AuthorityService authorityService;
	// Prvi endpoint koji pogadja korisnik kada se loguje.
	// Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
	@PostMapping("/login")
	public ResponseEntity<UserTokenState> createAuthenticationToken( JwtAuthenticationRequest authenticationRequest,
																	 HttpServletResponse response) {
		// sa fronta kao username prosledjujem email - authenticationRequest.getUsername()
		Authentication authentication = authenticationManager
				.authenticate( new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
						authenticationRequest.getPassword()));

		// Ubaci korisnika u trenutni security kontekst
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token za tog korisnika
		User user = (User) authentication.getPrincipal();
		String userRole = this.authorityService.findAuthorityIdByUserId(user.getId());
		
		String jwt = tokenUtils.generateToken(user.getId(),user.getEmail(),userRole);
		int expiresIn = tokenUtils.getExpiredIn();
		// Vrati token kao odgovor na uspesnu autentifikaciju
		return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
	}

	@GetMapping("/checkIfLogged")
	public ResponseEntity<?> checkIfLogged(JwtAuthenticationRequest authenticationRequest){
		Authentication authentication = authenticationManager
				.authenticate( new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
						authenticationRequest.getPassword()));

		// Ubaci korisnika u trenutni security kontekst
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token za tog korisnika
		User user = (User) authentication.getPrincipal();
			if(!user.getLogged()) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			} else {
				return new ResponseEntity<>(HttpStatus.OK);
			}
	}

	// Endpoint za registraciju novog korisnika
	@PostMapping("/register")
	public ResponseEntity<User> addUser(@RequestBody UserRegisterView userRequest, UriComponentsBuilder ucBuilder) {
		User existUser = this.userService.findByUsername(userRequest.getUsername());
		User existUserByEmail = this.userService.findByEmail(userRequest.getEmail());
		if (existUserByEmail != null) {
			//log.warn("User with this email already exists");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			//return null;
			//	throw new ResourceConflictException(userRequest.getId(), "Username already exists");
		}

		User user = this.userService.save(userRequest);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	@GetMapping(value = "/searchPharmacies/{let}",produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Pharmacy> searchPharmacies(@PathVariable("let") String let) {
		return pharmacyService.searchPharmacy(let);
	}
	@GetMapping(value = "/searchMedicine/{let}",produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Medicine> searchMedicine(@PathVariable("let") String let) {
		return medicineService.searchMedicine(let);
	}
/*
	@PostMapping("/registerAdminSystem")
	public ResponseEntity<User> registerAdminSystem(@RequestBody UserRegisterView userRequest, UriComponentsBuilder ucBuilder) {
		User existUser = this.userService.findByUsername(userRequest.getEmail());
		//	User existUserByEmail = this.userService.findByEmail(userRequest.getEmail());
		if (existUser != null) {
			//log.warn("User with this email already exists");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	//	User user = this.userService.saveUserAdminSystem(userRequest);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}*/

	@GetMapping(value = "/checkEmail/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> checkEmail(@PathVariable("email") String email) {
		User existUserByEmail = this.userService.findByEmail(email +".com");
		if (existUserByEmail != null) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// U slucaju isteka vazenja JWT tokena, endpoint koji se poziva da se token osvezi
	@PostMapping(value = "/refresh")
	public ResponseEntity<UserTokenState> refreshAuthenticationToken(HttpServletRequest request) {

		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);

		if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
			String refreshedToken = tokenUtils.refreshToken(token);
			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
		} else {
			UserTokenState userTokenState = new UserTokenState();
			return ResponseEntity.badRequest().body(userTokenState);
		}
	}

	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
		userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}
	@GetMapping("/profilePharmacist/{id}")
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public ResponseEntity<User> pharmacistDetails(@PathVariable(name="id") String id)  {
		User existUser = this.userService.findByUsername(id);
		
		return new ResponseEntity<User>(existUser,HttpStatus.OK);
	}
	@RequestMapping(value = "/changePassword" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> changePassword(@RequestBody ChangePassword change) {
		System.out.println(change);
		String regexEmail = "^([_a-zA-Z0-9-]+)@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6})?$";
		Pattern patternEmail = Pattern.compile(regexEmail);
        Matcher matcherEmail = patternEmail.matcher(change.getEmail());
        
        String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern patternPassword = Pattern.compile(regexPassword);
        Matcher matcherPassword = patternPassword.matcher(change.getNewPass());
        
        if(matcherEmail.matches() && matcherPassword.matches() && change.getNewPass().equals(change.getConfirmPass())) {
		Boolean result = userService.changePassword(change.getEmail(), change.getNewPass());
		if(result==false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	else {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
	}	
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}
}