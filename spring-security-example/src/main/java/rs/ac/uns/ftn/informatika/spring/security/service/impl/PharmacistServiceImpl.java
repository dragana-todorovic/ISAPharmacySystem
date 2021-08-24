package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.AppoitmentPrice;
import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.model.HolidayRequest;
import rs.ac.uns.ftn.informatika.spring.security.model.HolidayRequestStatus;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservation;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineReservationStatus;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacist;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounseling;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounselingPrice;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.Rating;
import rs.ac.uns.ftn.informatika.spring.security.model.RequestForMedicineAvailability;
import rs.ac.uns.ftn.informatika.spring.security.model.Therapy;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.WorkingDay;
import rs.ac.uns.ftn.informatika.spring.security.model.WorkingTime;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.AppointmentDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.HolidayRequestDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MyPatientDTO;
import rs.ac.uns.ftn.informatika.spring.security.repository.AppointmentPriceRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.HolidayRequestRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineWithQuantityRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistCounselingPriceRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistCounselingRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.RequestForMedicineAvailabilityRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.UserRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.DermatologistAppointmentService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacistCounselingService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacistService;

@Service
public class PharmacistServiceImpl implements PharmacistService {

	@Autowired
	private PharmacistRepository pharmacistRepository;
	@Autowired
	private HolidayRequestRepository holidayRequestRepository;
	@Autowired
	private AppointmentPriceRepository appointmentPriceRepository;
	@Autowired
	private PharmacistCounselingPriceRepository pharmacistCounselingPriceRepository;
	@Autowired
	private PharmacistCounselingService pharmacistCounselingService;
	@Autowired
	private RequestForMedicineAvailabilityRepository requestForMedicineAvailabilityRepository;
	@Autowired
	private MedicineRepository medicineRepository;
	@Autowired
	private PharmacistCounselingRepository pharmacistCounselingRepository;
	@Autowired
	private DermatologistAppointmentService  dermatologistAppointmentService;
	@Autowired
	private MedicineWithQuantityRepository  medicineWithQuantityRepository;
	@Autowired
	private UserRepository userRepository;
	@Override
	public void saveHolidayRequest(HolidayRequestDTO holidayRequest) {
	try{
		User u = userRepository.findByEmail(holidayRequest.getEmail());
		System.out.println("U"+u);
		for (Pharmacist d: pharmacistRepository.findAll()) {
		System.out.println("Hahahahh"+d.getUser().getEmail());
			d.getUser().getEmail();
			if(d.getUser().getEmail().equals(u.getEmail()))
			{
		System.out.println("Usao u if");		
		 HolidayRequest req = new HolidayRequest();
		 LocalDate localStartDate = LocalDate.parse(holidayRequest.getStartDate());
		 LocalDate localEndDate = LocalDate.parse(holidayRequest.getEndDate());
		 req.setStartDate(localStartDate);
		 req.setEndDate(localEndDate);
		 req.setStatus(HolidayRequestStatus.ON_HOLD);
		 d.getHolidayRequests().add(req);
		 //this.holidayRequestRepository.save(req);		
		 this.pharmacistRepository.save(d);
			}}} catch (Exception e) {
		return;
	}
		
	
	}

