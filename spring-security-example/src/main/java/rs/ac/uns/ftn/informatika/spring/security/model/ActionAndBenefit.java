package rs.ac.uns.ftn.informatika.spring.security.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ACTIONANDBENEFIT")
public class ActionAndBenefit {
	
	@Id
	 @Column(name = "id")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	
	@Column(name = "startDate", nullable = false)
	private LocalDateTime startDate;
	
	@Column(name = "endDate", nullable = false)
	private LocalDateTime endDate;
	
	@Column(name = "description", nullable = false)
	private String description;
	
}
