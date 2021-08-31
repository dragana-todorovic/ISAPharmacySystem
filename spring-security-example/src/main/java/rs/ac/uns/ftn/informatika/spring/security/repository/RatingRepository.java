package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.spring.security.model.Rating;


public interface RatingRepository extends JpaRepository<Rating,Long> {
	List<Rating> findAll();
}
