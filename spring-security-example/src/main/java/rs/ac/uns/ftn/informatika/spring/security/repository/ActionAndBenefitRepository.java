package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.spring.security.model.ActionAndBenefit;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;

public interface ActionAndBenefitRepository extends JpaRepository<ActionAndBenefit,Long>{
	
}
