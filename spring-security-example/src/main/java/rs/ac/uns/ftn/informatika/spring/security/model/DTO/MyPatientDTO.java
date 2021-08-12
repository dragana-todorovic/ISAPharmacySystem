package rs.ac.uns.ftn.informatika.spring.security.model.DTO;

import java.time.LocalDateTime;

public class MyPatientDTO {
	private long myPatientId;
	private String name;
	private String surname;
	private LocalDateTime startDateTime;
	public MyPatientDTO() {
		super();
	}
	public MyPatientDTO(long myPatientId, String name, String surname, LocalDateTime startDateTime) {
		super();
		this.myPatientId = myPatientId;
		this.name = name;
		this.surname = surname;
		this.startDateTime = startDateTime;
	}
	public long getMyPatientId() {
		return myPatientId;
	}
	public void setMyPatientId(long myPatientId) {
		this.myPatientId = myPatientId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}
	

}
