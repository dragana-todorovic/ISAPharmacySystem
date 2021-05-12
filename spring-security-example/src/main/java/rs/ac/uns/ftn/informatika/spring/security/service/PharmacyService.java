package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.Optional;

import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.User;

public interface PharmacyService {
	Optional<Pharmacy> findById(Long id);

}