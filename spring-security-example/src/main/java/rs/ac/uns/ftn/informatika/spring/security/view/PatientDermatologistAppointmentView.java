package rs.ac.uns.ftn.informatika.spring.security.view;

import java.time.LocalDateTime;

public class PatientDermatologistAppointmentView {
	private Long id;
	private String dermatologistsFirstName;
	private String dermatologistsLastName;
	private String pharmacyName;
	private String pharmacyCity;
	private String pharmacyStreet;
	private LocalDateTime dateAndtime;
	private int duration;
	private String price;
	private Boolean isAppointmentExpired;
	private Boolean isHistory;
	
	
	
	public PatientDermatologistAppointmentView() {
		super();
	}

	public PatientDermatologistAppointmentView(Long id, String dermatologistsFirstName, String dermatologistsLastName,
			String pharmacyName, String pharmacyCity, String pharmacyStreet, LocalDateTime dateAndtime, int duration,
			String price, Boolean isAppointmentExpired,Boolean isHistory) {
		super();
		this.id = id;
		this.dermatologistsFirstName = dermatologistsFirstName;
		this.dermatologistsLastName = dermatologistsLastName;
		this.pharmacyName = pharmacyName;
		this.pharmacyCity = pharmacyCity;
		this.pharmacyStreet = pharmacyStreet;
		this.dateAndtime = dateAndtime;
		this.duration = duration;
		this.price = price;
		this.isAppointmentExpired = isAppointmentExpired;
		this.isHistory = isHistory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDermatologistsFirstName() {
		return dermatologistsFirstName;
	}

	public void setDermatologistsFirstName(String dermatologistsFirstName) {
		this.dermatologistsFirstName = dermatologistsFirstName;
	}

	public String getDermatologistsLastName() {
		return dermatologistsLastName;
	}

	public void setDermatologistsLastName(String dermatologistsLastName) {
		this.dermatologistsLastName = dermatologistsLastName;
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

	public LocalDateTime getDateAndtime() {
		return dateAndtime;
	}

	public void setDateAndtime(LocalDateTime dateAndtime) {
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

	public Boolean getIsAppointmentExpired() {
		return isAppointmentExpired;
	}

	public void setIsAppointmentExpired(Boolean isAppointmentExpired) {
		this.isAppointmentExpired = isAppointmentExpired;
	}

	public Boolean getIsHistory() {
		return isHistory;
	}

	public void setIsHistory(Boolean isHistory) {
		this.isHistory = isHistory;
	}

	@Override
	public String toString() {
		return "PatientDermatologistAppointmentView [id=" + id + ", dermatologistsFirstName=" + dermatologistsFirstName
				+ ", dermatologistsLastName=" + dermatologistsLastName + ", pharmacyName=" + pharmacyName
				+ ", pharmacyCity=" + pharmacyCity + ", pharmacyStreet=" + pharmacyStreet + ", dateAndtime="
				+ dateAndtime + ", duration=" + duration + ", price=" + price + ", isAppointmentExpired="
				+ isAppointmentExpired + ", isHistory=" + isHistory + "]";
	}



}
