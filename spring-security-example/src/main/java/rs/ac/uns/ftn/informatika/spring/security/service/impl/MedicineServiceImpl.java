package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineService;

@Service
public class MedicineServiceImpl implements MedicineService{

	@Autowired
	private MedicineRepository medicineRepository;
	
	@Override
	public List<Medicine> findAll() {
		List<Medicine> result = medicineRepository.findAll();
		return result;
	}

	@Override
	public Medicine findByName(String name) {
		System.out.println("Usao u servis");
		Medicine m = medicineRepository.findByName(name);
		return m;
	}

}
