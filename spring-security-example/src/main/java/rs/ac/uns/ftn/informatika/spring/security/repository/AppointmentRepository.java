package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;

public interface AppointmentRepository extends JpaRepository<DermatologistAppointment,Long> {
	
}
