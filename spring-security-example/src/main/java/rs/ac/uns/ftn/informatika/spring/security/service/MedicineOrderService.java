package rs.ac.uns.ftn.informatika.spring.security.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import rs.ac.uns.ftn.informatika.spring.security.model.MedicineOrder;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;

public interface MedicineOrderService {
	List<MedicineOrder> findMedicineOrdersByPharmacy(Long pharmacyId);
	void createNewMedicineOrder(String email, Set<MedicineWithQuantity> medicinesWithQuantity, LocalDateTime date);
	public Boolean acceptSuplierOffer(String email, Long id);
}
