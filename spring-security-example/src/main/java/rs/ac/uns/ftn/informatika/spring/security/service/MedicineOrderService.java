package rs.ac.uns.ftn.informatika.spring.security.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import rs.ac.uns.ftn.informatika.spring.security.model.MedicineOrder;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;

public interface MedicineOrderService {
	List<MedicineOrder> findMedicineOrdersByPharmacy(Long pharmacyId);
	void createNewMedicineOrder(String email, Set<MedicineWithQuantity> medicinesWithQuantity, LocalDateTime date);
	public Boolean acceptSuplierOffer(String email, Long id);

	List<MedicineOrder> findAll();

	Set<MedicineWithQuantity> getMedicinesByOrder(long id);
	void editMedicineOrder(String email, long id, Set<MedicineWithQuantity> medicinesWithQuantity, LocalDateTime date);
	Boolean orderHaveOffers(Long id);
	void deleteMedicineOrder(String email,Long id);

}
