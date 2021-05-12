package rs.ac.uns.ftn.informatika.spring.security.service;

import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.User;

public interface PharmacyAdminService {
	PharmacyAdmin findPharmacyAdminByUser(User user);

}
