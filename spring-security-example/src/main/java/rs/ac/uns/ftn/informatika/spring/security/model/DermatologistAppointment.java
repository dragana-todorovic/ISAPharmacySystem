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
@Table(name="APPOITMENT")
public class DermatologistAppointment {
	
	@Id
	 @Column(name = "id")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Dermatologist dermatologist;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Patient patient;
	
	@Column(name = "startDateTime", nullable = false)
	private LocalDateTime startDateTime;
	
	@Column(name = "duration", nullable = false)
	private int duration;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Therapy therapy;
	

}
