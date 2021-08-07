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

@Entity
@Table(name="MEDICINERESERVATION")
public class MedicineReservation {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Patient patient;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private MedicineWithQuantity medicineWithQuantity;
	
	@Column(name = "dueTo", nullable = false)
	private LocalDateTime dueTo;
	
	@Column(name = "status", nullable = false)
	private MedicineReservationStatus status;
}
