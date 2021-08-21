package rs.ac.uns.ftn.informatika.spring.security.service;

import rs.ac.uns.ftn.informatika.spring.security.model.ActionAndBenefit;
import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.view.ActionAndBenefitDTO;

public interface PharmacyAdminService {
	PharmacyAdmin findPharmacyAdminByUser(User user);
	PharmacyAdmin save(PharmacyAdmin pharmacyAdmin);
}
