package rs.ac.uns.ftn.informatika.spring.security.view;

import java.time.LocalDate;
import java.time.LocalDateTime;

import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;

public class MedicineReservationView {
	private String medicineName;
	private String pharmacyName;
	private String pharmacyCity;
	private String pharmacyStreet;
	private LocalDate dueTo;
	private int quanity;
	
	
	public MedicineReservationView() {
		super();
	}
	public MedicineReservationView(String medicineName, String pharmacyName, String pharmacyCity, String pharmacyStreet,
			LocalDate dueTo,int quantity) {
		super();
		this.medicineName = medicineName;
		this.pharmacyName = pharmacyName;
		this.pharmacyCity = pharmacyCity;
		this.pharmacyStreet = pharmacyStreet;
		this.dueTo = dueTo;
		this.quanity=quantity;
	}

	@Override
	public String toString() {
		return "MedicineReservationView [medicineName=" + medicineName + ", pharmacyName=" + pharmacyName
				+ ", pharmacyCity=" + pharmacyCity + ", pharmacyStreet=" + pharmacyStreet + ", dueTo=" + dueTo
				+ ", quanity=" + quanity + "]";
	}
	public int getQuanity() {
		return quanity;
	}
	public void setQuanity(int quanity) {
		this.quanity = quanity;
	}
	public String getMedicineName() {
		return medicineName;
	}
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
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
	public LocalDate getDueTo() {
		return dueTo;
	}
	public void setDueTo(LocalDate dueTo) {
		this.dueTo = dueTo;
	}
	
	

}
