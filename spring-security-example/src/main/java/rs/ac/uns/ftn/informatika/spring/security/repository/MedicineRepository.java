package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

public interface MedicineRepository  extends JpaRepository<Medicine, Long>{

	List<Medicine> findAll();
	Medicine findByName(String name);
	Medicine findByCode(String code);

	Medicine findMedicineById(Long id);

	Optional<Medicine> findById(Long id);


	@Query(nativeQuery = true, value = "select * from medicine \n" +
			 "where medicine.name= :name")
	Medicine findfindByName(@Param("name")String name);


}
