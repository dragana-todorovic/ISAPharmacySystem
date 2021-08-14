package rs.ac.uns.ftn.informatika.spring.security.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.spring.security.model.MedicineOrder;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineOrderService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;

@RestController
@RequestMapping(value = "/medicineOrder", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicineOrderController {
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	@Autowired
	private PharmacyService pharmacyService;
	@Autowired
	private UserService userService;
	@Autowired
	private MedicineOrderService medicineOrderService;
	
	@GetMapping("/getAllMedicineOrderByPharmacy/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public List<MedicineOrder> getAll(@PathVariable(name="email") String email) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy pharmacy = pa.getPharmacy();
		System.out.println(pharmacy.getId());
		
		return this.medicineOrderService.findMedicineOrdersByPharmacy(pharmacy.getId());
		
	}

}
