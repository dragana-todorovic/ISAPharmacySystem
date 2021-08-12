package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;

public interface MedicineRepository  extends JpaRepository<Medicine, Long>{
	@Query(nativeQuery = true, value = "select * from medicine \n" +
			 "where medicine.name= :name")
	Medicine findfindByName(@Param("name")String name);

}
