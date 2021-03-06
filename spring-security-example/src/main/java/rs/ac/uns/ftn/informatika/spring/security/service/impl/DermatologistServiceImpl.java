package rs.ac.uns.ftn.informatika.spring.security.service.impl;
import java.util.Date;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.AppoitmentPrice;
import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppointment;
import rs.ac.uns.ftn.informatika.spring.security.model.HolidayRequest;
import rs.ac.uns.ftn.informatika.spring.security.model.HolidayRequestStatus;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounseling;
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
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistAppointmentRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.HolidayRequestRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineWithQuantityRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PatientRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.RequestForMedicineAvailabilityRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.UserRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.DermatologistAppointmentService;
import rs.ac.uns.ftn.informatika.spring.security.service.DermatologistService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacistCounselingService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyService;
import rs.ac.uns.ftn.informatika.spring.security.service.RatingService;
import rs.ac.uns.ftn.informatika.spring.security.view.RatingView;

@Service
public class DermatologistServiceImpl implements DermatologistService{

	@Autowired
	private DermatologistRepository dermatologistRepository;
	@Autowired
	private MedicineWithQuantityRepository medicineWithQuantityRepository;
	@Autowired
	private AppointmentPriceRepository appointmentPriceRepository;
	@Autowired
	private DermatologistAppointmentService dermatologistAppointmentService;
	@Autowired
	private RequestForMedicineAvailabilityRepository requestForMedicineAvailabilityRepository;
	@Autowired
	private DermatologistAppointmentRepository dermatologistAppointmentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MedicineRepository medicineRepository;

	@Autowired
	private PharmacistCounselingService pharmacistCounselingService;
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private RatingService ratingService;
	
	
	@Override
	public Boolean saveHolidayRequest(HolidayRequestDTO holidayRequest) {
	
	try{
		User u = userRepository.findByEmail(holidayRequest.getEmail());
		for (Dermatologist d: dermatologistRepository.findAll()) {
		
			if(d.getUser().getEmail().equals(u.getEmail()))
			{
		 Pharmacy pharmacy = pharmacyService.getPharmacyByDermatologistAndStartDate(d, LocalDateTime.now());
		 if(pharmacy==null) {
			 return false;
		 }		
		 HolidayRequest req = new HolidayRequest();
		 LocalDate localStartDate = LocalDate.parse(holidayRequest.getStartDate());
		 LocalDate localEndDate = LocalDate.parse(holidayRequest.getEndDate());
		 req.setStartDate(localStartDate);
		 req.setEndDate(localEndDate);
		 req.setStatus(HolidayRequestStatus.ON_HOLD);
		 req.setPharmacy(pharmacy);		
		 d.getHolidayRequests().add(req);	
		 this.dermatologistRepository.save(d);
		 return true;
			}}} catch (Exception e) {
		return false;
	}
		return false;
	
	}

