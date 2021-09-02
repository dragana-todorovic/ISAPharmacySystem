package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicinePrice;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservation;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MedicineReservationDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.MedicineReservationView;
import rs.ac.uns.ftn.informatika.spring.security.view.RatingView;


public interface MedicineService {

	List<Medicine> findAll();
	Collection<Medicine> searchMedicine(String p);
	Medicine save(Medicine medicine);
	Set<MedicineWithQuantity> getMedicinesByPharmacy (String email);
	Medicine findById(Long id);
	Medicine findByName(String name);
	Medicine findByCode(String code);
	
	
		
	Set<Medicine> getAllMedicinesExceptExisted (String email);
	
	void addMedicineWithQuatityInPharmacy(String email,String medicineName, int quantity);
	Boolean deleteMedicineFromPharmacy(Long id, String email);
	void editMedicineWithQuatityInPharmacy(String email, long id, int quantity);
	Set<Medicine> getAllMedicinePricesExpectedExsitedInPriceList(String email, List<Long> existed, String priceListId);
	List<RatingView> getAllMedicinePatientCanEvaluate(long patient);
	void changeRating(int rating,long patient,Long id);
}
