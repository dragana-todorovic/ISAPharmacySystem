package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservation;

public interface MedicineReservationRepository extends JpaRepository<MedicineReservation, Long>{

	@Query(nativeQuery = true, value = "select * from medicinereservation left join medicinewithquantity \n" +
			"on medicinewithquantity.id=medicinereservation.medicine_with_quantity_id\n" +
			"where medicinereservation.medicine_with_quantity_id= :medicineId")
	List<MedicineReservation> getMedicineReservationByMedicineWithQuantity(@Param("medicineId")Long medicineId);
}
