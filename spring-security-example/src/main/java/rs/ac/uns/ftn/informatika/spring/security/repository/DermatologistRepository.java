
package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;

public interface DermatologistRepository extends JpaRepository<Dermatologist, Long>{
	List<Dermatologist> findAll();
	Optional<Dermatologist> findById(Long id);	
	Dermatologist findDermatologistById(Long id);	
	
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select d from Dermatologist d where d.id = :id")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="0")})
	Dermatologist findDermatologistByID(@Param("id")Long id);
}


