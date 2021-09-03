package rs.ac.uns.ftn.informatika.spring.security.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import rs.ac.uns.ftn.informatika.spring.security.model.*;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicinePriceRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyAdminRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.*;
import rs.ac.uns.ftn.informatika.spring.security.view.*;

@RestController
@RequestMapping(value = "/pharmacy", produces = MediaType.APPLICATION_JSON_VALUE)
public class PharmacyController {

	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	@Autowired
	private PharmacyService pharmacyService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private UserService userService;
	@Autowired
	private MedicineService medicineService;
	@Autowired
	private PriceListService priceListService;
	@Autowired
	private StatisticService statisticService;

	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private PharmacistCounselingService pharmacistCounselingService;
	@Autowired
	private MedicinePriceRepository medicinePriceRepository;
	
	@PostMapping("/getPharmacyByAdmin")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Optional<Pharmacy> getBy(@RequestBody User user) {
		PharmacyAdmin pa = pharmacyAdminService.findPharmacyAdminByUser(user);
		System.out.println(pa);
		return pharmacyService.findById(pa.getPharmacy().getId());
		
	}
	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ROLE_PATIENT') || hasRole('ADMIN_PHARMACY') || hasRole('ADMIN_SYSTEM')")
	public List<Pharmacy> getAll() {
		return this.pharmacyService.findAll();
		
	}
	@GetMapping("/getPharmacyById/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public Optional<Pharmacy> getPharamcyById(@PathVariable(name="id") Long id) {
		return this.pharmacyService.findById(id);
		
	}
	
