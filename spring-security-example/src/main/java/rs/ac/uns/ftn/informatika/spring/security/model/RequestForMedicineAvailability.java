package rs.ac.uns.ftn.informatika.spring.security.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="REQUESTFORMEDICINEAVAILABILITY")
public class RequestForMedicineAvailability {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	 @Column(name = "createdAt", nullable = false)
	private LocalDateTime createdAt;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private MedicineWithQuantity medicineWithQuantity;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Pharmacy pharmacy;

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public MedicineWithQuantity getMedicineWithQuantity() {
		return medicineWithQuantity;
	}

	public void setMedicineWithQuantity(MedicineWithQuantity medicineWithQuantity) {
		this.medicineWithQuantity = medicineWithQuantity;
	}
	
}
