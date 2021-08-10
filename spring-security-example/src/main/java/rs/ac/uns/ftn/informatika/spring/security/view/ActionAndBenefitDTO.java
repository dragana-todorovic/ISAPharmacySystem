package rs.ac.uns.ftn.informatika.spring.security.view;

public class ActionAndBenefitDTO {
	
	private String pharmacyAdminEmail;
	private String startDate;
	private String endDate;
	private String description;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "ActionAndBenefitDTO [startDate=" + startDate + ", endDate=" + endDate + ", description=" + description
				+ "]";
	}
	public String getPharmacyAdminEmail() {
		return pharmacyAdminEmail;
	}
	public void setPharmacyAdminEmail(String pharmacyAdminEmail) {
		this.pharmacyAdminEmail = pharmacyAdminEmail;
	}
	
	

}
