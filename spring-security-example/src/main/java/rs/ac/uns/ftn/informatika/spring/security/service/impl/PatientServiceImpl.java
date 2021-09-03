package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.informatika.spring.security.model.*;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineWithQuantityRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PatientRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.UserRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.*;
import rs.ac.uns.ftn.informatika.spring.security.view.EPrescriptionBuyPharmacyView;
import rs.ac.uns.ftn.informatika.spring.security.view.EPrescriptionPharmacyView;
import rs.ac.uns.ftn.informatika.spring.security.view.PharmacyWithMedicationView;


@Service
public class PatientServiceImpl implements PatientService {
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private LoyaltyScaleService loyaltyScaleService;

	@Autowired
	private MedicineService medicineService;

	@Autowired
	private PharmacyService pharmacyService;

	@Autowired
	private EPrescriptionService ePrescriptionService;

	@Autowired
	private MedicineReservationService medicineReservationService;

	@Autowired
	private PriceListService priceListService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private MedicineWithQuantityRepository medicineWithQuantityRepository;
	
	@Override
	public List<Patient> findAll() {
		List<Patient> result = patientRepository.findAll();
		return result;
	}
	@Override
	public ArrayList<String> findPatientsAllergies(Long user_id) {
		return this.patientRepository.findPatientsAllergies(user_id);
	}
	@Override
	public Patient findPatientByUser(User user) {
		System.out.println(this.patientRepository.findByUser(user));
		return this.patientRepository.findByUser(user);
	}
	@Override
	public Patient savePatient(Patient patient) {
		Patient p = new Patient();
		p.setAllergies(patient.getAllergies());
		p.setCategory(patient.getCategory());
		p.setPoints(patient.getPoints());
		p.setUser(patient.getUser());
		p.setId(patient.getId());
		p.setPatientSubscriptions(patient.getPatientSubscriptions());
		p = this.patientRepository.save(p);
		return p;
	}

	public Patient findPatientById(Long id) {
		Patient p = patientRepository.findPatientById(id);
		return p;
	}
	public void giveOnePenalForPatient(Patient p) {
		System.out.println("Usao u penaleee");
		p.setPenal(p.getPenal()+1);		
		patientRepository.save(p);
	}
	@Override
	public int getPatientsDiscount(Patient patient) {	
		for(LoyaltyScale ls : loyaltyScaleService.findAll()) {
			if(ls.getCategory().equals(patient.getCategory())) {
				return ls.getDisccount();
			}
		}
		return 0;
	}

	@Override
	public List<EPrescriptionPharmacyView> findPharmacyForEPrescription(String codesAndCount) {
		String[] partings = codesAndCount.split(";");
		List<String> medicineCodes = new ArrayList<String>();
		List<String> medicineQuantity = new ArrayList<>();
	//	List<Medicine> medicines = new ArrayList<Medicine>();

		List<MedicineWithQuantity> medicineWithQuantities = new ArrayList<>();
		for(String p:partings){
			String[] medCodeAndCount = p.split(":");
			medicineCodes.add(medCodeAndCount[0]);
			medicineQuantity.add(medCodeAndCount[1]);
			medicineWithQuantities.add(new MedicineWithQuantity(medicineService.findByCode(medCodeAndCount[0]),Integer.parseInt( medCodeAndCount[1])));
		//	medicines.add(medicineService.findByCode(medCodeAndCount[0]));
		}

		List<EPrescriptionPharmacyView> foundedPharmacies = new ArrayList<>();
		for(Pharmacy pharmacy: pharmacyService.findAll()){
			if(checkIfPharmacyHaveAllMedicines(pharmacy,medicineWithQuantities)){
				double totalPrice = findTotalPrice(pharmacy,medicineWithQuantities);
				double pharmacyAverageRating = getPharmacyAverageRating(pharmacy);
				foundedPharmacies.add(new EPrescriptionPharmacyView(medicineCodes, medicineQuantity,pharmacy.getId(),totalPrice ,pharmacyAverageRating,pharmacy.getName(), pharmacy.getAddress().getCity() + " " + pharmacy.getAddress().getStreet()));
			}
		}

		return foundedPharmacies;
	}

	@Override
	@Transactional(readOnly = false)
	public void buyEPrescriptionInPharmacy(Patient patient, Pharmacy pharmacy, List<String> medicineCodes, List<String> medicineCodesQuantity) {
		EPrescription ePrescription = new EPrescription();
		ePrescription.setPatient(patient);
		ePrescription.setIssuedDate(LocalDate.now());
		List<Medicine> codes = new ArrayList<>();
		List<Integer> quantities = new ArrayList<>();
		for(String code:medicineCodes){
			codes.add(this.medicineService.findByCode(code));
		}

		for(String q: medicineCodesQuantity)
		{
			quantities.add(Integer.parseInt(q));
		}

		List<MedicineWithQuantity> medicineWithQuantities = new ArrayList<>();
		Set<MedicineWithQuantity> mqSet = new HashSet<>();
		for(int i=0;i < codes.size(); i++){
			MedicineWithQuantity mq = new MedicineWithQuantity(codes.get(i), quantities.get(i));
			medicineWithQuantities.add(mq);
			mqSet.add(mq);
		}

		ePrescription.setMedicines(mqSet);
		ePrescription.setPharmacy(pharmacy);
		this.ePrescriptionService.save(ePrescription);

		//update in pharmacy medicine quantity
		for(MedicineWithQuantity myMed :medicineWithQuantities) {
			MedicineWithQuantity m = this.medicineWithQuantityRepository.findMedWithQById(myMed.getId());
			updateMedicineQuantityInPharmacy(m, pharmacy);

			//TODO: check this againg milica
			updateUserPoints(m,patient );
			this.loyaltyScaleService.updateCathegoryOfPatient(patient);
		}
	}

