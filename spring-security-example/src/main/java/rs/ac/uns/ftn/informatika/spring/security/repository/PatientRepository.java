package rs.ac.uns.ftn.informatika.spring.security.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	Optional<Patient> findById(Long id);

	Patient findByUser(User user);
	List<Patient> findAll();
	Patient findPatientById(Long id);


		@Query(nativeQuery = true, value = "select name from patients_allergies_medicine left join medicine \n" +
				"on medicine.id=patients_allergies_medicine.allergies_medicine_id right join patients\n" +
				"on patients.id=patients_allergies_medicine.patient_id where patients.user_id= :user_id")
		ArrayList<String> findPatientsAllergies(@Param("user_id") Long user_id);
	}

