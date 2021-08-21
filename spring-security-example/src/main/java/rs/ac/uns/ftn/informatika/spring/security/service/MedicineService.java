package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MedicineReservationDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.MedicineReservationView;


public interface MedicineService {

	List<Medicine> findAll();
	Collection<Medicine> searchMedicine(String p);
	Medicine save(Medicine medicine);
	Set<MedicineWithQuantity> getMedicinesByPharmacy (String email);
	Medicine findById(Long id);
	Medicine findByName(String name);
	Medicine findByCode(String code);

	
	void saveReservation(MedicineReservationDTO medicineReservationDto);

	


	Set<Medicine> getAllMedicinesExceptExisted (String email);
	
	void addMedicineWithQuatityInPharmacy(String email,String medicineName, int quantity);
	Boolean deleteMedicineFromPharmacy(Long id, String email);


}
