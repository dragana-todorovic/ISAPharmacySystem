package rs.ac.uns.ftn.informatika.spring.security.view;

import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.model.MedicinePrice;

public class AlreadyExistsMedicinePrice {
	
	private List<String> medicinePrices;

	public List<String> getMedicinePrices() {
		return medicinePrices;
	}

	public void setMedicinePrices(List<String> medicinePrices) {
		this.medicinePrices = medicinePrices;
	}
	
}
