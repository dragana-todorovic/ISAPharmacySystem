package rs.ac.uns.ftn.informatika.spring.security.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="EPRESCRIPTION")
public class EPrescription {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<MedicineWithQuantity> medicines = new HashSet<MedicineWithQuantity>();
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Patient patient;
	
	@Column(name = "issuedDate", nullable = false)
	private LocalDate issuedDate;

	public EPrescription() {
	}

	public EPrescription(Long id, Set<MedicineWithQuantity> medicines, Patient patient, LocalDate issuedDate) {
		this.id = id;
		this.medicines = medicines;
		this.patient = patient;
		this.issuedDate = issuedDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<MedicineWithQuantity> getMedicines() {
		return medicines;
	}

	public void setMedicines(Set<MedicineWithQuantity> medicines) {
		this.medicines = medicines;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public LocalDate getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(LocalDate issuedDate) {
		this.issuedDate = issuedDate;
	}
}
