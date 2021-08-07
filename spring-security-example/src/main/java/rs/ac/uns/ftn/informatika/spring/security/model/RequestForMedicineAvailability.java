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
}
