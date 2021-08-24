package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacist;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounseling;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.WorkingDay;
import rs.ac.uns.ftn.informatika.spring.security.model.WorkingTime;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.CounselingDTO;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistAppointmentRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistCounselingRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.UserRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacistCounselingService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacistService;
import rs.ac.uns.ftn.informatika.spring.security.view.PharamcistForCounselingView;
import rs.ac.uns.ftn.informatika.spring.security.view.PharmacyForCounselingView;

@Service
public class PharmacistCounselingServiceImpl implements PharmacistCounselingService {
	
	@Autowired
	private PharmacistCounselingRepository pharmacistCounselingRepository;	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PharmacistService pharmacistService;
	@Autowired
	private PharmacistRepository pharmacistRepository;
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
	@Override
	public List<PharmacistCounseling> findByPatientId(Long id) {
		System.out.println("PATIENT ID"+id);
		List <PharmacistCounseling> appointments = new ArrayList<PharmacistCounseling>();
		for (PharmacistCounseling d:pharmacistCounselingRepository.findAll()) {
			if(d.getPatient()!=null) {
			if(d.getPatient().getId().equals(id)) {
			
				appointments.add(d);
			}
		}}
		
		return appointments;
	}
	@Override
	public void saveAppointment(CounselingDTO appointmentDTO,Patient patient, LocalDateTime startDateTime) {
		// TODO Auto-generated method stub
		try{
			User u = userRepository.findByEmail(appointmentDTO.getPharmacistEmail());
			for (Pharmacist d: pharmacistRepository.findAll()) {	
				if(d.getUser().getEmail().equals(u.getEmail()))
				{
			System.out.println("Usao u if###########");
			System.out.println(d.getId());
			PharmacistCounseling ap = new PharmacistCounseling();
			ap.setPharmacist(d);
			
			ap.setDuration(Integer.parseInt(appointmentDTO.getDuration()));
			ap.setPatient(patient);
			ap.setStartDateTime(startDateTime);
			System.out.println("Usao prije save");
			pharmacistCounselingRepository.save(ap);
			break;
			
				}}} catch (Exception e) {
			return;
		}
	}

	@Override
	public List<PharmacyForCounselingView> getPharamciesWithAvailablePharmacists(String term) {
		LocalDateTime dateTime = LocalDateTime.parse(term);
		LocalTime time=LocalTime.parse(term.split("T")[1]);
		DayOfWeek day= dateTime.getDayOfWeek();
		System.out.println(time);
		System.out.println(day);
		ArrayList<PharmacyForCounselingView> result=new ArrayList<PharmacyForCounselingView>();
		for(Pharmacist pharmacist : pharmacistRepository.findAll()) {
			boolean isWorking=false;
			for(WorkingDay wt:pharmacist.getWorkingTimes().getWorkingDays()) {
				if(wt.getStartTime().isBefore(time) && wt.getEndTime().isAfter(time) && wt.getDay().equals(day)) {
					System.out.println("u radnom vremenu je");			
					isWorking=true;
					break;
				}
			}
			if(isWorking) {
				System.out.println("dodaj");
				result.add(new PharmacyForCounselingView(pharmacist.getWorkingTimes().getPharmacy().getId(),pharmacist.getWorkingTimes().getPharmacy().getName(),pharmacist.getWorkingTimes().getPharmacy().getAddress().getCity(),pharmacist.getWorkingTimes().getPharmacy().getAddress().getStreet(),"ocena","cena"));
			}
			else {
				continue;
			}
		}
		return result;
	}

	@Override
	public List<PharamcistForCounselingView> getAvailablePharmacistsByPharmacy(Long id, String term) {	
		List<PharamcistForCounselingView> result=new ArrayList<PharamcistForCounselingView>();
		LocalDateTime dateTime = LocalDateTime.parse(term);
		LocalTime time=LocalTime.parse(term.split("T")[1]);
		DayOfWeek day= dateTime.getDayOfWeek();
		for(Pharmacist pharmacist : pharmacistRepository.findAll()) {
			if(pharmacist.getWorkingTimes().getPharmacy().getId().equals(id)) {
				boolean isWorking=false;
				for(WorkingDay wt:pharmacist.getWorkingTimes().getWorkingDays()) {
					if(wt.getStartTime().isBefore(time) && wt.getEndTime().isAfter(time) && wt.getDay().equals(day)) {
						System.out.println("u radnom vremenu je");			
						isWorking=true;
						break;
					}
				}
				if(isWorking) {
					System.out.println("dodaj");
					System.out.println(pharmacist.getUser().getFirstName());
					result.add(new PharamcistForCounselingView(pharmacist.getId(),pharmacist.getUser().getFirstName(),pharmacist.getUser().getLastName(),pharmacistService.getAvrageGrade(pharmacist)));
				}
				else {
					continue;
				}
			}
		}
		
		return result;
	}

}
