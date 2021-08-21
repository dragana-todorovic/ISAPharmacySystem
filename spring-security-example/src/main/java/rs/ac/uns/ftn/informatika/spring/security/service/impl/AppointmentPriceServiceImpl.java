package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import rs.ac.uns.ftn.informatika.spring.security.repository.AppointmentPriceRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.AppointmentPriceService;
import rs.ac.uns.ftn.informatika.spring.security.model.AppoitmentPrice;
public class AppointmentPriceServiceImpl implements AppointmentPriceService {
	@Autowired
	private AppointmentPriceRepository appointmentPriceRepository;
	@Override
	public List<AppoitmentPrice> findAll() {
		List<AppoitmentPrice> result = appointmentPriceRepository.findAll();
		return result;
	}

}
