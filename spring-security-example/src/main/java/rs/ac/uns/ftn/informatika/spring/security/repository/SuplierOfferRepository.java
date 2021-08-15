package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.MedicineOrder;
import rs.ac.uns.ftn.informatika.spring.security.model.SuplierOffer;

public interface SuplierOfferRepository extends JpaRepository<SuplierOffer, Long>{
	@Query(nativeQuery = true, value = "select * from suplieroffer \n" +
			"where suplieroffer.medicine_order_id= :orderId")
	List<SuplierOffer> findOffersByOrder(@Param("orderId")Long orderId);
	
	Optional<SuplierOffer> findById(Long offerId);

}
