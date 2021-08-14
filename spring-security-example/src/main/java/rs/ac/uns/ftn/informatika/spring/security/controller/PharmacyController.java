package rs.ac.uns.ftn.informatika.spring.security.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacist;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.WorkingDay;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyAdminRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.view.EditPharmacyView;
import rs.ac.uns.ftn.informatika.spring.security.view.UserRegisterView;
import rs.ac.uns.ftn.informatika.spring.security.view.WorkingTimeIntervalDTO;

@RestController
@RequestMapping(value = "/pharmacy", produces = MediaType.APPLICATION_JSON_VALUE)
public class PharmacyController {

	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	@Autowired
	private PharmacyService pharmacyService;
	@Autowired
	private MedicineService medicineService;
	
	@PostMapping("/getPharmacyByAdmin")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Optional<Pharmacy> getBy(@RequestBody User user) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(user);
		System.out.println(pa);
		return pharmacyService.findById(pa.getPharmacy().getId());
		
	}
	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public List<Pharmacy> getAll() {
		System.out.println("Pogodjena medota");
		return this.pharmacyService.findAll();
		
	}
	
	@PostMapping("/editPharmacy")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> editPharmacy(@RequestBody EditPharmacyView pharmacy) {
		
		pharmacyService.editPharmacy(pharmacy);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllMedicinesWithQuantity/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<MedicineWithQuantity> getMedicines(@PathVariable(name="email") String email) {
		return this.medicineService.getMedicinesByPharmacy(email);
		
	}
	
	@GetMapping("/getAllDermatologist/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<Dermatologist> getDermatologsts(@PathVariable(name="email") String email) {
		return this.pharmacyService.getDermatologistsByPharmacyAdmin(email);
		
	}
	

	@PostMapping("/addWorkingDayForDermatologist/{id}/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> addWorkingTime(@PathVariable(name="id") String id,
			@PathVariable(name="email") String email,@RequestBody WorkingTimeIntervalDTO workingDay) {
	//	this.pharmacyService.addWorkingTimeForDermatologist(id, email, workingDay);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllWorkingTimes/{id}/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<WorkingDay> getAllWorkingtimes(@PathVariable(name="id") String id,
			@PathVariable(name="email") String email) {
		return this.pharmacyService.getWorkingDayForDermatolog(id, email);
		
	}
	
	@GetMapping("/deleteDermatologist/{id}/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> deleteDermatologist(@PathVariable(name="id") String id,
			@PathVariable(name="email") String email) {
		this.pharmacyService.deleteDermatologistFromPharmacy(id, email);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllPharmacists/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<Pharmacist> getPharmacists(@PathVariable(name="email") String email) {
		return this.pharmacyService.getPharmacistssByPharmacyAdmin(email);
		
	}
	
	@GetMapping("/getAllWorkingTimesPharmacist/{id}/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<WorkingDay> getAllWorkingtimesPharmacist(@PathVariable(name="id") String id,
			@PathVariable(name="email") String email) {
		return this.pharmacyService.getWorkingDayForPharmacist(id, email);
		
	}
	
	@GetMapping("/deletePharmacist/{id}/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> deletePharmacist(@PathVariable(name="id") String id,
			@PathVariable(name="email") String email) {
		this.pharmacyService.deletePharmacistFromPharmacy(id, email);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllMedicineExceptAlreadyExisted/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<Medicine> getAllMedicineExceptAlreadyExisted(@PathVariable(name="email") String email) {
		return this.medicineService.getAllMedicinesExceptExisted(email);
		
	}
	
	@PostMapping("/addMedicineWithQuantityInPharmacy/{email}/{medicineName}/{quantity}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> getAllMedicineExceptAlreadyExisted(@PathVariable(name="email") String email,
			@PathVariable(name="medicineName") String medicineName,@PathVariable(name="quantity") String quantity) {
		int q = Integer.parseInt(quantity);
		this.medicineService.addMedicineWithQuatityInPharmacy(email, medicineName, q);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/deleteMedicineFromPharmacy/{id}/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> deleteMedicineFromPharmacy(@PathVariable(name="id") String id,
			@PathVariable(name="email") String email) {
		Long medicineId = Long.parseLong(id);
		if(this.medicineService.deleteMedicineFromPharmacy(medicineId, email)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
	

	@GetMapping("/getAllDermatologistsExpectAlreadyExisted/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<Dermatologist> getDermatologists(@PathVariable String email) {
		return this.pharmacyService.getAllDermatologistExpectAlreadyExisted(email);
	}
	

	@GetMapping("/getAllPharmacistsExpectAlreadyExisted/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<Pharmacist> getPharmacist(@PathVariable String email) {
		return this.pharmacyService.getAllPharmacistsExpectAlreadyExisted(email);
	}
	
	
	@PostMapping("/addDermatologistInPharmacy/{email}/{id}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> addDermatologistInPharmacy(@PathVariable(name="email") String email,@PathVariable(name="id") String id) {
		this.pharmacyService.addDermatologistInPharmacy(email, Long.parseLong(id));
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@PostMapping("/addPharmacistInPharmacy/{email}/{id}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> addPharmacistInPharmacy(@PathVariable(name="email") String email,@PathVariable(name="id") String id) {
		this.pharmacyService.addPharmacistInPharmacy(email, Long.parseLong(id));
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@PostMapping("/addWorkingDayDermatologist/{id}/{email}/{workingDay}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> addWorkingDayDermatologist(@PathVariable(name="id") String id,@PathVariable(name="email") String email,
			@PathVariable(name="workingDay") String workingDay,@RequestBody WorkingTimeIntervalDTO wd) {
		System.out.println(wd);
		this.pharmacyService.addWorkingTimeForDermatologist(id, email, workingDay, wd);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@PostMapping("/addWorkingDayPharmacist/{id}/{email}/{workingDay}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> addWorkingDayPharmacist(@PathVariable(name="id") String id,@PathVariable(name="email") String email,
			@PathVariable(name="workingDay") String workingDay,@RequestBody WorkingTimeIntervalDTO wd) {
		System.out.println(wd);
		this.pharmacyService.addWorkingTimeForPharmacist(id, email, workingDay, wd);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
}
	
