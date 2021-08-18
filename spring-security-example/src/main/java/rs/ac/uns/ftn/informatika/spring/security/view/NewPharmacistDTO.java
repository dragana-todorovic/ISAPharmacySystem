package rs.ac.uns.ftn.informatika.spring.security.view;

import java.util.List;

public class NewPharmacistDTO {
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private List<WorkingDayDTO> workingTimes;
	@Override
	public String toString() {
		return "NewPharmacistDTO [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", workingTimes=" + workingTimes + "]";
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<WorkingDayDTO> getWorkingTimes() {
		return workingTimes;
	}
	public void setWorkingTimes(List<WorkingDayDTO> workingTimes) {
		this.workingTimes = workingTimes;
	}

}
