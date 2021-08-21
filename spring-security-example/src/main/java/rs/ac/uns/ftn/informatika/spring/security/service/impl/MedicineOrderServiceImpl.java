package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineOrder;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineOrderStatus;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.SuplierOffer;
import rs.ac.uns.ftn.informatika.spring.security.repository.LoyaltyScaleRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineOrderRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineWithQuantityRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.SuplierOfferRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineOrderService;
import rs.ac.uns.ftn.informatika.spring.security.service.PatientService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;

@Service
public class MedicineOrderServiceImpl implements MedicineOrderService {
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
		
	  @Autowired
	   private MedicineOrderRepository medicineOrderRepository;
	  
	  @Autowired
		private SuplierOfferRepository suplierOfferRepository;
		
	  
	  @Autowired
	   private MedicineWithQuantityRepository medicineWithQuantityRepository;
	@Override
	public List<MedicineOrder> findMedicineOrdersByPharmacy(Long pharmacyId) {		
		
		return this.medicineOrderRepository.findMedicineOrdersByPharmacy(pharmacyId);
	}
	@Override
	public void createNewMedicineOrder(String email, Set<MedicineWithQuantity> medicinesWithQuantity, LocalDateTime date) {
		
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		List<Medicine> pomocna = new ArrayList<Medicine>();
		for(MedicineWithQuantity m : p.getMedicineWithQuantity()) {
			pomocna.add(m.getMedicine());
		}
		
		for(MedicineWithQuantity mq : medicinesWithQuantity) {
			if(!pomocna.contains(mq.getMedicine())) {
				MedicineWithQuantity newM = new MedicineWithQuantity();
				newM.setMedicine(mq.getMedicine());
				newM.setQuantity(0);
				p.getMedicineWithQuantity().add(newM);
				this.medicineWithQuantityRepository.save(newM);
			}
		}
		
		MedicineOrder mo = new MedicineOrder();
		mo.setMedicines(medicinesWithQuantity);
		mo.setStatus(MedicineOrderStatus.ON_HOLD);
		mo.setTimeLimit(date);
		
		p.getMedicineOrders().add(mo);
		
		this.pharmacyRepository.save(p);
		
	}
	
	public Boolean acceptSuplierOffer(String email, Long id) {
		
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		SuplierOffer so = this.suplierOfferRepository.findById(id).get();
		
		
		
		if(so.getDeleveryTime().isAfter(LocalDateTime.now())) {
			return false;
		} else {
		
		MedicineOrder mo = so.getMedicineOrder();
		mo.setStatus(MedicineOrderStatus.PROCESSED);
		
		List<SuplierOffer> allOffersExceptAccepted = new ArrayList<SuplierOffer>();
		for(SuplierOffer suplierOffer : this.suplierOfferRepository.findOffersByOrder(mo.getId())) {
			if(!suplierOffer.equals(so)) {
				allOffersExceptAccepted.add(suplierOffer);
			}
		}
		
		System.out.println("*****************" + allOffersExceptAccepted);
		
		for(MedicineWithQuantity mq : mo.getMedicines()) {
			for(MedicineWithQuantity mwq : p.getMedicineWithQuantity()) {
				if(mwq.getMedicine().equals(mq.getMedicine())) {
					mwq.setQuantity(mwq.getQuantity() + mq.getQuantity());
				}
			}
		}
		
		for(SuplierOffer soDelete: this.suplierOfferRepository.findOffersByOrder(mo.getId())) {
			soDelete.setMedicineOrder(null);
			this.suplierOfferRepository.save(soDelete);
		}
		
		this.pharmacyRepository.save(p);
		return true;
		}
	}
		

}
