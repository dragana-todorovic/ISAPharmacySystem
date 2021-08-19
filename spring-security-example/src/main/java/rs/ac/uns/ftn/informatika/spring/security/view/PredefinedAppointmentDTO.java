package rs.ac.uns.ftn.informatika.spring.security.view;

public class PredefinedAppointmentDTO {
	
	private String dermatologistId;
	private String date;
	private String time;
	private String duration;
	private String price;
	public String getDermatologistId() {
		return dermatologistId;
	}
	public void setDermatologistId(String dermatologistId) {
		this.dermatologistId = dermatologistId;
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
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "PredefinedAppointmentDTO [dermatologistId=" + dermatologistId + ", date=" + date + ", time=" + time
				+ ", duration=" + duration + ", price=" + price + "]";
	}
	
	

}
