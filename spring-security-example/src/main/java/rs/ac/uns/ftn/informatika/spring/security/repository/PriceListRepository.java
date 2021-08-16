package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.MedicineOrder;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicinePrice;
import rs.ac.uns.ftn.informatika.spring.security.model.PriceList;

public interface PriceListRepository extends JpaRepository<PriceList, Long>{
	@Query(nativeQuery = true, value = "select * from pricelist left join pharmacy_price_list \n" +
			"on pricelist.id = pharmacy_price_list.price_list_id\n" +
			"where pharmacy_price_list.pharmacy_id= :pharmacyId")
	List<PriceList> findPriceListByPharmacy(@Param("pharmacyId")Long pharmacyId);
	
	@Query(nativeQuery = true, value = "select * from medicineprice left join pricelist_medicine_price_list \n" +
			"on medicineprice.id = pricelist_medicine_price_list.medicine_price_list_id\n" +
			"where pricelist_medicine_price_list.price_list_id= :priceListId")
	List<MedicinePrice> findMedicinePricesByPriceList(@Param("priceListId")Long priceListId);
}
