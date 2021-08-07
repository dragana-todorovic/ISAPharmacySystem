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
	
}
