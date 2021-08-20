package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.spring.security.model.AppoitmentPrice;

public interface AppointmentPriceRepository extends JpaRepository<AppoitmentPrice,Long> {
	List<AppoitmentPrice> findAll();

}
