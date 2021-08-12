package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;

public interface DermatologistRepository extends JpaRepository<Dermatologist, Long>{
	List<Dermatologist> findAll();

}
