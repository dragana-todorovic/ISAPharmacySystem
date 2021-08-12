package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.ActionAndBenefit;
import rs.ac.uns.ftn.informatika.spring.security.model.Address;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.repository.ActionAndBenefitRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.view.ActionAndBenefitDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.EditPharmacyView;

@Service
public class PharmacyServiceImpl implements PharmacyService{

	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private ActionAndBenefitRepository actionAndBenefitRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Override
	public Optional<Pharmacy> findById(Long id) {
		return pharmacyRepository.findById(id);
	}

	@Override
	public void editPharmacy(EditPharmacyView p) {
		Address a = new Address();
		a.setStreet(p.getStreet());
		a.setCity(p.getCity());
		Pharmacy pharmacy = findById(p.getId()).get();
		pharmacy.setName(p.getName());
		pharmacy.setAddress(a);
		pharmacy.setDescription(p.getDescription());
		
		pharmacyRepository.save(pharmacy);

	}
	
	@Override
	public ActionAndBenefit addNew(ActionAndBenefitDTO actionAndBenefit) {
		
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(actionAndBenefit.getPharmacyAdminEmail()));
		
		ActionAndBenefit ab = new ActionAndBenefit();

		ab.setStartDate(LocalDate.parse(actionAndBenefit.getStartDate()));
		ab.setEndDate(LocalDate.parse(actionAndBenefit.getEndDate()));
		ab.setDescription(actionAndBenefit.getDescription());
	
		
		Pharmacy pharmacy =pa.getPharmacy();
		pharmacy.getActionsAndBenefits().add(ab);
		
		this.pharmacyRepository.save(pharmacy);
		
		return ab;
		
	}

	@Override
	public List<Pharmacy> findAll() {
		List<Pharmacy> result = pharmacyRepository.findAll();
		return result;
	}



	/*@Override
	public Collection<Pharmacy> searchPharmacy(String p) {
	   ArrayList<Pharmacy> pharmacies = new ArrayList<>();
	   for(Pharmacy pharamacy : pharmacyRepository.findAll()){
		   if(pharamacy.getName().equalsIgnoreCase(p)) {
			   pharmacies.add(pharamacy);
		   }
		   else if(pharamacy.getAddress().equalsIgnoreCase(p)){
			   pharmacies.add(pharamacy);
		   }
	   }
        return pharmacies;
	}*/

}
