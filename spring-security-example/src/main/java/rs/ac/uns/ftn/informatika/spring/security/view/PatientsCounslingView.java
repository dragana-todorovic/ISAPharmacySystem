package rs.ac.uns.ftn.informatika.spring.security.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PatientsCounslingView {
	private Long id;
	private String pharmacistsFirstName;
	private String pharmacistsLastName;
	private String pharmacyName;
	private String pharmacyCity;
	private String pharmacyStreet;
	private LocalDateTime dateAndtime;
	private int duration;
	private String price;
	private Boolean isCounslingExpired;
	public PatientsCounslingView() {
		super();
	}
	public PatientsCounslingView(Long id, String pharmacistsFirstName, String pharmacistsLastName, String pharmacyName,
			String pharmacyCity, String pharmacyStreet, LocalDateTime dateAndtime, int duration, String price,
			Boolean isCounslingExpired) {
		super();
		this.id = id;
		this.pharmacistsFirstName = pharmacistsFirstName;
		this.pharmacistsLastName = pharmacistsLastName;
		this.pharmacyName = pharmacyName;
		this.pharmacyCity = pharmacyCity;
		this.pharmacyStreet = pharmacyStreet;
		this.dateAndtime = dateAndtime;
		this.duration = duration;
		this.price = price;
		this.isCounslingExpired = isCounslingExpired;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPharmacistsFirstName() {
		return pharmacistsFirstName;
	}
	public void setPharmacistsFirstName(String pharmacistsFirstName) {
		this.pharmacistsFirstName = pharmacistsFirstName;
	}
	public String getPharmacistsLastName() {
		return pharmacistsLastName;
	}
	public void setPharmacistsLastName(String pharmacistsLastName) {
		this.pharmacistsLastName = pharmacistsLastName;
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
	public LocalDateTime getTime() {
		return dateAndtime;
	}
	public void setDate(LocalDateTime dateAndtime) {
		this.dateAndtime = dateAndtime;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Boolean getIsCounslingExpired() {
		return isCounslingExpired;
	}
	public void setIsCounslingExpired(Boolean isCounslingExpired) {
		this.isCounslingExpired = isCounslingExpired;
	}
	@Override
	public String toString() {
		return "PatientsCounslingView [id=" + id + ", pharmacistsFirstName=" + pharmacistsFirstName
				+ ", pharmacistsLastName=" + pharmacistsLastName + ", pharmacyName=" + pharmacyName + ", pharmacyCity="
				+ pharmacyCity + ", pharmacyStreet=" + pharmacyStreet + ", date and time=" + dateAndtime + 
				 ", duration=" + duration + ", price=" + price + ", isCounslingExpired=" + isCounslingExpired + "]";
	}
	
	
	
	
}
