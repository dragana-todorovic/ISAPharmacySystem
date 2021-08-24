package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineOrder;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineOrderStatus;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicinePrice;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.PriceList;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PriceListRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.PriceListService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;

@Service
public class PriceListServiceImpl implements PriceListService {
	

	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
		
	@Autowired
	private PriceListRepository priceListRepository;
	
	@Override
	public List<PriceList> findPriceListByPharmacy(String email) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		return this.priceListRepository.findPriceListByPharmacy(p.getId());
		
	}

	@Override
	public List<MedicinePrice> findMedicinePricesByPriceList(Long priceListId) {
		
		return this.priceListRepository.findMedicinePricesByPriceList(priceListId);
	}
	
	@Override
	public Set<MedicinePrice> findMedicinePricesByPriceListId(Long priceListId) {
		
		List<PriceList> priceList = this.priceListRepository.findAll();
		for(PriceList p : priceList) {
			if(p.getId() == priceListId ) {
				return p.getMedicinePriceList();
			}
		}
		
		return null;
	}

	@Override
	public Boolean createNewPriceList(String email, Set<MedicinePrice> medicinePrice, LocalDate date) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		for(PriceList priceList : p.getPriceList()) {
			if(priceList.getStartDate().equals(date)) {
				return false;
			}
		}
		
		PriceList pl = new PriceList();
		pl.setMedicinePriceList(medicinePrice);
		pl.setStartDate(date);
		
		p.getPriceList().add(pl);
		
		this.pharmacyRepository.save(p);
		return true;
		
	}

}
