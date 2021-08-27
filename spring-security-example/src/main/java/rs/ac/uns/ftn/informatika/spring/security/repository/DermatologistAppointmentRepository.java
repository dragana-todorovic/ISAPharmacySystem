package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;


public interface DermatologistAppointmentRepository extends JpaRepository<DermatologistAppointment, Long>  {
	List<DermatologistAppointment> findAll();
	Optional<DermatologistAppointment> findById(Long id);
	
	
	@Query(nativeQuery = true, value = "select * from APPOITMENT left join DERMATOLOGIST \n" +
			"on APPOITMENT.dermatologist_id=DERMATOLOGIST.id\n" +
			"where APPOITMENT.dermatologist_id= :dermatologistId")
	List<DermatologistAppointment> findApByDermatologist(@Param("dermatologistId")long dermatologistId);
	
}
