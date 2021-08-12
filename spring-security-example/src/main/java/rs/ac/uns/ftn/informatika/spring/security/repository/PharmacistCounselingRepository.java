package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppoitment;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounseling;

public interface PharmacistCounselingRepository extends JpaRepository<PharmacistCounseling, Long> {
	List<PharmacistCounseling> findAll();
	Optional<PharmacistCounseling> findById(Long id);
}
