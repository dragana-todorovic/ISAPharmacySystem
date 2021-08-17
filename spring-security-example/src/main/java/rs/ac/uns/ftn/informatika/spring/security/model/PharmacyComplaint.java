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
@Table(name="PharmacyComplaint")
public class PharmacyComplaint {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Pharmacy pharmacy;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Patient patient;
	@Column(name = "content" , nullable = false)
	private String content;

	@Column(name = "isAnswered" )
	private boolean isAnswered;

	public PharmacyComplaint() {
	}

	public PharmacyComplaint(Long id, Pharmacy pharmacy, Patient patient, String content, boolean isAnswered) {
		this.id = id;
		this.pharmacy = pharmacy;
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

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
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
