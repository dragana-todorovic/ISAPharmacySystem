package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import rs.ac.uns.ftn.informatika.spring.security.model.*;
import rs.ac.uns.ftn.informatika.spring.security.view.ActionAndBenefitDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.EditPharmacyView;
import rs.ac.uns.ftn.informatika.spring.security.view.NewDermatologistDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.NewPharmacistDTO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import rs.ac.uns.ftn.informatika.spring.security.model.ActionAndBenefit;

import rs.ac.uns.ftn.informatika.spring.security.model.Patient;

import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.view.ActionAndBenefitDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.EditPharmacyView;

public interface PharmacyService {

	Optional<Pharmacy> findById(Long id);
	void editPharmacy(EditPharmacyView p);
	ActionAndBenefit addNew(ActionAndBenefitDTO actionAndBenefit);
	Set<Dermatologist> getDermatologistsByPharmacyAdmin (String email);

	
	Collection<Pharmacy> searchPharmacy(String p);

	//Boolean addWorkingTimeForDermatologist(String dermatologistId, String email, String wd, WorkingTimeIntervalDTO workingDay);
	//Collection<Pharmacy> searchPharmacy(String p);

	List<Pharmacy> findAll ();

	Set<WorkingDay> getWorkingDayForDermatolog(String id, String email);
	Boolean deleteDermatologistFromPharmacy(String id, String email);
	Set<Pharmacist> getPharmacistssByPharmacyAdmin (String email);
	//void addWorkingTimeForPharmacist(String dermatologistId, String email, String wd, WorkingTimeIntervalDTO workingDay);
	//Collection<Pharmacy> searchPharmacy(String p);
	Set<WorkingDay> getWorkingDayForPharmacist(String id, String email);
	Boolean deletePharmacistFromPharmacy(String id, String email);

	
	Set<Dermatologist> getAllDermatologistExpectAlreadyExisted(String email);
	Boolean addDermatologistInPharmacy(String email, NewDermatologistDTO newDermatologist);
	void addPharmacistInPharmacy(String email, NewPharmacistDTO newPharmacist);
	Set<Pharmacist> getAllPharmacistsExpectAlreadyExisted(String email);
	Boolean editDermatologistInPharmacy(String email, NewDermatologistDTO newDermatologist);
	
	Set<HolidayRequest> getHolidayRequestsByPharmacy(long id, String email);
	void acceptHolidayRequest(long id);
	void declineHolidayRequest(long id);
	Pharmacy save(Pharmacy pharmacy);
}