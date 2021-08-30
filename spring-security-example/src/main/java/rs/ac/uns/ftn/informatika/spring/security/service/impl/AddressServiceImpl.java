package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.Address;
import rs.ac.uns.ftn.informatika.spring.security.repository.AddressRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.AddressService;
@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	AddressRepository addressRepository;
	
	@Override
	public List<String> getAllCities() {
		List<String> result=new ArrayList<String>();
		for(Address ad : this.addressRepository.findAll()) {
			if(!result.contains(ad.getCity()))
				result.add(ad.getCity());
		}
		return result;
	}

}
