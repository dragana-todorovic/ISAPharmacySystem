package rs.ac.uns.ftn.informatika.spring.security.model.DTO;

public class CounselingDTO {
	private String pharmacistEmail;
	private String patientEmail;
	private String duration;
	private String startTime;
	private String startDate;
	

	public String getPharmacistEmail() {
		return pharmacistEmail;
	}
	public void setPharmacistEmail(String pharmacistEmail) {
		this.pharmacistEmail = pharmacistEmail;
	}
	public String getPatientEmail() {
		return patientEmail;
	}
	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

}
