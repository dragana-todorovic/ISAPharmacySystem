package rs.ac.uns.ftn.informatika.spring.security.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import rs.ac.uns.ftn.informatika.spring.security.model.Pharmacy;

public class MedicineReservationView {
	private Long id;
	private UUID numberOfReservation;
	private String medicineName;
	private String pharmacyName;
	private String pharmacyCity;
	private String pharmacyStreet;
	private LocalTime dueToTime;
	private LocalDate dueTo;
	private int quanity;
	private Boolean isReservationExpired;
	
	
	public MedicineReservationView() {
		super();
	}

	public MedicineReservationView(Long id, UUID numberOfReservation, String medicineName, String pharmacyName,
			String pharmacyCity, String pharmacyStreet, LocalTime dueToTime, LocalDate dueTo, int quanity) {
		super();
		this.id = id;
		this.numberOfReservation = numberOfReservation;
		this.medicineName = medicineName;
		this.pharmacyName = pharmacyName;
		this.pharmacyCity = pharmacyCity;
		this.pharmacyStreet = pharmacyStreet;
		this.dueToTime = dueToTime;
		this.dueTo = dueTo;
		this.quanity = quanity;
	}

	@Override
	public String toString() {
		return "MedicineReservationView [medicineName=" + medicineName + ", pharmacyName=" + pharmacyName
				+ ", pharmacyCity=" + pharmacyCity + ", pharmacyStreet=" + pharmacyStreet + ", dueTo=" + dueTo
				+ ", quanity=" + quanity + "]";
	}
	
	public Boolean getIsReservationExpired() {
		return isReservationExpired;
	}

	public void setIsReservationExpired(Boolean isReservationExpired) {
		this.isReservationExpired = isReservationExpired;
	}

	public UUID getNumberOfReservation() {
		return numberOfReservation;
	}

	public void setNumberOfReservation(UUID numberOfReservation) {
		this.numberOfReservation = numberOfReservation;
	}

	public LocalTime getDueToTime() {
		return dueToTime;
	}

	public void setDueToTime(LocalTime dueToTime) {
		this.dueToTime = dueToTime;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
