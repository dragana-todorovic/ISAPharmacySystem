package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.AppoitmentPrice;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacist;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounseling;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounselingPrice;

public interface PharmacistCounselingPriceRepository extends JpaRepository<PharmacistCounselingPrice, Long>{

	@Query(nativeQuery = true, value = "select * from pharmacistcounselingprice \n" +
			"where pharmacistcounselingprice.counseling_id= :appointmentId")
	PharmacistCounselingPrice findByAppintmentId(@Param("appointmentId")Long appointmentId);
}
