package rs.ac.uns.ftn.informatika.spring.security.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.spring.security.model.*;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineOrderRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineWithQuantityRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineOrderService;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyService;
import rs.ac.uns.ftn.informatika.spring.security.service.SuplierOfferService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.view.MedicineForOrderView;
import rs.ac.uns.ftn.informatika.spring.security.view.NewOrderDTO;

@RestController
@RequestMapping(value = "/medicineOrder", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicineOrderController {
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	@Autowired
	private MedicineWithQuantityRepository medicineWithQuantityRepository;
	@Autowired
	private PharmacyService pharmacyService;
	@Autowired
	private UserService userService;
	@Autowired
	private MedicineOrderService medicineOrderService;
	
	@Autowired
	private MedicineService medicineService;
	
	@Autowired
	private SuplierOfferService suplierOfferService;
	
	@GetMapping("/getAllMedicineOrderByPharmacy/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public List<MedicineOrder> getAll(@PathVariable(name="email") String email) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy pharmacy = pa.getPharmacy();
		System.out.println(pharmacy.getId());
		
		return this.medicineOrderService.findMedicineOrdersByPharmacy(pharmacy.getId());
		
	}
	
	@GetMapping("/getAllOffersByOrder/{id}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public List<SuplierOffer> getSuplierOffers(@PathVariable(name="id") String id) {
		
		return this.suplierOfferService.findOffersByOrder(Long.parseLong(id));
		
	}
	
	@PostMapping("/addMedicineToOffer/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> add(@PathVariable(name="email") String email,
			@RequestBody NewOrderDTO newOrder) {
		
		
		String date = newOrder.getDate();
		LocalDate datePart = LocalDate.parse(date);
		LocalTime timePart = LocalTime.parse(newOrder.getTime());
		LocalDateTime dt = LocalDateTime.of(datePart, timePart);
		
		Set<MedicineWithQuantity> medicinesWithQuantity = new HashSet<MedicineWithQuantity>();
		
		for(MedicineForOrderView m : newOrder.getMedicines()) {
			MedicineWithQuantity mq = new MedicineWithQuantity();

			mq.setMedicine(this.medicineService.findById(Long.parseLong(m.getMedicineId())));
			mq.setQuantity(Integer.parseInt(m.getQuantity()));
			medicinesWithQuantity.add(mq);
			this.medicineWithQuantityRepository.save(mq);
			
		}
		
		this.medicineOrderService.createNewMedicineOrder(email, medicinesWithQuantity, dt);
		
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@PostMapping("/acceptOffer/{id}/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> acceptOffer(@PathVariable(name="id") String id,@PathVariable(name="email") String email) {
		if(this.medicineOrderService.acceptSuplierOffer(email, Long.parseLong(id))) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}


	@GetMapping("/getAllOrders")
	@PreAuthorize("hasRole('ROLE_SUPPLIER')")
	public List<MedicineOrder> getAllOrders()   {
		return this.medicineOrderService.findAll();
	}

	
	@GetMapping("/getMedicineFromOrder/{id}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<MedicineWithQuantity> getMedicineFromOrder(@PathVariable(name="id") String id) {
		return this.medicineOrderService.getMedicinesByOrder(Long.parseLong(id));
	}
	

	@PostMapping("/editMedicineOrder/{email}/{id}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> editMedicineOrder(@PathVariable(name="email") String email,@PathVariable(name="id") String id,
			@RequestBody NewOrderDTO newOrder) {
		
		System.out.println(newOrder);
		String date = newOrder.getDate();
		LocalDate datePart = LocalDate.parse(date);
		LocalTime timePart = LocalTime.parse(newOrder.getTime());
		LocalDateTime dt = LocalDateTime.of(datePart, timePart);
		
		Set<MedicineWithQuantity> medicinesWithQuantity = new HashSet<MedicineWithQuantity>();
		
		for(MedicineForOrderView m : newOrder.getMedicines()) {
			MedicineWithQuantity mq = new MedicineWithQuantity();

			mq.setMedicine(this.medicineService.findById(Long.parseLong(m.getMedicineId())));
			mq.setQuantity(Integer.parseInt(m.getQuantity()));
			medicinesWithQuantity.add(mq);
			this.medicineWithQuantityRepository.save(mq);
			
		}
		
		this.medicineOrderService.editMedicineOrder(email, Long.parseLong(id), medicinesWithQuantity, dt);
		
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@GetMapping("/orderHaveOffer/{id}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> orderHaveOffer(@PathVariable(name="id") String id) {
		if(this.medicineOrderService.orderHaveOffers(Long.parseLong(id))) {
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/deleteMedicineOrder/{email}/{id}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> deleteMedicineOrder(@PathVariable(name="email") String email,@PathVariable(name="id") String id) {
		this.medicineOrderService.deleteMedicineOrder(email,Long.parseLong(id));
		return new ResponseEntity<>(HttpStatus.OK);
	}



}
