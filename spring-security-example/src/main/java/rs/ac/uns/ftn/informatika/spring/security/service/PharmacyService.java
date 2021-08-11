package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import rs.ac.uns.ftn.informatika.spring.security.model.ActionAndBenefit;
import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.WorkingDay;
import rs.ac.uns.ftn.informatika.spring.security.view.ActionAndBenefitDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.EditPharmacyView;
import rs.ac.uns.ftn.informatika.spring.security.view.WorkingDayDTO;

public interface PharmacyService {
	Optional<Pharmacy> findById(Long id);
	void editPharmacy(EditPharmacyView p);
	ActionAndBenefit addNew(ActionAndBenefitDTO actionAndBenefit);
	Set<Dermatologist> getDermatologistsByPharmacyAdmin (String email);
	void addWorkingTimeForDermatologist(String dermatologistId, String email, WorkingDayDTO workingDay);
	//Collection<Pharmacy> searchPharmacy(String p);
	Set<WorkingDay> getWorkingDayForDermatolog(String id, String email);
}