package rs.ac.uns.ftn.informatika.spring.security.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PharmacistComplaint")
public class PharmacistComplaint {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Pharmacist pharmacist;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Patient patient;

	@Column(name = "content" , nullable = false)
	private String content;

	@Column(name = "isAnswered" )
	private boolean isAnswered;

	public PharmacistComplaint() {
	}

	public PharmacistComplaint(Long id, Pharmacist pharmacist, Patient patient, String content, boolean isAnswered) {
		this.id = id;
		this.pharmacist = pharmacist;
		this.patient = patient;
		this.content = content;
		this.isAnswered = isAnswered;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pharmacist getPharmacist() {
		return pharmacist;
	}

	public void setPharmacist(Pharmacist pharmacist) {
		this.pharmacist = pharmacist;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isAnswered() {
		return isAnswered;
	}

	public void setAnswered(boolean answered) {
		isAnswered = answered;
	}
}
