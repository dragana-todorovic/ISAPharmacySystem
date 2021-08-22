package rs.ac.uns.ftn.informatika.spring.security.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="MEDICINERESERVATION")
public class MedicineReservation {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	 @Column(name = "numberOfReservation",nullable = false,unique = true)
	 private String numberOfReservation;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Patient patient;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private MedicineWithQuantity medicineWithQuantity;
	
	@Column(name = "dueTo", nullable = false)
	private LocalDate dueTo;
	
	@Column(name = "dueToTime", nullable = false)
	private LocalTime dueToTime;
	
	@Column(name = "status", nullable = false)
	private MedicineReservationStatus status;
	

	public LocalTime getDueToTime() {
		return dueToTime;
	}

	public void setDueToTime(LocalTime dueToTime) {
		this.dueToTime = dueToTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public MedicineWithQuantity getMedicineWithQuantity() {
		return medicineWithQuantity;
	}

	public void setMedicineWithQuantity(MedicineWithQuantity medicineWithQuantity) {
		this.medicineWithQuantity = medicineWithQuantity;
	}

	public LocalDate getDueTo() {
		return dueTo;
	}

	public void setDueTo(LocalDate localDueToDate) {
		this.dueTo = localDueToDate;
	}

	public MedicineReservationStatus getStatus() {
		return status;
	}

	public void setStatus(MedicineReservationStatus status) {
		this.status = status;
	}

	public String getNumberOfReservation() {
		return numberOfReservation;
	}

	public void setNumberOfReservation(String numberOfReservation) {
		this.numberOfReservation = numberOfReservation;
	}
	
}
