package rs.ac.uns.ftn.informatika.spring.security.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="HOLIDAYREQUEST")
public class HolidayRequest {
	
	 @Id
	 @Column(name = "id")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	 
	
	@Column(name = "startDate", nullable = false)
	private LocalDate startDate;
	
	@Column(name = "endDate", nullable = false)
	private LocalDate endDate;
	
	@Column(name = "status", nullable = false)
	private HolidayRequestStatus status;

}
