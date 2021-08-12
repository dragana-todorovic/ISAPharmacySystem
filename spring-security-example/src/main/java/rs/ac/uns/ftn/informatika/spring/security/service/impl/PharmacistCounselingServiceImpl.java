package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounseling;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistAppointmentRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistCounselingRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacistCounselingService;

@Service
public class PharmacistCounselingServiceImpl implements PharmacistCounselingService {
	
	@Autowired
	private PharmacistCounselingRepository pharmacistCounselingRepository;
	@Override
	public List<PharmacistCounseling> findById(Long id) {
		List <PharmacistCounseling> appointments = new ArrayList<PharmacistCounseling>();
		for (PharmacistCounseling d:pharmacistCounselingRepository.findAll()) {
			if(d.getPharmacist().getId().equals(id)) {
				appointments.add(d);
			}
		}
		return appointments;
	}

	@Override
	public List<PharmacistCounseling> findAll() {
		List<PharmacistCounseling> result = pharmacistCounselingRepository.findAll();
		return result;
	
	}

}