	@Override
	public List<Dermatologist> findAll() {
		List<Dermatologist> result = dermatologistRepository.findAll();
		return result;
	}
	@Override
	public List<WorkCalendarDTO> getAppointmentsForCalendar(Dermatologist dermatologist,Pharmacy choosenPharmacy) {
		List<WorkCalendarDTO> result = new ArrayList<WorkCalendarDTO>();
		List<DermatologistAppointment> appointments =dermatologistAppointmentService.findAll();
		
		List<DermatologistAppointment> app = new ArrayList<DermatologistAppointment>();
		for(DermatologistAppointment da :appointments ) {
			if(da.getDermatologist().getId().equals(dermatologist.getId())) {
				if(da.getPharmacy().getId().equals(choosenPharmacy.getId())) {
				app.add(da);
			}}		
			}
		
		for(DermatologistAppointment d:app) {			
			if(d.getPatient()!=null ) {	
		
				result.add(new WorkCalendarDTO(d.getId(),choosenPharmacy.getName(),d.getStartDateTime().toLocalDate(),d.getStartDateTime().toString(),d.getDuration(),d.getPatient().getUser().getFirstName(),d.getPatient().getUser().getLastName()));
			}
			else if(d.getPatient()==null){
					result.add(new WorkCalendarDTO(d.getId(),choosenPharmacy.getName(),d.getStartDateTime().toLocalDate(),d.getStartDateTime().toString(),d.getDuration()));	
				}
				
		}
		return result;
	}
	@Override
	public List<MyPatientDTO> myPatients(String email) {
		try {
			for (Dermatologist d: dermatologistRepository.findAll()) {
				System.out.println("Dermatolog"+d.getUser().getEmail());
				if(d.getUser().getEmail().equals(email))
				{
					System.out.println("Usao u if");
				List <MyPatientDTO> myPatients =new ArrayList<MyPatientDTO>();
				List<DermatologistAppointment> appointments = dermatologistAppointmentService.findById(d.getId());
				if(appointments==null) {
					return new ArrayList<MyPatientDTO>();
				}
				for (DermatologistAppointment da :appointments) {
					
					System.out.println("Usao u for"+da.getDermatologist().getUser().getFirstName());
					if(da.getPatient()!=null && da.getStartDateTime().isBefore(LocalDateTime.now())) {
						
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
	public List<MyPatientDTO> getPatientsForAppointment(String email) {
		try {
			for (Dermatologist d: dermatologistRepository.findAll()) {
				System.out.println("Dermatolog"+d.getUser().getEmail());
				if(d.getUser().getEmail().equals(email))
				{
				System.out.println("Usao u if");
				List <MyPatientDTO> myPatients =new ArrayList<MyPatientDTO>();
				List<DermatologistAppointment> appointments = dermatologistAppointmentService.findById(d.getId());
				for (DermatologistAppointment da :appointments) {
					if(da.getPatient()!=null && da.getStartDateTime().isAfter(LocalDateTime.now())) {
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
		System.out.println("CATCH");
		List <MyPatientDTO> myPatients =new ArrayList<MyPatientDTO>();
		return myPatients;
	}
		System.out.println("NULL");
		return null;
	}
	//startDate za provjeru da li je taj dan 
		//startDateTime za provjeru vremena
		//moram provjeriti i za start i za end, jer ako pregled traje pola sata moram vidjeti da li je pregled ukljucen u radno vrijeme
		@Override
		public Boolean isMedicineAvailable(Pharmacy pharmacy, String medicineId) {
			Set<MedicineWithQuantity> medicines = pharmacy.getMedicineWithQuantity();
			if(medicines==null) {
				return false;
			}
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
	public List<Medicine> getMedicines() {
		List<Medicine> result = medicineRepository.findAll();
		return result;
	}

	@Override
	public void saveAppointment(AppointmentDTO appointmantDTO,Pharmacy pharmacy) {
		System.out.println("AppointmentDTO"+appointmantDTO.getPatientId());
		LocalDateTime startDateTime=LocalDateTime.parse(appointmantDTO.getStartDate());
		try{
			System.out.println("Usao u try");
			for(DermatologistAppointment d:dermatologistAppointmentRepository.findAll()) {
				if(d.getPatient()!=null) {
				
				
				if(d.getDermatologist().getUser().getEmail().equals(appointmantDTO.getDermatologistEmail()) && d.getPatient().getUser().getEmail().equals(appointmantDTO.getPatientEmail()) && d.getStartDateTime().equals(startDateTime)) {
					System.out.println("Usao u if");
					d.setDescription(appointmantDTO.getDiagnosis());
					d.setStartDateTime(LocalDateTime.now());					
					
					if(appointmantDTO.getMedicineName()!="") {
					Therapy therapy = new Therapy();
					therapy.setMedicine(medicineRepository.findById(Long.parseLong(appointmantDTO.getMedicineName().split(",sifra ")[1])).get());
					therapy.setDuration(Integer.parseInt(appointmantDTO.getTherapyDuration()));
					d.setTherapy(therapy);
					}
					
					dermatologistAppointmentRepository.save(d);
					AppoitmentPrice price = appointmentPriceRepository.findByAppintmentId(d.getId());
					price.setAppoitment(d);
					price.setPrice(Double.parseDouble(appointmantDTO.getPrice()));
					appointmentPriceRepository.save(price);
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
				}
	
		}catch (Exception e) {
			return;
		}
		}

	@Override
	public Dermatologist save(Dermatologist dermatologist) {
		Dermatologist newDerm = this.dermatologistRepository.save(dermatologist);
		return newDerm;
	}
	@Override
	public Boolean isAppointmentAvailableForScheduling(Dermatologist dermatologist,Patient patient,Integer duration,Pharmacy pharmacy,LocalDate startDate, LocalDateTime startDateTime,LocalDateTime endDateTime) {
		if(!isDermatologistWorkingTime(dermatologist,duration, pharmacy,startDate, startDateTime)) 
				{return false;}
		if(!isDermatologistWorkingTime(dermatologist,duration, pharmacy,startDate, endDateTime)){
					return false;
			}
		if(!isDermatologistAvailable(dermatologist, duration, startDateTime)) {
			return false;
		}
		if(!isPatientAvailable(patient, duration, startDateTime)) {
			return false;
		}
		
		return true;
	}

	private Boolean isDermatologistAvailable(Dermatologist dermatologist,Integer duration, LocalDateTime startDateTime) {
		System.out.println("Dermatologist id"+dermatologist.getId());
		List<DermatologistAppointment> appointments = dermatologistAppointmentService.findById(dermatologist.getId());
		System.out.println("Apointments"+appointments.size());
		for(DermatologistAppointment da:appointments) {
			if (da.getStartDateTime().toLocalDate().equals(startDateTime.toLocalDate())) {
				if (isTimeFine(startDateTime.toLocalTime(), duration, da.getStartDateTime().toLocalTime(),
						da.getStartDateTime().toLocalTime().plusMinutes(da.getDuration())) && da.getPatient()!=null)
				{
					System.out.println("************"+da.getPatient());
					System.out.println("Dermatolog nije slobodan");
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
						da.getStartDateTime().toLocalTime().plusMinutes(da.getDuration())) && da.getPatient()!=null)
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
		System.out.println(startTime);
		System.out.println(duration);
		System.out.println(endTime);
		System.out.println(time);
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
private Boolean isDermatologistWorkingTime(Dermatologist dermatologist,Integer duration,Pharmacy pharmacy,LocalDate startDate,LocalDateTime startDateTime) {
		Boolean isDermatologistWorking = false;
		DayOfWeek day = startDateTime.getDayOfWeek();
		System.out.println("Usao u funkciju");
		System.out.println("Dermatolog"+dermatologist);
		System.out.println("Pharmacy"+pharmacy);
		System.out.println("Start date"+startDate);
		System.out.println("Start date time"+startDateTime);
		System.out.println(dermatologist.getWorkingTimes());
		if(dermatologist.getWorkingTimes().isEmpty()) {
			return false;
		}
		
		WorkingTime workingTime = new WorkingTime();
		for(WorkingTime work:dermatologist.getWorkingTimes()) {
			if(work.getPharmacy().getId().equals(pharmacy.getId())) {				
					workingTime = work;	
			}
		}
		if(workingTime==null || workingTime.getWorkingDays() == null || workingTime.getWorkingDays().isEmpty()) {
			return false;
		}
		System.out.println("Working time"+workingTime);
		System.out.println("Working day"+workingTime.getWorkingDays());
		for(WorkingDay d:workingTime.getWorkingDays()) {
			System.out.println(d.getDay());
			if(d.getDay().equals(day)) {
				
				isDermatologistWorking = true;
				System.out.println(startDateTime.toLocalTime());
				System.out.println(d.getEndTime());
				System.out.println(d.getStartTime());
				if(!isTimeFine(startDateTime.toLocalTime(), duration, d.getStartTime(), d.getEndTime())) {
					System.out.println("Usao u false za preklapanje termina");
					return false;
				}
				
			}
		}
		
		if(!isDermatologistWorking) {
			System.out.println("Usao u false za radno vrijeme");
			return false;
		}
		
		for(HolidayRequest hol: dermatologist.getHolidayRequests()) {
			System.out.println("Apoteka"+hol.getPharmacy());
			System.out.println("Apoteka"+pharmacy);
			if(hol.getPharmacy().equals(pharmacy)) {
			System.out.println("Holiday"+hol.getStartDate()+hol.getStatus());
			System.out.println(startDate);
			
			if(!startDate.isBefore(hol.getStartDate()) && !startDate.isAfter(hol.getEndDate()) && hol.getStatus().equals(HolidayRequestStatus.ACCEPT)){
				return false;
			}
		}}
		
	
		return true;
	}

	@Override
	public Double getAvrageGrade(Dermatologist dermatologist) {
		double avrage_grade=0;
		int pom=0;
		for(Rating ratings : dermatologist.getRatings()) {
			pom++;
			avrage_grade+=ratings.getRating();
		}
		return avrage_grade/pom;
	}

	@Override
	public List<RatingView> getAllDermPatientCanEvaluate(long patient) {
		List<RatingView> result=new ArrayList<RatingView>();
		List<Dermatologist> pom=new ArrayList<Dermatologist>();
		for(DermatologistAppointment app: this.dermatologistAppointmentRepository.findAll()) {
			if(app.getPatient()==null) {
				continue;
			}
			if(app.getPatient().getId() == patient) {
				if(app.getStartDateTime().isBefore( LocalDateTime.now()) && !pom.contains(app.getDermatologist())) {
					pom.add(app.getDermatologist());
					}
				}
					
			}
		for(Dermatologist d : pom) {
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
	public void changeRating(int rating, long patient, Long dermatologistId) {
		Dermatologist derm=dermatologistRepository.findDermatologistById(dermatologistId);	
		Rating rat=new Rating();
		if(derm.getRatings().isEmpty()) {
			rat.setPatient(patient);
			rat.setRating(rating);
			derm.getRatings().add(rat);
		}
		for(Rating r : derm.getRatings()) {
			if(r.getPatient() ==patient) {
				r.setRating(rating);
			}
		}
		this.dermatologistRepository.save(derm);
		
	}

}