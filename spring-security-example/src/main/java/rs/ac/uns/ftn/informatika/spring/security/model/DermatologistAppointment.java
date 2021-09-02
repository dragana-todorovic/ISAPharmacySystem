package rs.ac.uns.ftn.informatika.spring.security.model;

import java.time.LocalDateTime;

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
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.Version;

@Entity
@Table(name="APPOITMENT",
uniqueConstraints = { @UniqueConstraint(columnNames = { "dermatologist_id", "startDateTime" }) })
public class DermatologistAppointment {
	
	@Id
	 @Column(name = "id")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Dermatologist dermatologist;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Pharmacy pharmacy;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Patient patient;
	

	
	@Column(name = "startDateTime", nullable = false)
	private LocalDateTime startDateTime;
	
	@Column(name = "duration", nullable = false)
	private int duration;
	
	@Column(name = "description",nullable = true)
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Therapy therapy;
	
	@Version
	@Column(name = "version", nullable = false, columnDefinition = "int default 1")
	private Long version;
	


	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Dermatologist getDermatologist() {
		return dermatologist;
	}

	public void setDermatologist(Dermatologist dermatologist) {
		this.dermatologist = dermatologist;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Therapy getTherapy() {
		return therapy;
	}

	public void setTherapy(Therapy therapy) {
		this.therapy = therapy;
	}
	
	

}
