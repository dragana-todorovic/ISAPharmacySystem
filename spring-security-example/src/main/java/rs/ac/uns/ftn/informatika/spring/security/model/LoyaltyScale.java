package rs.ac.uns.ftn.informatika.spring.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LOYALTYSCALE")
public class LoyaltyScale {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "category", nullable = false)
	private PatientCategory category;
	
	@Column(name = "neededPoints", nullable = false)
	private int neededPoints;
	
	@Column(name = "discount", nullable = false)
	private int disccount;
	
}
