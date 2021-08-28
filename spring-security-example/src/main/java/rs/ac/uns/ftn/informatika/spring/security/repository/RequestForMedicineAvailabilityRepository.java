package rs.ac.uns.ftn.informatika.spring.security.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.RequestForMedicineAvailability;

public interface RequestForMedicineAvailabilityRepository extends JpaRepository<RequestForMedicineAvailability, Long> {
	
	@Query(nativeQuery = true, value = "select * from requestformedicineavailability left join pharmacy \n" +
			"on requestformedicineavailability.pharmacy_id = pharmacy.id\n" +
			"where requestformedicineavailability.pharmacy_id= :pharmacyId")
	List<RequestForMedicineAvailability> findRequestsByPharmacy(@Param("pharmacyId") long pharmacyId);
}

