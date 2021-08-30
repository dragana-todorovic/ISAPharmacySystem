package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.spring.security.model.Address;

public interface AddressRepository  extends JpaRepository<Address,Long>{
	List<Address> findAll();
}
