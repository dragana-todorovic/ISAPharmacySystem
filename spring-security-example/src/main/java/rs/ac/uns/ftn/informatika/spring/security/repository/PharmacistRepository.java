
package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacist;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long>{
	List<Pharmacist> findAll();
	Optional<Pharmacist> findById(Long id);	
}



