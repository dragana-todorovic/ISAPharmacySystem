package rs.ac.uns.ftn.informatika.spring.security.model.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class WorkCalendarDTO {
	private Long id;
	private String pharmacyName;
	private LocalDate startTime;
	private String startDateTime;
	private int duration;
	private String patientName;
	private String patientLastName;
	
	public WorkCalendarDTO(Long id,String pharmacyName, LocalDate startTime,String startDateTime, int duration, String patientName, String patientLastName) {
		super();
		this.id = id;
		this.pharmacyName = pharmacyName;
		this.startTime = startTime;
		this.startDateTime= startDateTime;
		this.duration = duration;
		this.patientName = patientName;
		this.patientLastName = patientLastName;
	}
	public WorkCalendarDTO(Long id,String pharmacyName, LocalDate startTime,String startDateTime, int duration) {
		super();
		this.id = id;
		this.pharmacyName = pharmacyName;
		this.startTime = startTime;
		this.startDateTime= startDateTime;
		this.duration = duration;

	}
	
	
	public String getPharmacyName() {
		return pharmacyName;
	}


	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}


	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDate startTime) {
		this.startTime = startTime;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientLastName() {
		return patientLastName;
	}
	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}
	
	
	
}
