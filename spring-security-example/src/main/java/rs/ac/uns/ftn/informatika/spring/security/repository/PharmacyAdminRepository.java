package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.User;

public interface PharmacyAdminRepository extends JpaRepository<PharmacyAdmin, Long>  {
	List<PharmacyAdmin> findAll();
}
