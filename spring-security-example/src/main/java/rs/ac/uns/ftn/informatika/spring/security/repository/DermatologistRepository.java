package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;

public interface DermatologistRepository extends JpaRepository<Dermatologist, Long>{
	List<Dermatologist> findAll();

}