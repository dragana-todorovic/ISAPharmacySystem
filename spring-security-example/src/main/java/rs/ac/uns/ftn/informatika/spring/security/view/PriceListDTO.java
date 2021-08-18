package rs.ac.uns.ftn.informatika.spring.security.view;

import java.util.List;

public class PriceListDTO {
	
	private List<MedicinePriceDTO> medicines;
	private String date;
	public List<MedicinePriceDTO> getMedicines() {
		return medicines;
	}
	public void setMedicines(List<MedicinePriceDTO> medicines) {
		this.medicines = medicines;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "PriceListDTO [medicines=" + medicines + ", date=" + date + "]";
	}
	
	

}
