package rs.ac.uns.ftn.informatika.spring.security.view;

public class PharmacyForCounselingView {
	Long id;
	String pharmacyName;
	String pharmacyCity;
	String pharmacyStreet;
	String pharmacyGrade;
	
	
	public PharmacyForCounselingView() {
		super();
	}
	public PharmacyForCounselingView(Long id, String pharmacyName, String pharmacyCity, String pharmacyStreet,
			String pharmacyGrade) {
		super();
		this.id = id;
		this.pharmacyName = pharmacyName;
		this.pharmacyCity = pharmacyCity;
		this.pharmacyStreet = pharmacyStreet;
		this.pharmacyGrade = pharmacyGrade;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPharmacyName() {
		return pharmacyName;
	}
	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}
	public String getPharmacyCity() {
		return pharmacyCity;
	}
	public void setPharmacyCity(String pharmacyCity) {
		this.pharmacyCity = pharmacyCity;
	}
	public String getPharmacyStreet() {
		return pharmacyStreet;
	}
	public void setPharmacyStreet(String pharmacyStreet) {
		this.pharmacyStreet = pharmacyStreet;
	}
	public String getPharmacyGrade() {
		return pharmacyGrade;
	}
	public void setPharmacyGrade(String pharmacyGrade) {
		this.pharmacyGrade = pharmacyGrade;
	}

	@Override
	public String toString() {
		return "PharmacyForCounselingView [id=" + id + ", pharmacyName=" + pharmacyName + ", pharmacyCity="
				+ pharmacyCity + ", pharmacyStreet=" + pharmacyStreet + ", pharmacyGrade=" + pharmacyGrade
				 + "]";
	}
	
	
	
}
