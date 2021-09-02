package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.informatika.spring.security.model.AppoitmentPrice;
import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.model.HolidayRequest;
import rs.ac.uns.ftn.informatika.spring.security.model.HolidayRequestStatus;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.WorkingDay;
import rs.ac.uns.ftn.informatika.spring.security.model.WorkingTime;
import rs.ac.uns.ftn.informatika.spring.security.repository.AppointmentPriceRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistAppointmentRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.AppointmentService;
import rs.ac.uns.ftn.informatika.spring.security.service.PatientService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.service.WorkingTimeService;
import rs.ac.uns.ftn.informatika.spring.security.view.PredefinedAppointmentDTO;

@Service
public class AppointmentServiceImpl implements AppointmentService{
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	@Autowired
	private DermatologistAppointmentRepository dermatologistAppointmentRepository;
	
	@Autowired
	private AppointmentPriceRepository appointmentPriceRepository;
	
	@Override
	public List<DermatologistAppointment> findAll() {
		List<DermatologistAppointment> result=dermatologistAppointmentRepository.findAll();
		System.out.println("Servis");
		return result;
	}
	
	@Override
	@Transactional(readOnly = false)
	public Boolean createPredefinedExamination(String email, PredefinedAppointmentDTO predefinedAppointment) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		

		String date = predefinedAppointment.getDate();
		LocalDate datePart = LocalDate.parse(date);
		LocalTime timePart = LocalTime.parse(predefinedAppointment.getTime());
		LocalDateTime dt = LocalDateTime.of(datePart, timePart);
		Boolean workInPharmacy = false;
		
		DermatologistAppointment appointment = new DermatologistAppointment();
		Dermatologist dermatologist = this.dermatologistRepository.findDermatologistByID(Long.parseLong(predefinedAppointment.getDermatologistId()));
		
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
		
		for(HolidayRequest hol : dermatologist.getHolidayRequests()) {
			if(hol.getPharmacy().equals(p)) {
				if(!datePart.isBefore(hol.getStartDate()) && !datePart.isAfter(hol.getEndDate()) && hol.getStatus().equals(HolidayRequestStatus.ACCEPT)) {
					return false;
				}
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
		appointment.setPharmacy(p);
		appointment.setVersion(1L);
		
		this.dermatologistAppointmentRepository.save(appointment);
		
		AppoitmentPrice ap = new AppoitmentPrice();
		ap.setAppoitment(appointment);
		ap.setPrice(Integer.parseInt(predefinedAppointment.getPrice()));
		this.appointmentPriceRepository.save(ap);
		
		return true;
	}

	@Override
	public List<DermatologistAppointment> getAvailableAppointmentsByPharmacyId(Long id) {
		List<DermatologistAppointment> appointments=new ArrayList<DermatologistAppointment>();
		for(DermatologistAppointment da : dermatologistAppointmentRepository.findAll()) {
			if(da.getPharmacy().getId().equals(id) && da.getStartDateTime().isAfter(LocalDateTime.now())) {
				if(da.getPatient()==null) {
					appointments.add(da);
				}
			}
		}
		return appointments;
	}

	@Override
	public Boolean scheduleDermatologistAppointment(Long id,String patient) {
		System.out.println(patient);	
		User user = this.userService.findByUsername(patient);
		Patient p=patientService.findPatientByUser(user);
		for(DermatologistAppointment app: dermatologistAppointmentRepository.findAll()) {
			if(app.getId().equals(id)) {
				app.setPatient(p);
				this.dermatologistAppointmentRepository.save(app);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<DermatologistAppointment> getAllDermAppointmentsByPatient(String email) {
		List<DermatologistAppointment> result=new ArrayList<DermatologistAppointment>();
		for(DermatologistAppointment da : dermatologistAppointmentRepository.findAll()) {
			if(da.getPatient()==null) {
				continue;
			}
			else {
				if(da.getPatient().getUser().getEmail().equals(email)) {
					result.add(da);
				}
				
			}
		}
		return result;
	}

	@Override
	public Boolean cancelDermatologistAppointment(Long id) {
		LocalDateTime today=LocalDateTime.now();
		for(DermatologistAppointment da : dermatologistAppointmentRepository.findAll()) {
			if(da.getId().equals(id)) {
				if(da.getStartDateTime().isBefore(today.minusDays(1))) {
					return false;
				}
				else {
					da.setPatient(null);
					this.dermatologistAppointmentRepository.save(da);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public List<AppoitmentPrice> getAllAppointmentPricesByPharmacy(String email) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		return this.appointmentPriceRepository.findAllAppointmentPricesByPharmacy(p.getId());
	}
}
