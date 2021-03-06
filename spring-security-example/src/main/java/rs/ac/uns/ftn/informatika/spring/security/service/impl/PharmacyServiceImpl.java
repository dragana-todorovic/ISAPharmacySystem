package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.*;

import rs.ac.uns.ftn.informatika.spring.security.repository.ActionAndBenefitRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistAppointmentRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.RequestForMedicineAvailabilityRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.AuthorityService;
import rs.ac.uns.ftn.informatika.spring.security.service.EPrescriptionService;
import rs.ac.uns.ftn.informatika.spring.security.service.EmailService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.view.ActionAndBenefitDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.EditPharmacyView;

import rs.ac.uns.ftn.informatika.spring.security.view.MedicineReservationView;
import rs.ac.uns.ftn.informatika.spring.security.view.PharmacyWithMedicationView;
import rs.ac.uns.ftn.informatika.spring.security.view.RatingView;
import rs.ac.uns.ftn.informatika.spring.security.view.NewDermatologistDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.NewPharmacistDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.WorkingDayDTO;


import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;

import rs.ac.uns.ftn.informatika.spring.security.model.DTO.MedicineReservationDTO;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.EPrescriptionRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.HolidayRequestRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PatientRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistCounselingRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistRepository;


@Service
public class PharmacyServiceImpl implements PharmacyService{

	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	@Autowired
	private PharmacistRepository pharmacistRepository;
	
	@Autowired
	private ActionAndBenefitRepository actionAndBenefitRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Autowired
	private AuthorityService authService;
	
	@Autowired
	private EPrescriptionService ePrescriptionService;
	
	@Autowired
	private DermatologistAppointmentRepository dermatologistAppoitmentRepository;
	
	@Autowired
	private PharmacistCounselingRepository pharmacistConselingRepository;
	
	@Autowired
	private HolidayRequestRepository holidayRequestRepository;
	
	@Autowired
	private RequestForMedicineAvailabilityRepository requestForMedicineAvailabilityRepository;


	@Autowired
	private  PriceListServiceImpl priceListService;
	
	@Autowired
	private EPrescriptionRepository ePrescriptionRepository;
	
	@Override
	public Optional<Pharmacy> findById(Long id) {
		return pharmacyRepository.findById(id);
	}

	@Override
	public void editPharmacy(EditPharmacyView p) {
		Address a = new Address();
		a.setStreet(p.getStreet());
		a.setCity(p.getCity());
		a.setCoordX(p.getCoordX());
		a.setCoordY(p.getCoordY());
		Pharmacy pharmacy = findById(p.getId()).get();
		pharmacy.setName(p.getName());
		pharmacy.setAddress(a);
		pharmacy.setDescription(p.getDescription());
		
		pharmacyRepository.save(pharmacy);

	}
	
	@Override
	public ActionAndBenefit addNew(ActionAndBenefitDTO actionAndBenefit) {
		try {
			LocalDate start = LocalDate.parse(actionAndBenefit.getStartDate());
		} catch(DateTimeParseException e) {
			return null;
		}
		try {
			LocalDate end = LocalDate.parse(actionAndBenefit.getEndDate());
		} catch(DateTimeParseException e) {
			return null;
		}
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(actionAndBenefit.getPharmacyAdminEmail()));
		
		ActionAndBenefit ab = new ActionAndBenefit();

		ab.setStartDate(LocalDate.parse(actionAndBenefit.getStartDate()));
		ab.setEndDate(LocalDate.parse(actionAndBenefit.getEndDate()));
		ab.setDescription(actionAndBenefit.getDescription());
	
		
		Pharmacy pharmacy =pa.getPharmacy();
		pharmacy.getActionsAndBenefits().add(ab);
		
