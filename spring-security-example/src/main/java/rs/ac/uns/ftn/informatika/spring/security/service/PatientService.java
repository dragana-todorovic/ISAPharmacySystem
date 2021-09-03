package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import rs.ac.uns.ftn.informatika.spring.security.model.EPrescription;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.view.EPrescriptionBuyPharmacyView;
import rs.ac.uns.ftn.informatika.spring.security.view.EPrescriptionPharmacyView;
import rs.ac.uns.ftn.informatika.spring.security.view.UserRegisterView;



public interface PatientService {
	List<Patient> findAll ();
	ArrayList<String> findPatientsAllergies(Long user_id);

	Patient findPatientByUser(User user);
	Patient savePatient(Patient patient);
	Patient findPatientById(Long id);
	void giveOnePenalForPatient(Patient p);
	int getPatientsDiscount(Patient patient);
	List<EPrescriptionPharmacyView> findPharmacyForEPrescription(String codesAndCount);
    void buyEPrescriptionInPharmacy(Patient patient, Pharmacy pharmacy, List<String> medicineCodes, List<String> medicineCodesQuantity);

	List<EPrescription> findAllEpresForUser(Patient patient);

    void checkAndAddPenals(Patient patient);

}