	@PostMapping("/editPharmacy")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> editPharmacy(@RequestBody EditPharmacyView pharmacy) {
		
		pharmacyService.editPharmacy(pharmacy);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllMedicinesWithQuantity/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<MedicineWithQuantity> getMedicines(@PathVariable(name="email") String email) {
		return this.medicineService.getMedicinesByPharmacy(email);
		
	}
	
	@GetMapping("/getAllDermatologist/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<Dermatologist> getDermatologsts(@PathVariable(name="email") String email) {
		return this.pharmacyService.getDermatologistsByPharmacyAdmin(email);
		
	}
	
	@GetMapping("/getAllWorkingTimes/{id}/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<WorkingDay> getAllWorkingtimes(@PathVariable(name="id") String id,
			@PathVariable(name="email") String email) {
		return this.pharmacyService.getWorkingDayForDermatolog(id, email);
		
	}
	
	@GetMapping("/deleteDermatologist/{id}/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> deleteDermatologist(@PathVariable(name="id") String id,
			@PathVariable(name="email") String email) {
		if(this.pharmacyService.deleteDermatologistFromPharmacy(id, email)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		
		
	}
	
	@GetMapping("/getAllPharmacists/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<Pharmacist> getPharmacists(@PathVariable(name="email") String email) {
		return this.pharmacyService.getPharmacistssByPharmacyAdmin(email);
		
	}
	
	@GetMapping("/getAllWorkingTimesPharmacist/{id}/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<WorkingDay> getAllWorkingtimesPharmacist(@PathVariable(name="id") String id,
			@PathVariable(name="email") String email) {
		return this.pharmacyService.getWorkingDayForPharmacist(id, email);
		
	}
	
	@GetMapping("/deletePharmacist/{id}/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> deletePharmacist(@PathVariable(name="id") String id,
			@PathVariable(name="email") String email) {
		if(this.pharmacyService.deletePharmacistFromPharmacy(id, email)) {
		return new ResponseEntity<>(HttpStatus.OK);
	} else {
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
		
	}
	
	@GetMapping("/getAllMedicineExceptAlreadyExisted/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<Medicine> getAllMedicineExceptAlreadyExisted(@PathVariable(name="email") String email) {
		return this.medicineService.getAllMedicinesExceptExisted(email);
		
	}
	
	@GetMapping("/getMedicinePricesExceptAlreadyExisted/{email}/{ids}/{priceListId}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<Medicine> getMedicinePricesExceptAlreadyExisted(@PathVariable(name="email") String email,
			@PathVariable(name="ids") String ids, @PathVariable(name="priceListId") String priceListId) {
		System.out.println(ids);
		List<Long> existed = new ArrayList<Long>();
		String[] existedString = ids.split(",");
		for(String s : existedString) {
			existed.add(Long.parseLong(s));
		}
		return this.medicineService.getAllMedicinePricesExpectedExsitedInPriceList(email,existed,priceListId);
		
	}
	
	@PostMapping("/addMedicineWithQuantityInPharmacy/{email}/{medicineName}/{quantity}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> getAllMedicineExceptAlreadyExisted(@PathVariable(name="email") String email,
			@PathVariable(name="medicineName") String medicineName,@PathVariable(name="quantity") String quantity) {
		int q = Integer.parseInt(quantity);
		this.medicineService.addMedicineWithQuatityInPharmacy(email, medicineName, q);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/editMedicineWithQuantityInPharmacy/{email}/{medicineId}/{quantity}/{version}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> editMedicineWithQuantityInPharmacy(@PathVariable(name="email") String email,
			@PathVariable(name="medicineId") String medicineId,@PathVariable(name="quantity") String quantity,
			@PathVariable(name="version") String version) {
		int q = Integer.parseInt(quantity);
		long id = Long.parseLong(medicineId);
		Long v = Long.parseLong(version);
		try {
			this.medicineService.editMedicineWithQuatityInPharmacy(email, id, q,v);
		} catch (ObjectOptimisticLockingFailureException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/deleteMedicineFromPharmacy/{id}/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> deleteMedicineFromPharmacy(@PathVariable(name="id") String id,
			@PathVariable(name="email") String email) {
		Long medicineId = Long.parseLong(id);
		if(this.medicineService.deleteMedicineFromPharmacy(medicineId, email)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
	

	@GetMapping("/getAllDermatologistsExpectAlreadyExisted/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<Dermatologist> getDermatologists(@PathVariable String email) {
		return this.pharmacyService.getAllDermatologistExpectAlreadyExisted(email);
	}
	

	@GetMapping("/getAllPharmacistsExpectAlreadyExisted/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<Pharmacist> getPharmacist(@PathVariable String email) {
		return this.pharmacyService.getAllPharmacistsExpectAlreadyExisted(email);
	}
	
	
	@PostMapping("/addDermatologistInPharmacy/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> addDermatologistInPharmacy(@PathVariable(name="email") String email,@RequestBody NewDermatologistDTO newDermatologist) {
		System.out.println(newDermatologist);
		if(this.pharmacyService.addDermatologistInPharmacy(email, newDermatologist)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		
		
		
	}
	
	@PostMapping("/addPharmacistInPharmacy/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> addPharmacistInPharmacy(@PathVariable(name="email") String email,@RequestBody NewPharmacistDTO newPharmacist) {
		if(this.pharmacyService.addPharmacistInPharmacy(email, newPharmacist)) {
		
		return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	/*@PostMapping("/addWorkingDayPharmacist/{id}/{email}/{workingDay}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> addWorkingDayPharmacist(@PathVariable(name="id") String id,@PathVariable(name="email") String email,
			@PathVariable(name="workingDay") String workingDay,@RequestBody WorkingTimeIntervalDTO wd) {
		System.out.println(wd);
		this.pharmacyService.addWorkingTimeForPharmacist(id, email, workingDay, wd);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}*/
	
	
	@GetMapping("/getMedicinePriceList/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public PriceList getMedicinePriceList(@PathVariable(name="email") String email) {
		return this.priceListService.findPriceListByPharmacy(email);
	}
	

	@GetMapping("/getListMedicines/{id}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<MedicinePrice> getMedicinePrice(@PathVariable(name="id") String id) {
		return this.priceListService.findMedicinePricesByPriceListId(Long.parseLong(id));
	}
	
	
	@PostMapping("/addMedicinePriceToPriceList/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> add(@PathVariable(name="email") String email,
			@RequestBody PriceListDTO priceList) {
		
		try {
			LocalDate tryDate = LocalDate.parse(priceList.getDate());
		}catch (DateTimeParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(priceList.getMedicines().size() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		LocalDate date = LocalDate.parse(priceList.getDate());
		
		Set<MedicinePrice> medicinePrices = new HashSet<MedicinePrice>();
		
		
		for(MedicinePriceDTO m : priceList.getMedicines()) {
			MedicinePrice mp = new MedicinePrice();
			mp.setMedicine(this.medicineService.findById(Long.parseLong(m.getMedicineId())));
			mp.setPrice(Double.parseDouble(m.getPrice()));
			medicinePrices.add(mp);
			this.medicinePriceRepository.save(mp);
		}
		this.priceListService.createNewPriceList(email, medicinePrices, date);
			
			return new ResponseEntity<>(HttpStatus.OK);
	}
	


	@GetMapping(value = "/getPharamcyWithMedicine/{let}",produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<PharmacyWithMedicationView> getPharamcyWithMedicine(@PathVariable("let") Long let) {
		return pharmacyService.getPharamciesWithMedication(let);
	}
	
	

	@GetMapping("/getHolidayRequests/{id}/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<HolidayRequest> getHolidayRequests(@PathVariable(name="id") String id,@PathVariable(name="email") String email) {
		return this.pharmacyService.getHolidayRequestsByPharmacy(Long.parseLong(id),email);
	}

	@GetMapping("/getHolidayRequestsForDerm/{id}")
	@PreAuthorize("hasRole('ADMIN_SYSTEM')")
	public Set<HolidayRequest> getHolidayRequestsForDerm(@PathVariable(name="id") String id) {
		return this.pharmacyService.getHolidayRequestsForDerm(Long.parseLong(id));
	}
	
	@PostMapping("/acceptHolidayRequest/{id}/{dermatologistId}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY') || hasRole('ADMIN_SYSTEM')")
	public ResponseEntity<?> acceptHolidayRequest(@PathVariable(name="id") String id,
			@PathVariable(name="dermatologistId") String dermatologistId) {
		if(this.pharmacyService.acceptHolidayRequest(Long.parseLong(id), Long.parseLong(dermatologistId))) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/declineHolidayRequest/{id}/{dermatologistId}/{reason}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY') || hasRole('ADMIN_SYSTEM')")
	public ResponseEntity<?> declineHolidayRequest(@PathVariable(name="id") String id,
			@PathVariable(name="dermatologistId") String dermatologistId,
			@PathVariable(name="reason") String reason) {
		this.pharmacyService.declineHolidayRequest(Long.parseLong(id), Long.parseLong(dermatologistId),reason);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@GetMapping("/getHolidayRequestsP/{id}/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public Set<HolidayRequest> getHolidayRequestsP(@PathVariable(name="id") String id,@PathVariable(name="email") String email) {
		return this.pharmacyService.getHolidayRequestsByPharmacyP(Long.parseLong(id),email);
	}
	
	@PostMapping("/acceptHolidayRequestP/{id}/{pharmacistId}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> acceptHolidayRequestP(@PathVariable(name="id") String id,
			@PathVariable(name="pharmacistId") String pharmacistId) {
		if(this.pharmacyService.acceptHolidayRequestP(Long.parseLong(id), Long.parseLong(pharmacistId))) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/declineHolidayRequestP/{id}/{pharmacistId}/{reason}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public ResponseEntity<?> declineHolidayRequestP(@PathVariable(name="id") String id,
			@PathVariable(name="pharmacistId") String pharmacistId,
			@PathVariable(name="reason") String reason) {
		this.pharmacyService.declineHolidayRequestP(Long.parseLong(id),Long.parseLong(pharmacistId), reason);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/getDermatologistWorkingTimes/{id}/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public WorkingTime getDermatologistWorkingTimes(@PathVariable(name="id") String id,@PathVariable(name="email") String email) {
		return this.pharmacyService.getDermatologistWorkingTimes(Long.parseLong(id), email);
	}
	
	@GetMapping("/getDermatologistAppointmentByYear/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public List<StatisticDTO> getDermatologistAppointmentByYear(@PathVariable(name="email") String email) {
		return this.statisticService.getDermatologistAppoitmentByYear(email);
	}
	
	@GetMapping("/getDermatologistAppointmentByMonth/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public List<StatisticDTO> getDermatologistAppointmentByMonth(@PathVariable(name="email") String email) {
		return this.statisticService.getDermatologistAppoitmentByMounth(email);
	}
	
	@GetMapping("/getDermatologistAppointmentByQuarter/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public List<StatisticDTO> getDermatologistAppointmentByQuarter(@PathVariable(name="email") String email) {
		return this.statisticService.getDermatologistAppoitmentByQuarter(email);
	}
	
	@GetMapping("/getPharmacistConselingByYear/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public List<StatisticDTO> getPharmacistConselingByYear(@PathVariable(name="email") String email) {
		return this.statisticService.getPharmacistConselingByYear(email);
	}
	
	@GetMapping("/getPharmacistConselingtByMonth/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public List<StatisticDTO> getPharmacistConselingByMonth(@PathVariable(name="email") String email) {
		return this.statisticService.getPharmacistConselingByMounth(email);
	}
	
	@GetMapping("/getPharmacistConselingByQuarter/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public List<StatisticDTO> getPharmacistConselingByQuarter(@PathVariable(name="email") String email) {
		return this.statisticService.getPharmacistConselingByQuarter(email);
	}
	
	@GetMapping("/getMedicineConsumptionByYear/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public List<StatisticDTO> getMedicineConsumptionByYear(@PathVariable(name="email") String email) {
		return this.statisticService.getMedicineConsumptionByYear(email);
	}
	
	@GetMapping("/getMedicineConsumptionByMonth/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public List<StatisticDTO> getMedicineConsumptionByMonth(@PathVariable(name="email") String email) {
		return this.statisticService.getMedicineConsumptionByMonth(email);
	}
	
	@GetMapping("/getMedicineConsumptionByQuarter/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public List<StatisticDTO> getMedicineConsumptionByQuarter(@PathVariable(name="email") String email) {
		return this.statisticService.getMedicineConsumptionQuarter(email);
	}
	

	@GetMapping("/getAppointmentIncomes/{email}/{from}/{to}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public List<StatisticDTO> getAppointmentIncomes(@PathVariable(name="email") String email,
			@PathVariable(name="from") String from,@PathVariable(name="to") String to) {
		
		return this.statisticService.getPharmacyIncome(email, LocalDate.parse(from), LocalDate.parse(to));
	}
	
	@GetMapping("/getCounselingIncomes/{email}/{from}/{to}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public List<StatisticDTO> getCounselingIncomes(@PathVariable(name="email") String email,
			@PathVariable(name="from") String from,@PathVariable(name="to") String to) {
		
		return this.statisticService.getPharmacyIncomeFromPharmacistCouseling(email, LocalDate.parse(from), LocalDate.parse(to));
	}
	
	@GetMapping("/getMedicineIncomes/{email}/{from}/{to}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public List<StatisticDTO> getMedicineIncome(@PathVariable(name="email") String email,
			@PathVariable(name="from") String from,@PathVariable(name="to") String to) {
		
		return this.statisticService.getPharmacyIncomeFromMedicineConsumption(email, LocalDate.parse(from), LocalDate.parse(to));
	}
	
	@GetMapping("/getPharamciesWithAvailablePharmacists/{term}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public List<PharmacyForCounselingView> getPharamciesWithAvailablePharmacists(@PathVariable(name="term") String term) {
		return this.pharmacistCounselingService.getPharamciesWithAvailablePharmacists(term);
	}
	
	@GetMapping("/getSelectedMedicine/{id}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public MedicineWithQuantity getSelectedMedicine(@PathVariable(name="id") String id) {
		Long ID = Long.parseLong(id);
		return this.medicineService.getMedicineById(ID);
	}
	
	@GetMapping("/getCounselingByPatienetId/{email}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public List<PatientsCounslingView> getCounselingByPatienetId(@PathVariable(name="email") String email) {
		User user=this.userService.findByEmail(email);
		Patient patient=this.patientService.findPatientByUser(user);
		return this.pharmacistCounselingService.getPatientsCounlings(patient.getId());
	}
	
	@PostMapping("/cancelCounselingAppointment/{pom}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public  ResponseEntity<?> cancelCounselingAppointment(@PathVariable(name="pom") Long pom) {
		if(pharmacistCounselingService.cancelAppointment(pom))
		{
			return new ResponseEntity<>(HttpStatus.OK);	
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getRequestForMedicineAvailability/{email}")
	@PreAuthorize("hasRole('ADMIN_PHARMACY')")
	public List<RequestForMedicineAvailability> getRequestForMedicineAvailability(@PathVariable(name="email") String email) {
		
		return this.pharmacyService.findRequestsByPharmacy(email);
	}
	@GetMapping("/getAllPharmaciesPatientCanEvaluate/{email}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public List<RatingView> getAllPharmaciesPatientCanEvaluate(@PathVariable String email) {
		User user = this.userService.findByUsername(email);
		Patient patient=this.patientService.findPatientByUser(user);
		return pharmacyService.getAllPharmaciesPatientCanEvaluate(patient.getId());
	}
	 @PostMapping("/changeRating/{rating}/{email}/{id}")
	 @PreAuthorize("hasRole('ROLE_PATIENT')")
	 public ResponseEntity<?> changeRating(@PathVariable int rating,@PathVariable String email,@PathVariable Long id){
		 User user = this.userService.findByUsername(email);
		 Patient patient=this.patientService.findPatientByUser(user);
		 this.pharmacyService.changeRating(rating,patient.getId(),id);
		 return new ResponseEntity<>(HttpStatus.OK);
	 }

	@PostMapping("/savePharmacyComplaint")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> savePharmacyComplaint(@RequestBody ComplaintView complaintView) {
		// pronadjem po kategoriji i posaljem dva podatka za cuvanje  i setovanje
		User user = this.userService.findByUsername(complaintView.getUserName());
		Patient patient=this.patientService.findPatientByUser(user);
// u ovom slucaju complaint on name saljem id pharmacy
		Pharmacy pharmacy=null;
		for(Pharmacy d:pharmacyService.findAll()) {
			if(d.getId().equals(Long.parseLong( complaintView.getComplainedOnName()))) {
				pharmacy= d;
			}
		}
		PharmacyComplaint pharmacyComplaint = new PharmacyComplaint();
		pharmacyComplaint.setPharmacy(pharmacy);
		pharmacyComplaint.setContent(complaintView.getContent());
		pharmacyComplaint.setAnswered(false);
		pharmacyComplaint.setPatient(patient);
		this.complaintService.savePharmacy(pharmacyComplaint);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

