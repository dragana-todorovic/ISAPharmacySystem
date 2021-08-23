package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.repository.AppointmentPriceRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.AppointmentPriceService;
import rs.ac.uns.ftn.informatika.spring.security.model.AppoitmentPrice;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;

@Service
public class AppointmentPriceServiceImpl implements AppointmentPriceService {
	@Autowired
	private AppointmentPriceRepository appointmentPriceRepository;
	@Override
	public List<AppoitmentPrice> findAll() {
		List<AppoitmentPrice> result = appointmentPriceRepository.findAll();
		return result;
	}
	@Override
	public Double getPriceByAppointment(DermatologistAppointment appointment) {
		for(AppoitmentPrice ap: appointmentPriceRepository.findAll()) {
			if(ap.getAppoitment().equals(appointment)) {
				return ap.getPrice();
			}
		}
		return null;
	}

}
