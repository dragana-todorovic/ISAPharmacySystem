package rs.ac.uns.ftn.informatika.spring.security.view;

public class MedicineForOrderView {
	
	private String medicineId;
	private String quantity;
	public String getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(String medicineId) {
		this.medicineId = medicineId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "MedicineForOrder [medicineId=" + medicineId + ", quantity=" + quantity + "]";
	}

}
