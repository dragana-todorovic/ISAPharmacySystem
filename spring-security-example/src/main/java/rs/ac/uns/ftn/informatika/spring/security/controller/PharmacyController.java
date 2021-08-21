package rs.ac.uns.ftn.informatika.spring.security.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
import rs.ac.uns.ftn.informatika.spring.security.model.HolidayRequest;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicinePrice;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacist;

import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.PriceList;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.WorkingDay;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicinePriceRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyAdminRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyService;
import rs.ac.uns.ftn.informatika.spring.security.service.PriceListService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.view.EditPharmacyView;
import rs.ac.uns.ftn.informatika.spring.security.view.MedicineForOrderView;
import rs.ac.uns.ftn.informatika.spring.security.view.MedicinePriceDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.NewDermatologistDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.NewOrderDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.NewPharmacistDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.PriceListDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.UserRegisterView;

@RestController
@RequestMapping(value = "/pharmacy", produces = MediaType.APPLICATION_JSON_VALUE)
public class PharmacyController {

	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	@Autowired
	private PharmacyService pharmacyService;
	@Autowired
	private MedicineService medicineService;
	@Autowired
	private PriceListService priceListService;
	@Autowired
	private MedicinePriceRepository medicinePriceRepository;
	
	@PostMapping("/getPharmacyByAdmin")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Optional<Pharmacy> getBy(@RequestBody User user) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(user);
		System.out.println(pa);
		return pharmacyService.findById(pa.getPharmacy().getId());
		
	}
	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ROLE_PATIENT') || hasRole('ADMIN_PHARMACY') || hasRole('ADMIN_SYSTEM')")
	public List<Pharmacy> getAll() {
		return this.pharmacyService.findAll();
		
	}
	@GetMapping("/getPharmacyById/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public Optional<Pharmacy> getPharamcyById(@PathVariable(name="id") Long id) {
		return this.pharmacyService.findById(id);
		
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
		if(this.pharmacyService.deleteDermatologistFromPharmacy(id, email)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		
		
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
		if(this.pharmacyService.deletePharmacistFromPharmacy(id, email)) {
		return new ResponseEntity<>(HttpStatus.OK);
	} else {
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
		
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
	
	
	@PostMapping("/addDermatologistInPharmacy/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> addDermatologistInPharmacy(@PathVariable(name="email") String email,@RequestBody NewDermatologistDTO newDermatologist) {
		System.out.println(newDermatologist);
		if(this.pharmacyService.addDermatologistInPharmacy(email, newDermatologist)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		
		
		
	}
	
	@PostMapping("/addPharmacistInPharmacy/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> addPharmacistInPharmacy(@PathVariable(name="email") String email,@RequestBody NewPharmacistDTO newPharmacist) {
		this.pharmacyService.addPharmacistInPharmacy(email, newPharmacist);
		System.out.println("$$$$$$$$$$$$" + newPharmacist);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	
	/*@PostMapping("/addWorkingDayPharmacist/{id}/{email}/{workingDay}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> addWorkingDayPharmacist(@PathVariable(name="id") String id,@PathVariable(name="email") String email,
			@PathVariable(name="workingDay") String workingDay,@RequestBody WorkingTimeIntervalDTO wd) {
		System.out.println(wd);
		this.pharmacyService.addWorkingTimeForPharmacist(id, email, workingDay, wd);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}*/
	
	
	@GetMapping("/getMedicinePriceList/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public List<PriceList> getMedicinePriceList(@PathVariable(name="email") String email) {
		return this.priceListService.findPriceListByPharmacy(email);
	}
	

	@GetMapping("/getListMedicines/{id}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<MedicinePrice> getMedicinePrice(@PathVariable(name="id") String id) {
		return this.priceListService.findMedicinePricesByPriceListId(Long.parseLong(id));
	}
	
	
	@PostMapping("/addMedicinePriceToPriceList/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> add(@PathVariable(name="email") String email,
			@RequestBody PriceListDTO priceList) {
		
		
		LocalDate date = LocalDate.parse(priceList.getDate());
		
		Set<MedicinePrice> medicinePrices = new HashSet<MedicinePrice>();
		
		for(MedicinePriceDTO m : priceList.getMedicines()) {
			MedicinePrice mp = new MedicinePrice();
			mp.setMedicine(this.medicineService.findById(Long.parseLong(m.getMedicineId())).get());
			mp.setPrice(Double.parseDouble(m.getPrice()));
			medicinePrices.add(mp);
			this.medicinePriceRepository.save(mp);
			
		}
		this.priceListService.createNewPriceList(email, medicinePrices, date);
		
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@GetMapping("/getHolidayRequests/{id}/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<HolidayRequest> getHolidayRequests(@PathVariable(name="id") String id,@PathVariable(name="email") String email) {
		return this.pharmacyService.getHolidayRequestsByPharmacy(Long.parseLong(id),email);
	}
	
	@PostMapping("/acceptHolidayRequest/{id}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> acceptHolidayRequest(@PathVariable(name="id") String id) {
		this.pharmacyService.acceptHolidayRequest(Long.parseLong(id));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@PostMapping("/declineHolidayRequest/{id}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> declineHolidayRequest(@PathVariable(name="id") String id) {
		this.pharmacyService.declineHolidayRequest(Long.parseLong(id));
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
	
