package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.MedicineOrder;
import rs.ac.uns.ftn.informatika.spring.security.repository.LoyaltyScaleRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineOrderRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineOrderService;
import rs.ac.uns.ftn.informatika.spring.security.service.PatientService;

@Service
public class MedicineOrderServiceImpl implements MedicineOrderService {
	
	
	  @Autowired
	   private MedicineOrderRepository medicineOrderRepository;
	@Override
	public List<MedicineOrder> findMedicineOrdersByPharmacy(Long pharmacyId) {		
		
		return this.medicineOrderRepository.findMedicineOrdersByPharmacy(pharmacyId);
	}

}
