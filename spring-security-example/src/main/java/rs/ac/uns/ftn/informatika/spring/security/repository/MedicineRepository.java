package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;

public interface MedicineRepository  extends JpaRepository<Medicine, Long>{

}
