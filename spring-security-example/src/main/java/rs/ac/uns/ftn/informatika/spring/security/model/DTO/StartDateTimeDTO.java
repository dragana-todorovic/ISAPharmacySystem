package rs.ac.uns.ftn.informatika.spring.security.model.DTO;

public class StartDateTimeDTO {
	private String patientEmail;
	private String dermatologistEmail;
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
	

}
