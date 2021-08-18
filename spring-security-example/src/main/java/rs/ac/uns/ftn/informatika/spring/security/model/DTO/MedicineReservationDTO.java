package rs.ac.uns.ftn.informatika.spring.security.model.DTO;

public class MedicineReservationDTO {
	private Long medicineId;
	private Long pharmacyId;
	private String dueTo;
	private String patientEmail;
	private int quantity;
	
	
	
	public MedicineReservationDTO() {
		super();
	}
	public MedicineReservationDTO(Long medicineId, Long pharmacyId, String dueTo, String patientEmail, int quantity) {
		super();
		this.medicineId = medicineId;
		this.pharmacyId = pharmacyId;
		this.dueTo = dueTo;
		this.patientEmail = patientEmail;
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "MedicineReservationDTO [medicineId=" + medicineId + ", pharmacyId=" + pharmacyId + ", dueTo=" + dueTo
				+ ", patientEmail=" + patientEmail + ", quantity=" + quantity + "]";
	}
	public Long getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(Long medicineId) {
		this.medicineId = medicineId;
	}
	public Long getPharmacyId() {
		return pharmacyId;
	}
	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
	public String getDueTo() {
		return dueTo;
	}
	public void setDueTo(String dueTo) {
		this.dueTo = dueTo;
	}
	public String getPatientEmail() {
		return patientEmail;
	}
	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
