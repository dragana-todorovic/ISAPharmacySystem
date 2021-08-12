package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;


public interface MedicineService {

	List<Medicine> findAll();
	Collection<Medicine> searchMedicine(String p);
	Set<MedicineWithQuantity> getMedicinesByPharmacy (String email);

}
