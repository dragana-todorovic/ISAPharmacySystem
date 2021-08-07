package rs.ac.uns.ftn.informatika.spring.security.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="WORKINGDAY")
public class WorkingDay {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "day", nullable = false)
	private LocalDate day;
	
	@Column(name = "startTime", nullable = false)
	private LocalTime startTime;
	
	@Column(name = "endTime", nullable = false)
	private LocalTime endTime;
}
