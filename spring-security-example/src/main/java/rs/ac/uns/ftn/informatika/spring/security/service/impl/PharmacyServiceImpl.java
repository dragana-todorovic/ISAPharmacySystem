package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyService;

@Service
public class PharmacyServiceImpl implements PharmacyService{

	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Override
	public Optional<Pharmacy> findById(Long id) {
		return pharmacyRepository.findById(id);
	}

	@Override
	public void editPharmacy(Pharmacy p) {
		Pharmacy pharmacy = findById(p.getId()).get();
		pharmacy.setName(p.getName());
		pharmacy.setAddress(p.getAddress());
		pharmacy.setDescription(p.getDescription());
		
		pharmacyRepository.save(pharmacy);

	}

	@Override
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
	}

}