	List<Patient> patients = this.patientRepository.findAll();
		for(Patient p : patients) {
			for(Long ph : p.getPatientSubscriptions()) {
				if(pa.getPharmacy().getId().equals(ph)) {
					try {
						emailService.sendEmail(p.getUser().getEmail(), "Action or benefit valid from "  + actionAndBenefit.getStartDate() + " to " + actionAndBenefit.getEndDate(), actionAndBenefit.getDescription());
					} catch (MailException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		
		this.pharmacyRepository.save(pharmacy);
		
		return ab;
		
	}

	@Override

	public List<Pharmacy> findAll() {
		List<Pharmacy> result = pharmacyRepository.findAll();
		return result;

	}
	
	public Set<Dermatologist> getDermatologistsByPharmacyAdmin(String email) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		
		Pharmacy p = pa.getPharmacy();
		
		List<Dermatologist> dermatologists = dermatologistRepository.findAll();
		Set<Dermatologist> result = new HashSet<Dermatologist>();
		for(Dermatologist d : dermatologists) {
			for(WorkingTime wt : d.getWorkingTimes()) {
				if(wt.getPharmacy().equals(p)) {
					result.add(d);
				}
			}
		}
		
		return result;
		
	}

	
		
		/*for(WorkingTime workingTime : dermatologist.getWorkingTimes()) {
			if(!workingTime.getPharmacy().equals(p)) {
				for(WorkingDay workD : workingTime.getWorkingDays()) {
					if(workD.getDay().toString().equals(wd)) {
						if(workD.getStartTime().isAfter(LocalTime.parse(workingDay.getStartTime())) 
							&& workD.getStartTime().isAfter(LocalTime.parse(workingDay.getEndTime()))) {
							isOk = true;
						} else if(workD.getStartTime().isBefore(LocalTime.parse(workingDay.getStartTime())) 
								&& workD.getStartTime().isBefore(LocalTime.parse(workingDay.getEndTime()))) {
							isOk = true;
						} else {
							isOk = false;
						}
					} 
					if(!isOk) {
						return false;
					}
				}
			}
		}*/
		
	
	@Override
	public Set<WorkingDay> getWorkingDayForDermatolog(String id, String email) {
		Dermatologist dermatologist = this.dermatologistRepository.findById(Long.parseLong(id)).get();

		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		for(WorkingTime t : dermatologist.getWorkingTimes()) {
			System.out.println(t.getPharmacy());
			if(t.getPharmacy().equals(p)) {
				return t.getWorkingDays();
			}
		}
		return null;
		
		
	}

	@Override
	public Boolean deleteDermatologistFromPharmacy(String id, String email) {
		Dermatologist dermatologist = this.dermatologistRepository.findById(Long.parseLong(id)).get();

		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		List<DermatologistAppointment> appoitments = this.dermatologistAppoitmentRepository.findAll();
		
		for(DermatologistAppointment da : appoitments) {
			if(da.getDermatologist().equals(dermatologist)) {
				for(WorkingTime wt : dermatologist.getWorkingTimes()) {
					if(wt.getPharmacy().equals(p)) {
						if(da.getStartDateTime().isAfter(LocalDateTime.now())) {
							return false;
						}
					}
				}
			}
		}
			
		for(WorkingTime t : dermatologist.getWorkingTimes()) {
			if(t.getPharmacy().equals(p)) {
				dermatologist.getWorkingTimes().remove(t);
			}
		}
	
		this.dermatologistRepository.save(dermatologist);
		return true;
			
	}

	@Override
	public Set<Pharmacist> getPharmacistssByPharmacyAdmin(String email) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		
		Pharmacy p = pa.getPharmacy();
		
		List<Pharmacist> pharmacists = this.pharmacistRepository.findAll();
		Set<Pharmacist> result = new HashSet<Pharmacist>();
		for(Pharmacist d : pharmacists) {
			if(d.getWorkingTimes() != null) {
			if( d.getWorkingTimes().getPharmacy().equals(p)) {
				result.add(d);
			}	
			}
		}	
		return result;
	}

	/*@Override
	public void addWorkingTimeForPharmacist(String dermatologistId, String email, String wd, WorkingTimeIntervalDTO workingDay) {
		Pharmacist pharmacist = this.pharmacistRepository.findById(Long.parseLong(dermatologistId)).get();

		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		WorkingDay workingD = new WorkingDay();
		if(wd.equals("MONDAY")) {
			workingD.setDay(DayOfWeek.MONDAY);
		} else if (wd.equals("TUESDAY")) {
			workingD.setDay(DayOfWeek.TUESDAY);
		} else if (wd.equals("WEDNESDAY")) {
			workingD.setDay(DayOfWeek.WEDNESDAY);
		} else if (wd.equals("THURSDAY")) {
			workingD.setDay(DayOfWeek.THURSDAY);
		} else if (wd.equals("FRIDAY")) {
			workingD.setDay(DayOfWeek.FRIDAY);
		} else if (wd.equals("SATURDAY")) {
			workingD.setDay(DayOfWeek.SATURDAY);
		} else {
			workingD.setDay(DayOfWeek.SUNDAY);
		}
		
		workingD.setStartTime(LocalTime.parse(workingDay.getStartTime()));
		workingD.setEndTime(LocalTime.parse(workingDay.getEndTime()));
		
		//treba dodati uslov ako je prazno
		
		if(pharmacist.getWorkingTimes().getPharmacy().equals(p)) {
			pharmacist.getWorkingTimes().getWorkingDays().add(workingD);
		}
		
		this.pharmacistRepository.save(pharmacist);
		//treba dodati uslov ako je prazno
		
	}*/

	@Override
	public Set<WorkingDay> getWorkingDayForPharmacist(String id, String email) {
		Pharmacist pharmacist = this.pharmacistRepository.findById(Long.parseLong(id)).get();

		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		System.out.println(p);
		
		WorkingTime pharacistWorkingTime = pharmacist.getWorkingTimes();
		if(pharacistWorkingTime.getPharmacy().equals(p)) {
			return pharacistWorkingTime.getWorkingDays();
		}
		return null;
		
	}

	@Override
	public Boolean deletePharmacistFromPharmacy(String id, String email) {
		Pharmacist pharmacist = this.pharmacistRepository.findById(Long.parseLong(id)).get();

		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		

		List<PharmacistCounseling> consulting = this.pharmacistConselingRepository.findAll();
		
		for(PharmacistCounseling da : consulting) {
			if(da.getPharmacist().equals(pharmacist)) {
					if(pharmacist.getWorkingTimes().getPharmacy().equals(p)) {
						if(da.getStartDateTime().isAfter(LocalDateTime.now())) {
							return false;
						}
					}
			}
		}
		
		
		
		WorkingTime pharacistWorkingTime = pharmacist.getWorkingTimes();
		if(pharacistWorkingTime.getPharmacy().equals(p)) {
			pharmacist.setWorkingTimes(null);
		}
		
		this.pharmacistRepository.save(pharmacist);
		return true;

	}

	@Override

	public Set<Dermatologist> getAllDermatologistExpectAlreadyExisted(String email) {
		Set<Dermatologist> alreadyExisted = getDermatologistsByPharmacyAdmin(email);
		List<Dermatologist> all = dermatologistRepository.findAll();
		Set<Dermatologist> result = new HashSet<Dermatologist>();
			
		for(Dermatologist d : all) {	
				if(!alreadyExisted.contains(d)) {
					result.add(d);
				}
			}
		return result;
	}
	
	@Override
	public Set<Pharmacist> getAllPharmacistsExpectAlreadyExisted(String email) {
		Set<Pharmacist> alreadyExisted = getPharmacistssByPharmacyAdmin(email);
		List<Pharmacist> all = pharmacistRepository.findAll();
		Set<Pharmacist> result = new HashSet<Pharmacist>();
			
		for(Pharmacist p : all) {	
				if(!alreadyExisted.contains(p)) {
					result.add(p);
				}
			}
		return result;
	}


	@Override
	public Boolean addDermatologistInPharmacy(String email, NewDermatologistDTO newDermatologist) {
		Dermatologist dermatologist = this.dermatologistRepository.findById(Long.parseLong(newDermatologist.getDermatologistId())).get();
		if(newDermatologist.getWorkingTimes().size() == 0) {
			return false;
		}
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy(); 
		//da li se radno vrijeme poklapa sa drugim apotekama
		for(WorkingTime workingtime : dermatologist.getWorkingTimes()) {
			if(!workingtime.getPharmacy().equals(p)) {
				for(WorkingDay workD : workingtime.getWorkingDays()) {
					for(WorkingDayDTO wd : newDermatologist.getWorkingTimes()) {
						if(workD.getDay().toString().equals(wd.getDay())) {
							if(LocalTime.parse(wd.getStartTime()).isAfter(workD.getStartTime()) &&
									LocalTime.parse(wd.getStartTime()).isBefore(workD.getEndTime())) {
								return false;
							}
							if(LocalTime.parse(wd.getEndTime()).isAfter(workD.getStartTime()) &&
									LocalTime.parse(wd.getEndTime()).isBefore(workD.getEndTime())) {
								return false;
							}
							if(LocalTime.parse(wd.getStartTime()).isBefore(workD.getStartTime()) &&
									LocalTime.parse(wd.getEndTime()).isAfter(workD.getEndTime())) {
								return false;
							}
							
						}
				}
				}
			}
		}
		
		WorkingTime wt = new WorkingTime();
		wt.setPharmacy(p);
		
		for(WorkingDayDTO wd : newDermatologist.getWorkingTimes()) {
			WorkingDay workingD = new WorkingDay();
			if(wd.getDay().equals("MONDAY")) {
				workingD.setDay(DayOfWeek.MONDAY);
			} else if (wd.getDay().equals("TUESDAY")) {
				workingD.setDay(DayOfWeek.TUESDAY);
			} else if (wd.getDay().equals("WEDNESDAY")) {
				workingD.setDay(DayOfWeek.WEDNESDAY);
			} else if (wd.getDay().equals("THURSDAY")) {
				workingD.setDay(DayOfWeek.THURSDAY);
			} else if (wd.getDay().equals("FRIDAY")) {
				workingD.setDay(DayOfWeek.FRIDAY);
			} else if (wd.getDay().equals("SATURDAY")) {
				workingD.setDay(DayOfWeek.SATURDAY);
			} else {
				workingD.setDay(DayOfWeek.SUNDAY);
			}
			
			workingD.setStartTime(LocalTime.parse(wd.getStartTime()));
			workingD.setEndTime(LocalTime.parse(wd.getEndTime()));
			
			wt.getWorkingDays().add(workingD);
		}
		dermatologist.getWorkingTimes().add(wt);
		
		this.dermatologistRepository.save(dermatologist);
		return true;

	}
	
	
	@Override
	public Boolean editDermatologistInPharmacy(String email, NewDermatologistDTO newDermatologist) {
		Dermatologist dermatologist = this.dermatologistRepository.findById(Long.parseLong(newDermatologist.getDermatologistId())).get();

		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy(); 
		//da li se radno vrijeme poklapa sa drugim apotekama
	/*	Boolean isOk = false;
		for(WorkingTime workingTime : dermatologist.getWorkingTimes()) {
			if(!workingTime.getPharmacy().equals(p)) {
				for(WorkingDay workD : workingTime.getWorkingDays()) {
					for(WorkingDayDTO wd : newDermatologist.getWorkingTimes())
						if(workD.getDay().toString().equals(wd.getDay())) {
							if(workD.getStartTime().isAfter(LocalTime.parse(wd.getStartTime())) 
								&& workD.getStartTime().isAfter(LocalTime.parse(wd.getEndTime()))) {
								isOk = true;
							} else if(workD.getStartTime().isBefore(LocalTime.parse(wd.getStartTime())) 
									&& workD.getStartTime().isBefore(LocalTime.parse(wd.getEndTime()))) {
								isOk = true;
							} else {
								isOk = false;
							}
						} 
						if(!isOk) {
							return false;
						}
					}
				}
			}	*/	
		Set<WorkingDay> daysList = new HashSet<WorkingDay>();
		
		for(WorkingDayDTO wd : newDermatologist.getWorkingTimes()) {
			WorkingDay workingD = new WorkingDay();
			if(wd.getDay().equals("MONDAY")) {
				workingD.setDay(DayOfWeek.MONDAY);
			} else if (wd.getDay().equals("TUESDAY")) {
				workingD.setDay(DayOfWeek.TUESDAY);
			} else if (wd.getDay().equals("WEDNESDAY")) {
				workingD.setDay(DayOfWeek.WEDNESDAY);
			} else if (wd.getDay().equals("THURSDAY")) {
				workingD.setDay(DayOfWeek.THURSDAY);
			} else if (wd.getDay().equals("FRIDAY")) {
				workingD.setDay(DayOfWeek.FRIDAY);
			} else if (wd.getDay().equals("SATURDAY")) {
				workingD.setDay(DayOfWeek.SATURDAY);
			} else {
				workingD.setDay(DayOfWeek.SUNDAY);
			}
			
			workingD.setStartTime(LocalTime.parse(wd.getStartTime()));
			workingD.setEndTime(LocalTime.parse(wd.getEndTime()));
			
			daysList.add(workingD);
			
		}
		for(WorkingTime t : dermatologist.getWorkingTimes()) {
			if(t.getPharmacy().equals(p)) {
				t.setWorkingDays(daysList);
			}
		}
		
		this.dermatologistRepository.save(dermatologist);
		return true;

	}
	
	@Override
	public boolean addPharmacistInPharmacy(String email, NewPharmacistDTO newpharmacist) {
		if(newpharmacist.getWorkingTimes().size() == 0) {
			return false;
		}
		User user = new User();
		user.setFirstName(newpharmacist.getFirstName());
		user.setLastName(newpharmacist.getLastName());
		user.setEmail(newpharmacist.getEmail());
		user.setPassword(passwordEncoder.encode(newpharmacist.getPassword()));
		user.setUsername(newpharmacist.getEmail());
		user.setLogged(false);
		user.setEnabled(true);
		List<Authority> auth = authService.findByname("ROLE_PHARMACIST");
		user.setAuthorities(auth);
		Pharmacist pharmacist = new Pharmacist();
		pharmacist.setUser(user);
		

		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy();
		
		WorkingTime wt = new WorkingTime();
		wt.setPharmacy(p);
		for(WorkingDayDTO wd : newpharmacist.getWorkingTimes()) {
			WorkingDay workingD = new WorkingDay();
			if(wd.getDay().equals("MONDAY")) {
				workingD.setDay(DayOfWeek.MONDAY);
			} else if (wd.getDay().equals("TUESDAY")) {
				workingD.setDay(DayOfWeek.TUESDAY);
			} else if (wd.getDay().equals("WEDNESDAY")) {
				workingD.setDay(DayOfWeek.WEDNESDAY);
			} else if (wd.getDay().equals("THURSDAY")) {
				workingD.setDay(DayOfWeek.THURSDAY);
			} else if (wd.getDay().equals("FRIDAY")) {
				workingD.setDay(DayOfWeek.FRIDAY);
			} else if (wd.getDay().equals("SATURDAY")) {
				workingD.setDay(DayOfWeek.SATURDAY);
			} else {
				workingD.setDay(DayOfWeek.SUNDAY);
			}
			
			workingD.setStartTime(LocalTime.parse(wd.getStartTime()));
			workingD.setEndTime(LocalTime.parse(wd.getEndTime()));
			
			wt.getWorkingDays().add(workingD);
		}
		pharmacist.setWorkingTimes(wt);
		this.pharmacistRepository.save(pharmacist);
		return true;
		
	}
	@Override
	public Collection<Pharmacy> searchPharmacy(String p) {
			   ArrayList<Pharmacy> pharmacies = new ArrayList<>();
			   for(Pharmacy pharamacy : pharmacyRepository.findAll()){
				   if(pharamacy.getName().toLowerCase().contains(p.toLowerCase())) {
					   pharmacies.add(pharamacy);
				   }
				   else if(pharamacy.getAddress().getCity().toLowerCase().contains(p.toLowerCase())){
					   pharmacies.add(pharamacy);
				   }
				   else if(pharamacy.getAddress().getStreet().toLowerCase().contains(p.toLowerCase())){
					   pharmacies.add(pharamacy);
				   }
			   }
		        return pharmacies;
		}
	@Override
	public double getAvrageGrade(Pharmacy pharmacy) {
		double avrage_grade=0;
		int pom=0;
		for(Rating ratings : pharmacy.getRatings()) {
			pom++;
			avrage_grade+=ratings.getRating();
		}
		return avrage_grade/pom;
  }
	@Override
	public Collection<PharmacyWithMedicationView> getPharamciesWithMedication(Long id) {
		ArrayList<PharmacyWithMedicationView> pharmacies = new ArrayList <PharmacyWithMedicationView>();

		for (Pharmacy pharmacy : pharmacyRepository.findAll()) {
			for (MedicineWithQuantity medi : pharmacy.getMedicineWithQuantity()) {
				if (medi.getMedicine().getId()==id) {
					if(medi.getQuantity()>0) {
						//nasla aktuelnu price listu u apoteci
						PriceList pl =this.priceListService.findPriceListByPharmacy(pharmacy);
						PharmacyWithMedicationView ph = new PharmacyWithMedicationView();
						if(pl != null) {
							for (MedicinePrice medicinePrice : pl.getMedicinePriceList()) {
								if (medicinePrice.getMedicine().getId().equals(medi.getMedicine().getId())) {
									System.out.println(medicinePrice.getPrice());
										double	pricetotal = medicinePrice.getPrice();
										ph.setMedicinePrice(pricetotal);
									break;
								}
							}
						}
						ph.setId(pharmacy.getId());
						ph.setPharmacyName(pharmacy.getName());
						ph.setCity(pharmacy.getAddress().getCity());
						ph.setStreet(pharmacy.getAddress().getStreet());
						double grade=getAvrageGrade(pharmacy);
						System.out.println(grade);
						if(Double.isNaN(grade)) {
							ph.setAvrageGrade(0);
						}else
							ph.setAvrageGrade(getAvrageGrade(pharmacy));
					//	PharmacyWithMedicationView ph = new
						//	PharmacyWithMedicationView(pharmacy.getId(),pharmacy.getName(),
					//			pharmacy.getAddress().getStreet(),pharmacy.getAddress().getCity(), pricetotal);
						pharmacies.add(ph);
					}
				}
			}
		}
		return pharmacies;
	}

	@Override
	public Collection<MedicineReservationView> getReservationsByPatientsEmail(String email) {
		ArrayList<MedicineReservationView> reservations = new ArrayList<MedicineReservationView>();
		for(Pharmacy ph:findAll()) {
			for(MedicineReservation mR:ph.getMedicineReservations()) {
				if(mR.getPatient().getUser().getEmail().equals(email)) {
					if(mR.getStatus().equals(MedicineReservationStatus.RESERVED)) {
					MedicineReservationView myRes=new MedicineReservationView(mR.getId(),mR.getNumberOfReservation(),mR.getMedicineWithQuantity().getMedicine().getName(),ph.getName(),ph.getAddress().getCity(),ph.getAddress().getStreet(),mR.getDueToTime(),mR.getDueTo(),mR.getMedicineWithQuantity().getQuantity());
				 	LocalDateTime dt = LocalDateTime.of(myRes.getDueTo(), myRes.getDueToTime());
			       	if(LocalDateTime.now().isBefore(dt.minus(Period.ofDays(1)))) {
			       		myRes.setIsReservationExpired(false);
			       	}
			       	else {
			       		myRes.setIsReservationExpired(true);
			       	}
					reservations.add(myRes);
					}
				}
			}
		}
		return reservations;
  }
	public Set<HolidayRequest> getHolidayRequestsByPharmacy(long id,String email) {
		Dermatologist dermatologist = this.dermatologistRepository.findById(id).get();

		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy(); 
		
		Set<HolidayRequest> holidayRequests = new HashSet<HolidayRequest>();
		for(HolidayRequest hr : dermatologist.getHolidayRequests()) {
			if(hr.getPharmacy().equals(p)) {
				holidayRequests.add(hr);
			}
		}
		return holidayRequests;
	}


	public Set<HolidayRequest> getHolidayRequestsForDerm(long id) {
		Dermatologist dermatologist = this.dermatologistRepository.findById(id).get();


		Set<HolidayRequest> holidayRequests = new HashSet<HolidayRequest>();
		for(HolidayRequest hr : dermatologist.getHolidayRequests()) {
				holidayRequests.add(hr);

		}
		return holidayRequests;
	}
	@Override
	public Boolean acceptHolidayRequest(long id,long dermatologistId) {
		Dermatologist dermatologist = this.dermatologistRepository.findById(dermatologistId).get();
		HolidayRequest holidayRequest = this.holidayRequestRepository.findById(id).get();
		List<DermatologistAppointment> dermatologistAppointments = this.dermatologistAppoitmentRepository.findApByDermatologist(dermatologistId);
		
		for(HolidayRequest hr : dermatologist.getHolidayRequests()) {
			if(hr.equals(holidayRequest)) {
				for(DermatologistAppointment da : dermatologistAppointments) {
			
					if(!hr.getStartDate().isAfter(da.getStartDateTime().toLocalDate()) &&
							!hr.getEndDate().isBefore(da.getStartDateTime().toLocalDate())) {
						return false;
					}
				}
				hr.setStatus(HolidayRequestStatus.ACCEPT);
			}
		}
		try {
			this.emailService.sendEmail(dermatologist.getUser().getEmail(), "Vaction request", "Request was accepted");
		} catch (MailException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dermatologistRepository.save(dermatologist);
		return true;
		
	}


	@Override
	public void declineHolidayRequest(long id,long dermatologistId, String reason) {
		Dermatologist dermatologist = this.dermatologistRepository.findById(dermatologistId).get();
		HolidayRequest holidayRequest = this.holidayRequestRepository.findById(id).get();
		for(HolidayRequest hr : dermatologist.getHolidayRequests()) {
			if(hr.equals(holidayRequest)) {
				hr.setStatus(HolidayRequestStatus.DENIED);
			}
		}
		try {
			this.emailService.sendEmail(dermatologist.getUser().getEmail(), "Vaction request", "Request was declined\n" + reason);
		} catch (MailException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dermatologistRepository.save(dermatologist);
		
	}

	@Override
	public Pharmacy save(Pharmacy pharmacy) {
		Pharmacy newPharm = this.pharmacyRepository.save(pharmacy);
		return newPharm;
  }
  @Override
	public WorkingTime getDermatologistWorkingTimes(long id, String email) {
		Dermatologist dermatologist = this.dermatologistRepository.findById(id).get();

		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy(); 
		
		for(WorkingTime wt : dermatologist.getWorkingTimes()) {
			if(wt.getPharmacy().equals(p)) {
				return wt;
			}
		}
		return null;
		
		
	}
	@Override
	public Pharmacy getPharmacyByDermatologistAndStartDate(Dermatologist d, LocalDateTime start) {
		LocalDate pom = start.toLocalDate();
		LocalTime pomTime = start.toLocalTime();
		Pharmacy pharm;
		if(d==null) {
			return null;
		}
		if(d.getWorkingTimes()==null) {
			return null;
		}
		for(WorkingTime t:d.getWorkingTimes()) {
			for(WorkingDay day:t.getWorkingDays()) {
				System.out.println("Working time"+t);
				System.out.println("Working day" + day);
				if(day.getStartTime().equals(pomTime) && day.getDay().equals(pom.getDayOfWeek())) {
					System.out.println("Usao u if za apoteku");
					pharm = t.getPharmacy();
					return pharm;
				}
				else if(pom.getDayOfWeek().equals(day.getDay()) ) {
					System.out.println("Usao u else if");
					System.out.println(pomTime);
					System.out.println(day.getStartTime());
					System.out.println(day.getEndTime());
					if(pomTime.isAfter(day.getStartTime()) && pomTime.isBefore(day.getEndTime()) ) {
					pharm = t.getPharmacy();
					return pharm;}
				}
			}
		}
		return null;

	}

	@Override
	public Set<HolidayRequest> getHolidayRequestsByPharmacyP(long id, String email) {
		Pharmacist pharmacist = this.pharmacistRepository.findById(id).get();

		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy(); 
		
		Set<HolidayRequest> holidayRequests = new HashSet<HolidayRequest>();
		for(HolidayRequest hr : pharmacist.getHolidayRequests()) {
			if(hr.getPharmacy().equals(p)) {
				holidayRequests.add(hr);
			}
		}
		
		
		return holidayRequests;
	}
	
	
	@Override
	public Boolean acceptHolidayRequestP(long id, long pharmacistId) {
		Pharmacist pharmacist = this.pharmacistRepository.findById(pharmacistId).get();
		HolidayRequest holidayRequest = this.holidayRequestRepository.findById(id).get();
		List<PharmacistCounseling> pharmacistConselings = this.pharmacistConselingRepository.findCoByPharmacist(pharmacistId);
		for(HolidayRequest hr : pharmacist.getHolidayRequests()) {
			if(hr.equals(holidayRequest)) {
				for(PharmacistCounseling pc : pharmacistConselings) {
					if(!hr.getStartDate().isAfter(pc.getStartDateTime().toLocalDate()) &&
							!hr.getEndDate().isBefore(pc.getStartDateTime().toLocalDate())) {
						return false;
					}
				}
				hr.setStatus(HolidayRequestStatus.ACCEPT);
			}
		}
		try {
			this.emailService.sendEmail(pharmacist.getUser().getEmail(), "Vaction request", "Request was accepted");
		} catch (MailException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.pharmacistRepository.save(pharmacist);
		return true;
		
	}

	@Override
	public void declineHolidayRequestP(long id, long pharmacistId, String reason) {
		Pharmacist pharmacist = this.pharmacistRepository.findById(pharmacistId).get();
		HolidayRequest holidayRequest = this.holidayRequestRepository.findById(id).get();
		for(HolidayRequest hr : pharmacist.getHolidayRequests()) {
			if(hr.equals(holidayRequest)) {
				hr.setStatus(HolidayRequestStatus.DENIED);
			}
		}
		try {
			this.emailService.sendEmail(pharmacist.getUser().getEmail(), "Vaction request", "Request was declined\n" + reason);
		} catch (MailException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.pharmacistRepository.save(pharmacist);
	}
	
	

	@Override
	public List<RequestForMedicineAvailability> findRequestsByPharmacy(String email) {

		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(userService.findByEmail(email));
		Pharmacy p = pa.getPharmacy(); 
		
		long pharmacyId = p.getId();
		
		return this.requestForMedicineAvailabilityRepository.findRequestsByPharmacy(pharmacyId);
	}

	@Override
	public List<RatingView> getAllPharmaciesPatientCanEvaluate(long patient) {
		List<RatingView> result=new ArrayList<RatingView>();
		List<Pharmacy> pom=new ArrayList<Pharmacy>();
		for(Pharmacy pharmacy : this.pharmacyRepository.findAll()) {
			//PROVERA DA LI JE REZERVISAO I PREUZEO LEK U APOTECI
			for(MedicineReservation mr : pharmacy.getMedicineReservations()) {
				if(mr.getPatient()==null) {
					continue;
				}
				if(mr.getPatient().getId() == patient) {
					if(mr.getStatus().equals(MedicineReservationStatus.TAKEN) && !pom.contains(pharmacy)) {
	
						pom.add(pharmacy);
						}
					}
			}
			//PROVERA DA LI JE IMAO PREGLED KOD DERMATOLOGA U APOTECI
			for(DermatologistAppointment da : this.dermatologistAppoitmentRepository.findAll()) {
				if(da.getPatient()==null) {
					continue;
				}
				if(da.getPatient().getId() == patient) {
					if(da.getStartDateTime().isBefore( LocalDateTime.now())) {
						if(da.getPharmacy()==null) {
							continue;
							}
						if(da.getPharmacy().equals(pharmacy) && !pom.contains(pharmacy)) {
							pom.add(pharmacy);
							}
						}
					}
		}
		//PROVERA DA LI JE IMAO KONSULATACIJE KOD APOTEKARA U APOTECI
		for(PharmacistCounseling pc : this.pharmacistConselingRepository.findAll()) {
			Pharmacy ph=new Pharmacy();		
			if(pc.getPatient()==null) {
				continue;
			}
			if(pc.getPatient().getId()==patient) {
				if(pc.getStartDateTime().isBefore(LocalDateTime.now()) && !pom.contains(pc.getPharmacist().getWorkingTimes().getPharmacy())) {
					ph=pc.getPharmacist().getWorkingTimes().getPharmacy();
						pom.add(ph);
				}
			}
			
		}
		//PROVERA DA LI MU JE PREPISAN LEK PREKO ERECEPTA
		for(EPrescription ep:ePrescriptionService.findAll()) {
			if(ep.getPharmacy().equals(pharmacy)) {
				if(ep.getPatient()==null) {
					continue;
				}
				if(ep.getPatient().getId()==patient) {
					if(ep.getPharmacy().equals(pharmacy) && !pom.contains(pharmacy))
							pom.add(pharmacy);
					}
			}
		}
}
		for(Pharmacy p : pom) {
			RatingView rdw=new RatingView(p.getId(),p.getName(),
					"");
						
		
			for(Rating ra :p.getRatings()){
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
		Pharmacy ph=pharmacyRepository.findPharmacyById(id);	
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
		this.pharmacyRepository.save(ph);
		
	}
}


