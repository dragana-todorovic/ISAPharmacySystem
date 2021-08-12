package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;

@Service
public class MedicineServiceImpl implements MedicineService{

	@Autowired
	private MedicineRepository medicineRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Override
	public List<Medicine> findAll() {
		List<Medicine> result = medicineRepository.findAll();
		return result;
	}

	@Override
	public Collection<Medicine> searchMedicine(String p) {
		   ArrayList<Medicine> medicines = new ArrayList<>();
		   for(Medicine medicine : medicineRepository.findAll()){
			   if(medicine.getName().equalsIgnoreCase(p)) {
				   System.out.println(p);
				   medicines.add(medicine);
			   }
		   }
	        return medicines;
	}

	@Override
	public Set<MedicineWithQuantity> getMedicinesByPharmacy(String email) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		
		Pharmacy p = pa.getPharmacy();
		System.out.println(p.getMedicineWithQuantity());
		
		return p.getMedicineWithQuantity();
	}

}
