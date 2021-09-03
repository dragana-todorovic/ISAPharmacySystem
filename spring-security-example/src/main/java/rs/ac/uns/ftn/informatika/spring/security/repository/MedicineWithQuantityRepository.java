package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.Optional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

public interface MedicineWithQuantityRepository extends JpaRepository<MedicineWithQuantity, Long> {
	
	Optional<MedicineWithQuantity> findById(Long id);
	
	
	@Query(nativeQuery = true, value = "select * from medicinewithquantity left join medicine \n" +
			"on medicinewithquantity.medicine_id = medicine.id\n" +
			"where medicinewithquantity.medicine_id= :medicineId")
	MedicineWithQuantity findByMedicineId(@Param("medicineId") long medicineId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select p from MedicineWithQuantity p where p.id = :id")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="0")})
	MedicineWithQuantity findMedWithQById( long id);
}
