package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.model.SuplierOffer;
import rs.ac.uns.ftn.informatika.spring.security.view.OfferView;

public interface SuplierOfferService {
	List<SuplierOffer> findOffersByOrder(Long orderId);
	SuplierOffer saveOffer(OfferView offerView);

	SuplierOffer editOffer(OfferView offerView);

	List<SuplierOffer> findAll();
}
