
package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacist;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long>{
	List<Pharmacist> findAll();
	Optional<Pharmacist> findById(Long id);	
	Pharmacist findPharmacistById(Long id);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select d from Pharmacist d where d.id =:id")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="0")})
	Pharmacist findPharmacistByID(@Param("id")Long id);
	
}



