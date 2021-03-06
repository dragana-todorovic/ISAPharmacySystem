package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.AppoitmentPrice;

public interface AppointmentPriceRepository extends JpaRepository<AppoitmentPrice,Long> {
	List<AppoitmentPrice> findAll();
	
	@Query(nativeQuery = true, value = "select * from appoitmentprice \n" +
			"where appoitmentprice.appoitment_id= :appointmentId")
	AppoitmentPrice findByAppintmentId(@Param("appointmentId")Long appointmentId);
	
	@Query(nativeQuery = true, value = "select * from APPOITMENTPRICE left join APPOITMENT \n" +
			"on APPOITMENTPRICE.appoitment_id = APPOITMENT.id\n" +
			"where APPOITMENT.pharmacy_id= :pharmacyId")
	List<AppoitmentPrice> findAllAppointmentPricesByPharmacy(@Param("pharmacyId")long pharmacyId);

}
