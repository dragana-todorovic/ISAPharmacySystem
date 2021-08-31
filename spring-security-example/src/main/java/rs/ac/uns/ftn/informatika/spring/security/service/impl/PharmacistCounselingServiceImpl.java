package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.model.HolidayRequest;
import rs.ac.uns.ftn.informatika.spring.security.model.HolidayRequestStatus;
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
import rs.ac.uns.ftn.informatika.spring.security.view.PatientsCounslingView;
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
	@Transactional(readOnly=false)
	public void saveAppointment(CounselingDTO appointmentDTO,Patient patient, LocalDateTime startDateTime) {
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
		LocalDate date=LocalDate.parse(term.split("T")[0]);
		LocalTime time=LocalTime.parse(term.split("T")[1]);
		DayOfWeek day= dateTime.getDayOfWeek();
		ArrayList<PharmacyForCounselingView> result=new ArrayList<PharmacyForCounselingView>();

		for(Pharmacist pharmacist : pharmacistRepository.findAll()) {

			boolean isWorking=false;
			for(WorkingDay wt:pharmacist.getWorkingTimes().getWorkingDays()) {

				if(wt.getStartTime().isBefore(time) && wt.getEndTime().isAfter(time) && wt.getDay().equals(day)) {	
					System.out.println("radi u " + pharmacist.getWorkingTimes().getPharmacy());
					isWorking=true;
					break;
				}
			}
			
			for(HolidayRequest hol: pharmacist.getHolidayRequests()) {
				System.out.println("Holiday"+pharmacist.getHolidayRequests());
				if(hol.getStartDate().isBefore(date) && hol.getEndDate().isAfter(date) && hol.getStatus().equals(HolidayRequestStatus.ACCEPT)){
					System.out.println("Tad je na godisnjem");
					isWorking= false;
				}
			}
			
			if(isWorking) {
				
				String grade=Double.toString(pharmacistService.getAvrageGrade(pharmacist));
				if(grade.equals("NaN")) {
					grade="";
				}
				result.add(new PharmacyForCounselingView(pharmacist.getWorkingTimes().getPharmacy().getId(),pharmacist.getWorkingTimes().getPharmacy().getName(),pharmacist.getWorkingTimes().getPharmacy().getAddress().getCity(),pharmacist.getWorkingTimes().getPharmacy().getAddress().getStreet(),
						grade,"cena"));
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
		LocalDate date=LocalDate.parse(term.split("T")[0]);
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
				for(HolidayRequest hol: pharmacist.getHolidayRequests()) {
					System.out.println("Holiday"+pharmacist.getHolidayRequests());
					System.out.println("godisnji od" + hol.getStartDate());
					System.out.println("godisnji do"+hol.getEndDate());
					System.out.println(date);
					System.out.println(hol.getStatus());
					if(hol.getStartDate().isBefore(date) && hol.getEndDate().isAfter(date) && hol.getStatus().equals(HolidayRequestStatus.ACCEPT)){
						isWorking= false;
						break;
					}
				}
				if(isWorking) {
					result.add(new PharamcistForCounselingView(pharmacist.getId(),pharmacist.getUser().getFirstName(),pharmacist.getUser().getLastName(),pharmacistService.getAvrageGrade(pharmacist)));
				}
				else {
					continue;
				}
			}
		}
		
		return result;
	}

	@Override
	public List<PatientsCounslingView> getPatientsCounlings(Long id) {
		List<PatientsCounslingView> result=new ArrayList<PatientsCounslingView>();
		LocalDateTime today=LocalDateTime.now();
		for(PharmacistCounseling pc: findByPatientId(id)) {
			boolean isExpaired=false;
			Pharmacy pharmacy=pc.getPharmacist().getWorkingTimes().getPharmacy();
			if(pc.getStartDateTime().isBefore(today.minusDays(1))) {
				isExpaired=true;
			}
			PatientsCounslingView pcv=new PatientsCounslingView(pc.getId(),pc.getPharmacist().getUser().getFirstName(),pc.getPharmacist().getUser().getLastName(),
					pharmacy.getName(),pharmacy.getAddress().getCity(),pharmacy.getAddress().getStreet(),pc.getStartDateTime(),pc.getDuration(),"cena",isExpaired);
			result.add(pcv);
		}
		return result;
	}

	@Override
	public Boolean cancelAppointment(Long id) {
		LocalDateTime today=LocalDateTime.now();
		for(PharmacistCounseling pc: pharmacistCounselingRepository.findAll()) {
			if(pc.getId().equals(id)) {
				if(pc.getStartDateTime().isBefore(today.minusDays(1))) {
					return false;
				}
				pc.setPharmacist(null);
				pc.setPatient(null);
				pc.setTherapy(null);
				this.pharmacistCounselingRepository.delete(pc);
				return true;
			}	
		}
		return false;
		
	}

}
