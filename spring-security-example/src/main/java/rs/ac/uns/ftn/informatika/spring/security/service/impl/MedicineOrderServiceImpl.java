package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineOrder;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineOrderStatus;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.Suplier;
import rs.ac.uns.ftn.informatika.spring.security.model.SuplierOffer;
import rs.ac.uns.ftn.informatika.spring.security.model.SuplierOfferStatus;
import rs.ac.uns.ftn.informatika.spring.security.repository.LoyaltyScaleRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineOrderRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineWithQuantityRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.SuplierOfferRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.SuplierRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.EmailService;
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
	private EmailService emailService;
		
		
	  @Autowired
	   private MedicineOrderRepository medicineOrderRepository;
	  
	  @Autowired
		private SuplierOfferRepository suplierOfferRepository;
	  @Autowired
	  private SuplierRepository suplierRepository;
		
	  
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
		mo.setPharmacyAdminId(pa.getId());
		
		p.getMedicineOrders().add(mo);
		
		this.pharmacyRepository.save(p);
		
	}
	@Override
	public Boolean acceptSuplierOffer(String email, Long id) {
		
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		List<Suplier> supliers = this.suplierRepository.findAll();
		
		SuplierOffer so = this.suplierOfferRepository.findById(id).get();
		
		
		if(so.getDeleveryTime().isAfter(LocalDateTime.now())) {
			return false;
		} else {
		
		MedicineOrder mo = so.getMedicineOrder();
		if(mo.getPharmacyAdminId() != pa.getId()) return false;
		mo.setStatus(MedicineOrderStatus.PROCESSED);
		so.setStatus(SuplierOfferStatus.ACCEPTED);
		this.suplierOfferRepository.save(so);
		
		List<SuplierOffer> allOffersExceptAccepted = new ArrayList<SuplierOffer>();
		for(SuplierOffer suplierOffer : this.suplierOfferRepository.findOffersByOrder(mo.getId())) {
			if(!suplierOffer.equals(so)) {
				allOffersExceptAccepted.add(suplierOffer);
			}
		}
		
		
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
		
		for(Suplier s : supliers) {
			for(SuplierOffer soffer : s.getOffers()) {
				if(soffer.equals(so)) {
					
					try {
						this.emailService.sendEmail(s.getUser().getEmail(), "Accept offer", "Your offer is accepted");
					} catch (MailException | MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					for(SuplierOffer su : allOffersExceptAccepted) {
						su.setStatus(SuplierOfferStatus.REJECTED);
						this.suplierOfferRepository.save(su);
						if(soffer.equals(su)) {
							try {
								this.emailService.sendEmail(s.getUser().getEmail(), "Decline offer", "Your offer is declined");
							} catch (MailException | MessagingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		
		
		this.pharmacyRepository.save(p);
		return true;
		}
	}


	@Override
	public List<MedicineOrder> findAll() {
		return this.medicineOrderRepository.findAll();
	}


	
	@Override
	public Set<MedicineWithQuantity> getMedicinesByOrder(long id) {
		List<MedicineOrder> allOrder = this.medicineOrderRepository.findAll();
		
		for(MedicineOrder mr : allOrder) {
			if(mr.getId() == id) {
				return mr.getMedicines();
			}
		}
		return null;
 	}
	
	
	@Override
	public void editMedicineOrder(String email, long id, Set<MedicineWithQuantity> medicinesWithQuantity, LocalDateTime date) {
		
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
		
		MedicineOrder mo = this.medicineOrderRepository.findById(id).get();
		
		
		for(MedicineOrder m : p.getMedicineOrders()) {
			if(m.getId() == mo.getId()) {
				m.setMedicines(medicinesWithQuantity);
				m.setStatus(MedicineOrderStatus.ON_HOLD);
				m.setTimeLimit(date);
			}
		}
		
		this.pharmacyRepository.save(p);
		
	}
	
	@Override
	public Boolean orderHaveOffers(Long id) {		

		
		List<SuplierOffer> offers = this.suplierOfferRepository.findAll();
		if(offers.size() == 0) {
			return false;
		}
		for(SuplierOffer so : offers) {
			
			if(so.getMedicineOrder().getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void deleteMedicineOrder(String email,Long id) {		
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		for(MedicineOrder mo : p.getMedicineOrders()) {
			if(mo.getId() == id) {
				p.getMedicineOrders().remove(mo);
				this.pharmacyRepository.save(p);
			}
		}
		this.medicineOrderRepository.delete(this.medicineOrderRepository.findById(id).get());
	}


}
