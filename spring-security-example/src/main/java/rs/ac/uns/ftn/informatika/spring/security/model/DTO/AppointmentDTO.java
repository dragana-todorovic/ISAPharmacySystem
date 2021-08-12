package rs.ac.uns.ftn.informatika.spring.security.model.DTO;

import java.time.LocalDateTime;

public class AppointmentDTO {
	private String dermatologistEmail;
	private String patientEmail;
	private String patientId;
	private String diagnosis;
	private String medicineName;
	private String duration;
	
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getPatientEmail() {
		return patientEmail;
	}
	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}
	public String getDermatologistEmail() {
		return dermatologistEmail;
	}
	public void setDermatologistEmail(String dermatologistEmail) {
		this.dermatologistEmail = dermatologistEmail;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public String getMedicineName() {
		return medicineName;
	}
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
	

}
