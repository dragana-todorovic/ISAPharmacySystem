package rs.ac.uns.ftn.informatika.spring.security.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.MedicinePrice;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.PriceList;

public interface PriceListService {
	List<PriceList> findPriceListByPharmacy(String email);
	List<MedicinePrice> findMedicinePricesByPriceList(Long priceListId);
	Set<MedicinePrice> findMedicinePricesByPriceListId(Long priceListId);
	
	void createNewPriceList(String email, Set<MedicinePrice> medicinePrice, LocalDate date);
}
