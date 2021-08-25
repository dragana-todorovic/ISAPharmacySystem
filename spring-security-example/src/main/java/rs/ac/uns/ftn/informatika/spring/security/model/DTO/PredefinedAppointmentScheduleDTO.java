package rs.ac.uns.ftn.informatika.spring.security.model.DTO;

public class PredefinedAppointmentScheduleDTO {
	private String dermatologistEmail;
	private String patientEmail;
	private String startDateTimeId;
	private String duration;
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDermatologistEmail() {
		return dermatologistEmail;
	}
	public void setDermatologistEmail(String dermatologistEmail) {
		this.dermatologistEmail = dermatologistEmail;
	}
	public String getPatientEmail() {
		return patientEmail;
	}
	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}
	public String getStartDateTimeId() {
		return startDateTimeId;
	}
	public void setStartDateTimeId(String startDateTimeId) {
		this.startDateTimeId = startDateTimeId;
	}
	

}
