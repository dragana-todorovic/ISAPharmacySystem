package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.List;


import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounseling;

public interface PharmacistCounselingService {
	List<PharmacistCounseling> findById(Long id);
	List<PharmacistCounseling> findAll ();
}
