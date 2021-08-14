package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.model.MedicineOrder;

public interface MedicineOrderService {
	List<MedicineOrder> findMedicineOrdersByPharmacy(Long pharmacyId);
}