	@Override
	public List<Pharmacist> findAll() {
		List<Pharmacist> result = pharmacistRepository.findAll();
		return result;
	}
	@Override
	public List<MyPatientDTO> getPatientsForAppointment(String email) {
		try {
			for (Pharmacist d: pharmacistRepository.findAll()) {
				System.out.println("Dermatolog"+d.getUser().getEmail());
				if(d.getUser().getEmail().equals(email))
				{
					System.out.println("Usao u if");
				List <MyPatientDTO> myPatients =new ArrayList<MyPatientDTO>();
				List<PharmacistCounseling> appointments = pharmacistCounselingService.findById(d.getId());
				for (PharmacistCounseling da :appointments) {
					
					System.out.println("Usao u for"+da.getPharmacist().getUser().getFirstName());
					if(da.getPatient()!=null && da.getStartDateTime().isAfter(LocalDateTime.now())) {
						System.out.println("Usao u if"+"#################################");
						MyPatientDTO myPatientDTO = new MyPatientDTO();
						myPatientDTO.setMyPatientId(da.getPatient().getId());
						myPatientDTO.setName(da.getPatient().getUser().getFirstName());
						myPatientDTO.setSurname(da.getPatient().getUser().getLastName());
						myPatientDTO.setStartDateTime(da.getStartDateTime());
						myPatients.add(myPatientDTO);
						System.out.println("My patients*********************************"+myPatients.size());
					}
				}
				System.out.println("Broooooj jeee"+myPatients.size());
				return myPatients;
				}
				}
			
	} catch (Exception e) {
		System.out.println("CATCHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
		List <MyPatientDTO> myPatients =new ArrayList<MyPatientDTO>();
		return myPatients;
	}
		System.out.println("NULLLLLLLLLLLLLLLLLLLLLLLLLLL");
		return null;
	}
	@Override
	public List<Medicine> getMedicines() {
		List<Medicine> result = medicineRepository.findAll();
		return result;
	}

	@Override
	public List<MyPatientDTO> myPatients(String email) {
		try {
			for (Pharmacist d: pharmacistRepository.findAll()) {
				System.out.println("Dermatolog"+d.getUser().getEmail());
				if(d.getUser().getEmail().equals(email))
				{
					System.out.println("Usao u if");
				List <MyPatientDTO> myPatients =new ArrayList<MyPatientDTO>();
				List<PharmacistCounseling> appointments = pharmacistCounselingService.findById(d.getId());
				for (PharmacistCounseling da :appointments) {
					System.out.println("Usao u for"+da.getPharmacist().getUser().getFirstName());
					if(da.getPatient()!=null  && da.getStartDateTime().isBefore(LocalDateTime.now())) {
						
						MyPatientDTO myPatientDTO = new MyPatientDTO();
						myPatientDTO.setMyPatientId(da.getPatient().getId());
						myPatientDTO.setName(da.getPatient().getUser().getFirstName());
						myPatientDTO.setSurname(da.getPatient().getUser().getLastName());
						myPatientDTO.setStartDateTime(da.getStartDateTime());
						myPatients.add(myPatientDTO);
					}
				}
				return myPatients;
				}
				}
			
	} catch (Exception e) {
		List <MyPatientDTO> myPatients =new ArrayList<MyPatientDTO>();
		return myPatients;
	}
		return null;
	}
	@Override
	public void saveAppointment(AppointmentDTO appointmantDTO,Pharmacy pharmacy) {
		System.out.println("AppointmentDTO"+appointmantDTO.getPatientId());
		LocalDateTime startDateTime=LocalDateTime.parse(appointmantDTO.getStartDate());
		try{
			for(PharmacistCounseling d:pharmacistCounselingRepository.findAll()) {
				if(d.getPharmacist().getUser().getEmail().equals(appointmantDTO.getDermatologistEmail()) && d.getPatient().getUser().getEmail().equals(appointmantDTO.getPatientEmail()) && d.getStartDateTime().equals(startDateTime)) {
					d.setDescription(appointmantDTO.getDiagnosis());
					d.setStartDateTime(LocalDateTime.now());
					
					System.out.println("Medicineee namee"+appointmantDTO.getMedicineName());
					if(appointmantDTO.getMedicineName()!="") {
					Therapy therapy = new Therapy();
					System.out.println("Ispod medicine"+medicineRepository.findById(Long.parseLong(appointmantDTO.getMedicineName().split(",sifra ")[1])).get().getName());
					therapy.setMedicine(medicineRepository.findById(Long.parseLong(appointmantDTO.getMedicineName().split(",sifra ")[1])).get());
					therapy.setDuration(Integer.parseInt(appointmantDTO.getTherapyDuration()));
					d.setTherapy(therapy);
					}
					
					pharmacistCounselingRepository.save(d);
					System.out.println("DDDDDDDDDDDDDD"+d);
					System.out.println("DDDDDDDDDDDDDDDDDDDD"+d.getId());
					PharmacistCounselingPrice price = new PharmacistCounselingPrice();
					price.setPrice(Double.parseDouble(appointmantDTO.getPrice()));
					price.setCounseling(d);
					pharmacistCounselingPriceRepository.save(price);
			
					Medicine  prescribedMedicine = medicineRepository.findById(Long.parseLong(appointmantDTO.getMedicineName().split(",sifra ")[1])).get();
					
					
					for(MedicineWithQuantity m:pharmacy.getMedicineWithQuantity()) {
						if(m.getMedicine().getId().equals(prescribedMedicine.getId())) {
							m.setQuantity(m.getQuantity() - 1);
							medicineWithQuantityRepository.save(m);
							return;
						}
					}
					
					
				}
			}
	
		}catch (Exception e) {
			return;
		}
		}
	@Override
	public void saveAppointment(AppointmentDTO appointmantDTO) {
		try{
			for(PharmacistCounseling d:pharmacistCounselingService.findAll()) {
				if(d.getPharmacist().getUser().getEmail().equals(appointmantDTO.getDermatologistEmail()) && d.getPatient().getUser().getEmail().equals(appointmantDTO.getPatientEmail())) {
					d.setDescription(appointmantDTO.getDiagnosis());
					d.setStartDateTime(LocalDateTime.now());
					
					System.out.println("Medicineee namee"+appointmantDTO.getMedicineName());
					if(appointmantDTO.getMedicineName()!="") {
					Therapy therapy = new Therapy();
					therapy.setMedicine(medicineRepository.findByName(appointmantDTO.getMedicineName()));
					d.setTherapy(therapy);
					}
					
					
					pharmacistCounselingRepository.save(d);
				}
			}
	
		}catch (Exception e) {
			return;
		}
		}
	@Override
	public Boolean isAppointmentAvailableForScheduling(Pharmacist pharmacist,Patient patient,Integer duration,Pharmacy pharmacy,LocalDate startDate, LocalDateTime startDateTime,LocalDateTime endDateTime) {
		if(!isPharmacistWorkingTime(pharmacist,duration, pharmacy,startDate, startDateTime)) 
				{return false;}
		if(!isPharmacistWorkingTime(pharmacist,duration, pharmacy,startDate, endDateTime)){
					return false;
			}
		if(!isPharmacistAvailable(pharmacist, duration, startDateTime)) {
			return false;
		}
		if(!isPatientAvailable(patient, duration, startDateTime)) {
			return false;
		}
		
		return true;
	}
	private Boolean isPharmacistAvailable(Pharmacist pharmacist,Integer duration, LocalDateTime startDateTime) {
		System.out.println("Dermatologist id"+pharmacist.getId());
		List<PharmacistCounseling> appointments = pharmacistCounselingService.findById(pharmacist.getId());
		System.out.println("Apointments################+++++++++++++"+appointments.size());
		for(PharmacistCounseling da:appointments) {
			if (da.getStartDateTime().toLocalDate().equals(startDateTime.toLocalDate())) {
				if (isTimeFine(startDateTime.toLocalTime(), duration, da.getStartDateTime().toLocalTime(),
						da.getStartDateTime().toLocalTime().plusMinutes(da.getDuration())))
				{
					return false;}
			}
		}
		return true;
		
	}
	private Boolean isPatientAvailable(Patient patient,Integer duration, LocalDateTime startDateTime) {
		if(patient==null) {
			System.out.println("Usao u null");
			return true;
		}
		System.out.println("Usao u patient available");
		List<PharmacistCounseling> counselings = pharmacistCounselingService.findByPatientId(patient.getId());
		
		for(PharmacistCounseling da:counselings) {
			System.out.println("Counseling" + da);
			if (da.getStartDateTime().toLocalDate().equals(startDateTime.toLocalDate())) {
				if (isTimeFine(startDateTime.toLocalTime(), duration, da.getStartDateTime().toLocalTime(),
						da.getStartDateTime().toLocalTime().plusMinutes(da.getDuration())))
					return false;
			}
		}
		
		
		List<DermatologistAppointment> appointments = dermatologistAppointmentService.findByPatientId(patient.getId());
		for(DermatologistAppointment da:appointments) {
			if (da.getStartDateTime().toLocalDate().equals(startDateTime.toLocalDate())) {
				if (isTimeFine(startDateTime.toLocalTime(), duration, da.getStartDateTime().toLocalTime(),
						da.getStartDateTime().toLocalTime().plusMinutes(da.getDuration())))
					return false;
			}
		}
		return true;
		
	}
	private Boolean isTimeFine(LocalTime time, int duration, LocalTime startTime, LocalTime endTime){
		if (!time.isBefore(startTime) && !time.plusMinutes(duration).isAfter(endTime))
			return true;
		if (time.isAfter(startTime) && time.isBefore(endTime))
			return true;
		if (time.plusMinutes(duration).isAfter(startTime) && time.plusMinutes(duration).isBefore(endTime))
			return true;
		if (time.equals(startTime))
			return true;
		return false;
		
	}
	private Boolean isPharmacistWorkingTime(Pharmacist pharmacist,Integer duration,Pharmacy pharmacy,LocalDate startDate,LocalDateTime startDateTime) {
		Boolean isPharmacistWorking = false;
		DayOfWeek day = startDateTime.getDayOfWeek();
		System.out.println("Usao u funkciju");
		System.out.println("Dermatolog"+pharmacist);
		System.out.println("Pharmacy"+pharmacy);
		System.out.println("Start date"+startDate);
		System.out.println("Start date time"+startDateTime);
		System.out.println(pharmacist.getWorkingTimes());
		if(pharmacist.getWorkingTimes().getWorkingDays().isEmpty()) {
			return false;
		}
		

		for(WorkingDay work:pharmacist.getWorkingTimes().getWorkingDays()) {
			if(work.getDay().equals(day)) {				
					isPharmacistWorking = true;	
			}
			if(!isTimeFine(startDateTime.toLocalTime(), duration, work.getStartTime(), work.getEndTime())) {
				return false;
			}
		}
	
		
		if(!isPharmacistWorking) {
			return false;
		}
		
		for(HolidayRequest hol: pharmacist.getHolidayRequests()) {
			System.out.println("Holiday"+pharmacist.getHolidayRequests());
			if(!startDate.isBefore(hol.getStartDate()) && !startDate.isAfter(hol.getEndDate()) && hol.getStatus().equals(HolidayRequestStatus.ACCEPT)){
				return false;
			}
		}
		
	
		return true;
	}
	@Override
	public Boolean isMedicineAvailable(Pharmacy pharmacy, String medicineId) {
		Set<MedicineWithQuantity> medicines = pharmacy.getMedicineWithQuantity();
		for(MedicineWithQuantity mq:medicines) {
			System.out.println("****************Quantity"+mq.getMedicine().getName()+mq.getQuantity());
		}
		for(MedicineWithQuantity mq: medicines) {
			
			if(mq.getMedicine().getId().equals(Long.parseLong(medicineId)) ) {
				if(mq.getQuantity()==0) {
					RequestForMedicineAvailability rq = new RequestForMedicineAvailability();
					rq.setCreatedAt(LocalDateTime.now());
					rq.setMedicineWithQuantity(mq);
					rq.setPharmacy(pharmacy);
					requestForMedicineAvailabilityRepository.save(rq);
					
					return false;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public List<MedicineReservation>searchReservedMedicnes(String resNumber,Pharmacy pharmacy){
		List<MedicineReservation>result = new ArrayList<MedicineReservation>();
		for(MedicineReservation m:pharmacy.getMedicineReservations()) {
			LocalDateTime dt = LocalDateTime.of(m.getDueTo(), m.getDueToTime());
			if(m.getNumberOfReservation().toString().toLowerCase().contains(resNumber.toLowerCase()) && m.getStatus().equals(MedicineReservationStatus.RESERVED) && dt.isAfter(LocalDateTime.now()) ) {
				System.out.println("Rezervacija"+m.getNumberOfReservation());
				result.add(m);				
			}
		}
		return result;
	}

	@Override
	public double getAvrageGrade(Pharmacist pharmacist) {
		double avrage_grade=0;
		int pom=0;
		for(Rating ratings : pharmacist.getRatings()) {
			pom++;
			avrage_grade+=ratings.getRating();
		}
		return avrage_grade/pom;
	}
	
	
}