	@Override
	public List<EPrescription> findAllEpresForUser(Patient patient) {
		List<EPrescription> allE = this.ePrescriptionService.findAll();
		List<EPrescription> newAll = new ArrayList<>();
		for(EPrescription e: allE){
			if(e.getPatient().equals(patient)){
				newAll.add(e);
			}
		}
		return newAll;
	}

	private void updateUserPoints(MedicineWithQuantity medicineWithQuantity, Patient patient) {
		patient.setPoints(patient.getPoints() + medicineWithQuantity.getMedicine().getBuyingPoints()*(medicineWithQuantity.getQuantity() ==0 ? 1 : medicineWithQuantity.getQuantity()));
		this.patientRepository.save(patient);
	}

	public void updateMedicineQuantityInPharmacy(MedicineWithQuantity myMed, Pharmacy pharmacy)  {

		//prolazim kroz listu u apoteci
				for(MedicineWithQuantity medicineWithQuantity: pharmacy.getMedicineWithQuantity()){
			// proveravam da li se lekovi u apoteci poklapaju sa mojim svim lekovima
					if(myMed.getMedicine().equals(medicineWithQuantity.getMedicine())){
						medicineWithQuantity.setQuantity(medicineWithQuantity.getQuantity() - myMed.getQuantity());
						//save in medicine wwith quantity
						this.pharmacyService.save(pharmacy);
						return;
					}
				}

	}
	private double getPharmacyAverageRating(Pharmacy pharmacy) {
		double averageRating= 0.0;
		double totalCountOfRatings = 0.0;
		Set<Rating> ratings = pharmacy.getRatings();
		if(!ratings.isEmpty() ) {
			for(Rating r : ratings) {
				totalCountOfRatings += r.getRating();
			}
			averageRating = totalCountOfRatings/ratings.size();
		}
		return averageRating;
	}

	private double findTotalPrice(Pharmacy pharmacy, List<MedicineWithQuantity> medicineWithQuantities) {
		double totalPrice = 0.0;
		for(MedicineWithQuantity m : medicineWithQuantities) {

			//totalPrice += pharmacy.getCurrentPrice(m);
			PriceList pl =this.priceListService.findPriceListByPharmacy(pharmacy);
			if(pl != null) {
				for (MedicinePrice medicinePrice : pl.getMedicinePriceList()) {
					if (medicinePrice.getMedicine().getId().equals(m.getMedicine().getId())) {
						System.out.println(medicinePrice.getPrice());
						if(medicinePrice.getPrice() !=0) {
							totalPrice += medicinePrice.getPrice() * m.getQuantity();
							break;
						}
					}
				}
			}
		}
		return totalPrice;
	}
	private boolean checkIfPharmacyHaveAllMedicines(Pharmacy pharmacy, List<MedicineWithQuantity> medicineWithQuantities) {
		int foundedMedicineAndQuantity = 0;

		//za tu apoteku prolazimo kroz listu lekova sa kolicinom
		for(MedicineWithQuantity medicineWithQuantityInPharmacy :pharmacy.getMedicineWithQuantity()){

			// prolazimo kroz listu nasih potrebnih lekova
			for(MedicineWithQuantity neededMedicineAndQuantity : medicineWithQuantities){
				//proveravamo da li se lek u apoteci poklapa sa nasim lekom
				if(medicineWithQuantityInPharmacy.getMedicine().equals(neededMedicineAndQuantity.getMedicine())){
					//ima lek proveravamo kolicinu
					if(medicineWithQuantityInPharmacy.getQuantity() >= neededMedicineAndQuantity.getQuantity()){
						//ima za taj lek
						foundedMedicineAndQuantity ++;
					}

				}
			}
		}

		if(foundedMedicineAndQuantity >= medicineWithQuantities.size()){
			return true;
		}else {
			return  false;
		}
	}
	@Override
	public void checkAndAddPenals(Patient patient) {
		for(MedicineReservation reservation : this.medicineReservationService.getAll()) {
			if(reservation.getPatient()==null) {
				continue;
			}
				if(reservation.getPatient().equals(patient)) {
					if(LocalDate.now().getDayOfMonth()==1) {
						patient.setPenal(0);
						this.patientRepository.save(patient);
						break;
					}
					if(!reservation.getIsPenalGiven()) {
						if(reservation.getDueTo().isBefore(LocalDate.now()) && reservation.getDueToTime().isBefore(LocalTime.now()) && reservation.getStatus().equals(MedicineReservationStatus.RESERVED)) {
							patient.setPenal(patient.getPenal()+1);
							reservation.setIsPenalGiven(true);
							this.medicineReservationService.saveReservation(reservation);
							this.patientRepository.save(patient);
						}
					}
				}
		}
	}


}
