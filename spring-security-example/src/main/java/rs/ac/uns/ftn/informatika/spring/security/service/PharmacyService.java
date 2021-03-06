package rs.ac.uns.ftn.informatika.spring.security.service;


import java.time.LocalDateTime;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import rs.ac.uns.ftn.informatika.spring.security.model.*;
import rs.ac.uns.ftn.informatika.spring.security.view.ActionAndBenefitDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.EditPharmacyView;
import rs.ac.uns.ftn.informatika.spring.security.view.NewDermatologistDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.NewPharmacistDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.ActionAndBenefitDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.EditPharmacyView;

import rs.ac.uns.ftn.informatika.spring.security.view.MedicineReservationView;
import rs.ac.uns.ftn.informatika.spring.security.view.PharmacyWithMedicationView;
import rs.ac.uns.ftn.informatika.spring.security.view.RatingView;
import rs.ac.uns.ftn.informatika.spring.security.view.NewDermatologistDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.NewPharmacistDTO;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MedicineReservationDTO;
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


	
	Collection<PharmacyWithMedicationView> getPharamciesWithMedication(Long id);
	Collection<MedicineReservationView> getReservationsByPatientsEmail(String email);

	//void addWorkingTimeForPharmacist(String dermatologistId, String email, String wd, WorkingTimeIntervalDTO workingDay);
	//Collection<Pharmacy> searchPharmacy(String p);
	Set<WorkingDay> getWorkingDayForPharmacist(String id, String email);
	Boolean deletePharmacistFromPharmacy(String id, String email);


	double getAvrageGrade(Pharmacy pharmacy);
	Set<Dermatologist> getAllDermatologistExpectAlreadyExisted(String email);
	Boolean addDermatologistInPharmacy(String email, NewDermatologistDTO newDermatologist);
	boolean addPharmacistInPharmacy(String email, NewPharmacistDTO newPharmacist);

	
	Pharmacy save(Pharmacy pharmacy);


	Set<Pharmacist> getAllPharmacistsExpectAlreadyExisted(String email);
	Boolean editDermatologistInPharmacy(String email, NewDermatologistDTO newDermatologist);
	
	Set<HolidayRequest> getHolidayRequestsByPharmacy(long id, String email);
	Boolean acceptHolidayRequest(long id, long dermatologistId);
	void declineHolidayRequest(long id, long dermatologistId, String reason);

	WorkingTime getDermatologistWorkingTimes(long id, String email);
	Pharmacy getPharmacyByDermatologistAndStartDate(Dermatologist d,LocalDateTime start);
	Set<HolidayRequest> getHolidayRequestsByPharmacyP(long parseLong, String email);
	Boolean acceptHolidayRequestP(long parseLong, long pharmacistId);
	void declineHolidayRequestP(long parseLong, long pharmacistId, String reason);
	List<RequestForMedicineAvailability> findRequestsByPharmacy(String email);


	Set<HolidayRequest> getHolidayRequestsForDerm(long id);

	List<RatingView> getAllPharmaciesPatientCanEvaluate(long patient);
	void changeRating(int rating,long patient,Long id);

}