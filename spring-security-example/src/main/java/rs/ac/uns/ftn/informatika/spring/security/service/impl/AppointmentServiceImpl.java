package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.AppoitmentPrice;
import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.WorkingDay;
import rs.ac.uns.ftn.informatika.spring.security.model.WorkingTime;
import rs.ac.uns.ftn.informatika.spring.security.repository.AppointmentPriceRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.AppointmentRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistAppointmentRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.AppointmentService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.view.PredefinedAppointmentDTO;

@Service
public class AppointmentServiceImpl implements AppointmentService{
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	@Autowired
	private DermatologistAppointmentRepository dermatologistAppointmentRepository;
	
	@Autowired
	private AppointmentPriceRepository appointmentPriceRepository;
	
	@Override
	public List<DermatologistAppointment> findAll() {
		List<DermatologistAppointment> result=appointmentRepository.findAll();
		System.out.println("Servis");
		return result;
	}
	
	@Override
	public Boolean createPredefinedExamination(String email, PredefinedAppointmentDTO predefinedAppointment) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		

		String date = predefinedAppointment.getDate();
		LocalDate datePart = LocalDate.parse(date);
		LocalTime timePart = LocalTime.parse(predefinedAppointment.getTime());
		LocalDateTime dt = LocalDateTime.of(datePart, timePart);
		Boolean workInPharmacy = false;
		
		DermatologistAppointment appointment = new DermatologistAppointment();
		Dermatologist dermatologist = this.dermatologistRepository.findById(Long.parseLong(predefinedAppointment.getDermatologistId())).get();
		
		Set<WorkingTime> workingTimes = dermatologist.getWorkingTimes();
		if(workingTimes.isEmpty()) {
			return false;
		}
		
		WorkingTime wt = new WorkingTime();
		
		for(WorkingTime w : workingTimes) {
			if(w.getPharmacy().equals(p)) {
				workInPharmacy = true;
				wt = w;
			}
		}
		if(!workInPharmacy) {
			return false;
		}
		
		WorkingDay workingDay = new WorkingDay();
		
		for(WorkingDay wd : wt.getWorkingDays()) {
			if(wd.getDay().equals(dt.getDayOfWeek())) {
				workingDay = wd;
			}
		}
		if(workingDay.getId() == null) {
			return false;
		}
		
		int duration = Integer.parseInt(predefinedAppointment.getDuration());
		
		if(timePart.isBefore(workingDay.getStartTime()) || timePart.plusMinutes(duration).isBefore(workingDay.getStartTime())) {
	
			System.out.println(timePart.plusMinutes(duration));
			return false;
		}
		if(timePart.isAfter(workingDay.getEndTime()) || timePart.plusMinutes(duration).isAfter(workingDay.getEndTime())) {
			
			return false;
		}
		
		List<DermatologistAppointment> dermatologistAppointments = this.dermatologistAppointmentRepository.findAll();
		Set<DermatologistAppointment> derm = new HashSet<DermatologistAppointment>();
		
		
		for(DermatologistAppointment da : dermatologistAppointments) {
			if(da.getDermatologist().equals(dermatologist)) {
				derm.add(da);
			}
		}
		
		for(DermatologistAppointment d : derm) {
			if(d.getStartDateTime().toLocalDate().equals(datePart)) {
				LocalTime time = d.getStartDateTime().toLocalTime();
				int duration1 = d.getDuration();
				int duration2 = Integer.parseInt(predefinedAppointment.getDuration());
				if(timePart.isAfter(time) && timePart.isBefore(time.plusMinutes(duration1))) {
			
					return false;
				}
				if(timePart.plusMinutes(duration2).isAfter(time) && timePart.plusMinutes(duration2).isBefore(time.plusMinutes(duration1))) {
					
					return false;
				}
				
			}
		}
		appointment.setDermatologist(dermatologist);
		appointment.setDuration(Integer.parseInt(predefinedAppointment.getDuration()));
		appointment.setStartDateTime(dt);
		
		this.appointmentRepository.save(appointment);
		
		AppoitmentPrice ap = new AppoitmentPrice();
		ap.setAppoitment(appointment);
		ap.setPrice(Integer.parseInt(predefinedAppointment.getPrice()));
		this.appointmentPriceRepository.save(ap);
		
		return true;
	}
}
