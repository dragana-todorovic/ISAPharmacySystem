package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;

public interface MedicineRepository  extends JpaRepository<Medicine, Long>{

	List<Medicine> findAll();
	Medicine findByName(String name);

	@Query(nativeQuery = true, value = "select * from medicine \n" +
			 "where medicine.name= :name")
	Medicine findfindByName(@Param("name")String name);

}
