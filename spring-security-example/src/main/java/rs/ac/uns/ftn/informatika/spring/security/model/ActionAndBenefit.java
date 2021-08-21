package rs.ac.uns.ftn.informatika.spring.security.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="ACTIONANDBENEFIT")
public class ActionAndBenefit {
	
	@Id
	 @Column(name = "id")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	
	@Column(name = "startDate", nullable = false)
	private LocalDate startDate;
	
	@Column(name = "endDate", nullable = false)
	private LocalDate endDate;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ActionAndBenefit [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", description="
				+ description + "]";
	}
	
	
}
