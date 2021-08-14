package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;;

public interface MedicineWithQuantityRepository extends JpaRepository<MedicineWithQuantity, Long> {
	Optional<MedicineWithQuantity> findById(Long id);
}
