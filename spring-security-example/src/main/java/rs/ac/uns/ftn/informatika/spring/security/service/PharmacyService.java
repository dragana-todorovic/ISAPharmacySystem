package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.Collection;
import java.util.Optional;

import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.view.EditPharmacyView;

public interface PharmacyService {
	Optional<Pharmacy> findById(Long id);
	void editPharmacy(EditPharmacyView p);
	//Collection<Pharmacy> searchPharmacy(String p);
}