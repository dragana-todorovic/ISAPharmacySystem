package rs.ac.uns.ftn.informatika.spring.security.model;

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
@Table(name="MEDICINEORDER")
public class MedicineOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)	
	private Set<MedicineWithQuantity> medicines = new HashSet<MedicineWithQuantity>();
	
	@Column(name = "timeLimit" , nullable = false)
	private LocalDateTime timeLimit;
	
	@Column(name = "status" , nullable = false)
	private MedicineOrderStatus status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<MedicineWithQuantity> getMedicines() {
		return medicines;
	}

	public void setMedicines(Set<MedicineWithQuantity> medicines) {
		this.medicines = medicines;
	}

	public LocalDateTime getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(LocalDateTime timeLimit) {
		this.timeLimit = timeLimit;
	}

	public MedicineOrderStatus getStatus() {
		return status;
	}

	public void setStatus(MedicineOrderStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MedicineOrder [id=" + id + ", medicines=" + medicines + ", timeLimit=" + timeLimit + ", status="
				+ status + "]";
	}
	
}
