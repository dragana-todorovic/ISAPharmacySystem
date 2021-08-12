package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppoitment;


public interface DermatologistAppointmentRepository extends JpaRepository<DermatologistAppoitment, Long>  {
	List<DermatologistAppoitment> findAll();
	Optional<DermatologistAppoitment> findById(Long id);
}
