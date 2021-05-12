package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long>  {
	Optional<Pharmacy> findById(Long id);	
}