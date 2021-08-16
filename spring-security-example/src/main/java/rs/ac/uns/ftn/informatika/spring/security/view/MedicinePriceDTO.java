package rs.ac.uns.ftn.informatika.spring.security.view;

public class MedicinePriceDTO {
	
	private String medicineId;
	private String price;
	public String getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(String medicineId) {
		this.medicineId = medicineId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public MedicinePriceDTO(String medicineId, String price) {
		super();
		this.medicineId = medicineId;
		this.price = price;
	}
	
	

}
