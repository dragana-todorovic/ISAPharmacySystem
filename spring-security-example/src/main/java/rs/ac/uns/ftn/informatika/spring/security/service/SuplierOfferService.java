package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.model.SuplierOffer;

public interface SuplierOfferService {
	List<SuplierOffer> findOffersByOrder(Long orderId);
}
