package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.List;

import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.Rating;


public interface RatingService {
	List<Rating> findAll();
}
