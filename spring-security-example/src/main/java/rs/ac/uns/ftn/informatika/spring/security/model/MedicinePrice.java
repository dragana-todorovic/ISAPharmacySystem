package rs.ac.uns.ftn.informatika.spring.security.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="MEDICINEPRICE")
public class MedicinePrice {
	
	@Id
	 @Column(name = "id")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Medicine medicine;
	
	 @Column(name = "price", nullable = false)
	private double price;
	
	 @Column(name = "startDate", nullable = false)
	private LocalDateTime startDate;
	
	 @Column(name = "endDate", nullable = false)
	private LocalDateTime endTime;
	

}
