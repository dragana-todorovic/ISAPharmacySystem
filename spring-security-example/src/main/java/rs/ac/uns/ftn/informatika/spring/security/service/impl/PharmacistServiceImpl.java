package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistAppoitment;
import rs.ac.uns.ftn.informatika.spring.security.model.HolidayRequest;
import rs.ac.uns.ftn.informatika.spring.security.model.HolidayRequestStatus;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacist;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistCounseling;
import rs.ac.uns.ftn.informatika.spring.security.model.Therapy;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.AppointmentDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.HolidayRequestDTO;
import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MyPatientDTO;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.HolidayRequestRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistCounselingRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistRepository;
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
	private PharmacistCounselingService pharmacistCounselingService;
	@Autowired
	private MedicineRepository medicineRepository;
	@Autowired
	private PharmacistCounselingRepository pharmacistCounselingRepository;
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
					
					d.setDuration(Integer.parseInt(appointmantDTO.getDuration()));
					pharmacistCounselingRepository.save(d);
				}
			}
	
		}catch (Exception e) {
			return;
		}
		}
}


