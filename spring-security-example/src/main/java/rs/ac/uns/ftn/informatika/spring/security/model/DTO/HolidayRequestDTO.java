package rs.ac.uns.ftn.informatika.spring.security.model.DTO;

public class HolidayRequestDTO {
	private String email;
	private String startDate;
	private String endDate;
	 
	public HolidayRequestDTO() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
