package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineOrder;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;

public interface MedicineOrderRepository extends JpaRepository<MedicineOrder, Long> {
	@Query(nativeQuery = true, value = "select * from medicineorder left join pharmacy_medicine_orders \n" +
			"on medicineorder.id = pharmacy_medicine_orders.medicine_orders_id\n" +
			"where pharmacy_medicine_orders.pharmacy_id= :pharmacyId")
	List<MedicineOrder> findMedicineOrdersByPharmacy(@Param("pharmacyId")Long pharmacyId);
	}
