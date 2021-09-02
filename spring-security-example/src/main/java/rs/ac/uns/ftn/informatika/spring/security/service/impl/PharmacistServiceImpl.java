package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
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
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.WorkCalendarDTO;
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
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyService;
import rs.ac.uns.ftn.informatika.spring.security.view.RatingView;

@Service
public class PharmacistServiceImpl implements PharmacistService {

	@Autowired
	private PharmacistRepository pharmacistRepository;

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
	public Boolean saveHolidayRequest(HolidayRequestDTO holidayRequest) {
	try{
		User u = userRepository.findByEmail(holidayRequest.getEmail());
		System.out.println("U"+u);
		for (Pharmacist d: pharmacistRepository.findAll()) {
			if(d.getUser().getEmail().equals(u.getEmail()))
			{
		if(d.getWorkingTimes()==null) {
			return false;
		}
	    Pharmacy pharmacy = d.getWorkingTimes().getPharmacy();
		if(pharmacy==null) {
					 return false;
				 }
		 System.out.println("Usao u if");		
		 HolidayRequest req = new HolidayRequest();
		 LocalDate localStartDate = LocalDate.parse(holidayRequest.getStartDate());
		 LocalDate localEndDate = LocalDate.parse(holidayRequest.getEndDate());
		 req.setStartDate(localStartDate);
		 req.setEndDate(localEndDate);
		 req.setStatus(HolidayRequestStatus.ON_HOLD);
		 req.setPharmacy(pharmacy);
		 d.getHolidayRequests().add(req);	
		 this.pharmacistRepository.save(d);
		 return true;
			}}} catch (Exception e) {
		return false;
	}
	return false;	
	
	}

