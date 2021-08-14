package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;


public interface DermatologistAppointmentRepository extends JpaRepository<DermatologistAppointment, Long>  {
	List<DermatologistAppointment> findAll();
	Optional<DermatologistAppointment> findById(Long id);
}
