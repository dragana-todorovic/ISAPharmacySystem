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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="SUPLIEROFFER")

public class SuplierOffer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private MedicineOrder medicineOrder;
	
	@Column(name = "price", nullable = false)
	private double price;
	
	@Column(name = "deleveryTime", nullable = false)
	private LocalDateTime deleveryTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MedicineOrder getMedicineOrder() {
		return medicineOrder;
	}

	public void setMedicineOrder(MedicineOrder medicineOrder) {
		this.medicineOrder = medicineOrder;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDateTime getDeleveryTime() {
		return deleveryTime;
	}

	public void setDeleveryTime(LocalDateTime deleveryTime) {
		this.deleveryTime = deleveryTime;
	}

	@Override
	public String toString() {
		return "SuplierOffer [id=" + id + ", medicineOrder=" + medicineOrder + ", price=" + price + ", deleveryTime="
				+ deleveryTime + "]";
	}
	
	
}