	@Override
	public List<Pharmacist> findAll() {
		List<Pharmacist> result = pharmacistRepository.findAll();
		System.out.println("Rezultat"+result.size());
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
				if(appointments==null) {
					return new ArrayList<MyPatientDTO>();
				}
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
					
			
					if(appointmantDTO.getMedicineName()!="") {
					Therapy therapy = new Therapy();
					therapy.setMedicine(medicineRepository.findById(Long.parseLong(appointmantDTO.getMedicineName().split(",sifra ")[1])).get());
					therapy.setDuration(Integer.parseInt(appointmantDTO.getTherapyDuration()));
					d.setTherapy(therapy);
					}
					
					pharmacistCounselingRepository.save(d);
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
				if (isTimeIncluded(startDateTime.toLocalTime(), duration, da.getStartDateTime().toLocalTime(),
						da.getStartDateTime().toLocalTime().plusMinutes(da.getDuration())) && da.getPatient()!=null)
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
				if (isTimeIncluded(startDateTime.toLocalTime(), duration, da.getStartDateTime().toLocalTime(),
						da.getStartDateTime().toLocalTime().plusMinutes(da.getDuration())) && da.getPatient()!=null)
					return false;
			}
		}
		
		
		List<DermatologistAppointment> appointments = dermatologistAppointmentService.findByPatientId(patient.getId());
		for(DermatologistAppointment da:appointments) {
			if (da.getStartDateTime().toLocalDate().equals(startDateTime.toLocalDate())) {
				if (isTimeIncluded(startDateTime.toLocalTime(), duration, da.getStartDateTime().toLocalTime(),
						da.getStartDateTime().toLocalTime().plusMinutes(da.getDuration())))
					return false;
			}
		}
		return true;
		
	}
	private Boolean isTimeIncluded(LocalTime time, int duration, LocalTime startTime, LocalTime endTime){
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
			if(!isTimeIncluded(startDateTime.toLocalTime(), duration, work.getStartTime(), work.getEndTime())) {
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
		Boolean hasMedicine = false;
		for(MedicineWithQuantity mq:medicines) {
			if(mq.getMedicine().getId().equals(Long.parseLong(medicineId))) {
				hasMedicine=true;
			}
		}
		if(hasMedicine==false) {
			RequestForMedicineAvailability rq = new RequestForMedicineAvailability();
			rq.setCreatedAt(LocalDateTime.now());
			Medicine medicine = medicineRepository.findById(Long.parseLong(medicineId)).get();
			MedicineWithQuantity medicineWithQuantity = new MedicineWithQuantity(medicine,0);
			rq.setMedicineWithQuantity(medicineWithQuantity);
			rq.getMedicineWithQuantity().setQuantity(0);					
			rq.setPharmacy(pharmacy);
			requestForMedicineAvailabilityRepository.save(rq);
			
			return false;
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
			System.out.println(m.getNumberOfReservation().toString());
			System.out.println(resNumber);
			System.out.println(m.getNumberOfReservation().toString().toLowerCase().contains(resNumber.toLowerCase()));
			System.out.println(m.getStatus());
			System.out.println(m.getStatus().equals(MedicineReservationStatus.RESERVED));

			LocalDateTime dt = LocalDateTime.of(m.getDueTo(), m.getDueToTime());
			System.out.println(dt.isAfter(LocalDateTime.now()));
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
	public List<WorkCalendarDTO> getPharmacistsCounseling(Pharmacist pharmacist) {
		List<WorkCalendarDTO> result = new ArrayList<WorkCalendarDTO>();
		List<PharmacistCounseling> appointments =pharmacistCounselingService.findAll();
		if(appointments==null) {
			return new ArrayList<WorkCalendarDTO>();
		}
		System.out.println("Size"+appointments.size());
		List<PharmacistCounseling> app = new ArrayList<PharmacistCounseling>();
		for(PharmacistCounseling da :appointments ) {
			System.out.println(da.getPharmacist().getId());
			System.out.println(pharmacist.getId());
			if(da.getPharmacist().getId().equals(pharmacist.getId())) {
				
				app.add(da);
			}
		}
		if(app==null) {
			return new ArrayList<WorkCalendarDTO>();
		}
		for(PharmacistCounseling d:app) {
			if(d.getPatient()!=null) {
			result.add(new WorkCalendarDTO(d.getId(),d.getPharmacist().getWorkingTimes().getPharmacy().getName(),d.getStartDateTime().toLocalDate(),d.getStartDateTime().toString(),d.getDuration(),d.getPatient().getUser().getFirstName(),d.getPatient().getUser().getLastName()));
			}else{
				result.add(new WorkCalendarDTO(d.getId(),d.getPharmacist().getWorkingTimes().getPharmacy().getName(),d.getStartDateTime().toLocalDate(),d.getStartDateTime().toString(),d.getDuration()));	
			}
			}
		return result;
	}

	@Override
	public Pharmacist getPharmacistsById(Long id) {
		return this.pharmacistRepository.findPharmacistById(id);
	}

	@Override
	public List<RatingView> getAllPharmacistsPatientCanEvaluate(long patient) {
		List<RatingView> result=new ArrayList<RatingView>();
		List<Pharmacist> pom=new ArrayList<Pharmacist>();
		for(PharmacistCounseling pc : pharmacistCounselingService.findAll()) {
			if(pc.getPatient()==null) {
				continue;
			}
			if(pc.getPatient().getId().equals(patient)) {
				if(pc.getStartDateTime().isBefore( LocalDateTime.now()) && !pom.contains(pc.getPharmacist())) {
					pom.add(pc.getPharmacist());
					}
				}
			}
		for(Pharmacist d : pom) {
			RatingView rdw=new RatingView(d.getId(),d.getUser().getFirstName(),
					d.getUser().getLastName());
			
			for(Rating ra :d.getRatings()){
				if(ra.getPatient() == patient) {
					rdw.setPatientsGrade(ra.getRating());
								
				} 
			}
			result.add(rdw);	
		}
		
		return result;
	}

	@Override
	public void changeRating(int rating, long patient, Long id) {
		Pharmacist ph=pharmacistRepository.findPharmacistById(id);	
		Rating rat=new Rating();
		if(ph.getRatings().isEmpty()) {
			rat.setPatient(patient);
			rat.setRating(rating);
			ph.getRatings().add(rat);
		}
		for(Rating r : ph.getRatings()) {
			if(r.getPatient() ==patient) {
				r.setRating(rating);
			}
		}
		this.pharmacistRepository.save(ph);
		
	}
	
	
}


