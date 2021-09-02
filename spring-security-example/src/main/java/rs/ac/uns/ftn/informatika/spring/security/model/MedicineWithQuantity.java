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
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="MEDICINEWITHQUANTITY")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MedicineWithQuantity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)	
	private Medicine medicine;
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
	@Version
	@Column(name = "version", nullable = false, columnDefinition = "int default 1")
	private Long version;


	public MedicineWithQuantity() {
	}


	public MedicineWithQuantity( Medicine medicine, int quantity) {
		super();
		this.medicine = medicine;
		this.quantity = quantity;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "MedicineWithQuantity [id=" + id + ", medicine=" + medicine + ", quantity=" + quantity + ", getId()="
				+ getId() + ", getMedicine()=" + getMedicine() + ", getQuantity()=" + getQuantity() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
