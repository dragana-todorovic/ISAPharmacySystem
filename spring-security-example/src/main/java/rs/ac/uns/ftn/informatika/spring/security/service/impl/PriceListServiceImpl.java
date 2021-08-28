package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.expr.NewArray;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineOrder;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineOrderStatus;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicinePrice;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.PriceList;
import rs.ac.uns.ftn.informatika.spring.security.model.PriceListSort;
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
	public PriceList findPriceListByPharmacy(String email) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();

		List<PriceList> list = new ArrayList<PriceList>(this.priceListRepository.findPriceListByPharmacy(p.getId()));
		List<PriceList> result = new ArrayList<PriceList>();
		
		for(PriceList pl : list) {
			if(pl.getStartDate().isBefore(LocalDate.now()) || pl.getStartDate().equals(LocalDate.now())) {
				System.out.println("USAOOOOOO U IFFFF IFF IFFF");
				result.add(pl);
			}
		}
		
		Collections.sort(result, new PriceListSort(-1));
		return result.get(0);
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
	public void createNewPriceList(String email, Set<MedicinePrice> medicinePrice, LocalDate date) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		for(PriceList priceList : p.getPriceList()) {
			if(priceList.getStartDate().equals(date)) {
				priceList.setMedicinePriceList(medicinePrice);
				this.pharmacyRepository.save(p);
				return;
			}
		}
		
		PriceList pl = new PriceList();
		pl.setMedicinePriceList(medicinePrice);
		pl.setStartDate(date);
		
		p.getPriceList().add(pl);
		
		this.pharmacyRepository.save(p);
		return;
		
	}

}
