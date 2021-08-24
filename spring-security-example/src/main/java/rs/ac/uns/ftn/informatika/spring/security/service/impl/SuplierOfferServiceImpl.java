package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.*;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineOrderRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.SuplierOfferRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.SuplierOfferService;
import rs.ac.uns.ftn.informatika.spring.security.service.SuplierService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.view.OfferView;

@Service
public class SuplierOfferServiceImpl implements SuplierOfferService{

	@Autowired
	private SuplierOfferRepository suplierOfferRepository;

	@Autowired
	private MedicineOrderRepository medicineOrderRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private SuplierService suplierService;

	@Override
	public List<SuplierOffer> findOffersByOrder(Long orderId) {
		// TODO Auto-generated method stub
		return this.suplierOfferRepository.findOffersByOrder(orderId);
	}

	@Override
	public SuplierOffer saveOffer(OfferView offerView) {
		LocalDate datePart = LocalDate.parse(offerView.getDeliveryDate());
		LocalTime timePart = LocalTime.parse(offerView.getTime());
		LocalDateTime dt = LocalDateTime.of(datePart, timePart);

		MedicineOrder medicineOrder = this.medicineOrderRepository.findById(Long.valueOf(offerView.getMedicineOrderId())).get();

		User user = this.userService.findByEmail(offerView.getSupplierEmail());
		Suplier suplier = this.suplierService.findSuplierByUser(user);

		SuplierOffer newOffer = new SuplierOffer();
		newOffer.setDeleveryTime(dt);
		newOffer.setPrice(Double.parseDouble(offerView.getPrice()));
		newOffer.setMedicineOrder(medicineOrder);
		newOffer.setStatus(SuplierOfferStatus.ON_HOLD);
		boolean isExists = false;
		Set<SuplierOffer> suplierOffers = suplier.getOffers();

		for (SuplierOffer offer : suplierOffers) {
			if (offer.getMedicineOrder().equals(newOffer.getMedicineOrder())) {
				isExists = true;
			}
		}
		//treba provera da li je suplier vec dao ponudu za narudzbenicu
		if (!isExists)
		{
			suplierOffers.add(newOffer);
			suplier.setOffers(suplierOffers);
			this.suplierService.save(suplier);
		//	this.suplierOfferRepository.save(newOffer);
			return newOffer;
		}else
			return null;
	}

	@Override
	public SuplierOffer editOffer(OfferView offerView) {
		LocalDate datePart = LocalDate.parse(offerView.getDeliveryDate());
		LocalTime timePart = LocalTime.parse(offerView.getTime());
		LocalDateTime dt = LocalDateTime.of(datePart, timePart);
		//KOD EDITA DA NE BI PRAVILA NOVU KLASU
		// medd=icineOrderId je zapravo SupplierOfferId
	    SuplierOffer suplierOffer = this.suplierOfferRepository.findById(Long.valueOf(offerView.getMedicineOrderId())).get();

		User user = this.userService.findByEmail(offerView.getSupplierEmail());
		Suplier suplier = this.suplierService.findSuplierByUser(user);

		//if(d.getStartDateTime().toLocalDate().equals(datePart)) {
		if(suplierOffer.getMedicineOrder().getTimeLimit().toLocalDate().isAfter(datePart)){
			suplierOffer.setDeleveryTime(dt);
			suplierOffer.setPrice(Double.parseDouble(offerView.getPrice()));
			this.suplierOfferRepository.save(suplierOffer);
			return suplierOffer;
		}else{
			return null;
		}


	}
	@Override
	public List<SuplierOffer> findAll() {
		List<SuplierOffer> result = suplierOfferRepository.findAll();
		return result;
	}

}
