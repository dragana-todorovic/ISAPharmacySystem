package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounseling;

public interface PharmacistCounselingRepository extends JpaRepository<PharmacistCounseling, Long> {
	List<PharmacistCounseling> findAll();
	Optional<PharmacistCounseling> findById(Long id);
	
	@Query(nativeQuery = true, value = "select * from pharmacistcounseling left join PHARMACIST \n" +
			"on pharmacistcounseling.pharmacist_id=PHARMACIST.id\n" +
			"where pharmacistcounseling.pharmacist_id= :pharmacistId")
	List<PharmacistCounseling> findCoByPharmacist(@Param("pharmacistId")long pharmacistId);
	
}
