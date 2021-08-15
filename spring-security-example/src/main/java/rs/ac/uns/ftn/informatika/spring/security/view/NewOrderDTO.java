package rs.ac.uns.ftn.informatika.spring.security.view;

import java.util.List;

public class NewOrderDTO {
	private List<MedicineForOrderView> medicines;
	private String date;
	private String time;

	public List<MedicineForOrderView> getMedicines() {
		return medicines;
	}

	public void setMedicines(List<MedicineForOrderView> medicines) {
		this.medicines = medicines;
	}

	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "NewOrderDTO [medicines=" + medicines + ", date=" + date + ", time=" + time + "]";
	}
	
	
	

}
