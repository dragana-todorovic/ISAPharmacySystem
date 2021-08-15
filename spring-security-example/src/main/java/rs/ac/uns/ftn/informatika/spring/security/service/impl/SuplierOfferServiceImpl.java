package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.SuplierOffer;
import rs.ac.uns.ftn.informatika.spring.security.repository.SuplierOfferRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.SuplierOfferService;

@Service
public class SuplierOfferServiceImpl implements SuplierOfferService{

	@Autowired
	private SuplierOfferRepository suplierOfferRepository;
	
	@Override
	public List<SuplierOffer> findOffersByOrder(Long orderId) {
		// TODO Auto-generated method stub
		return this.suplierOfferRepository.findOffersByOrder(orderId);
	}
	
	
	
}
