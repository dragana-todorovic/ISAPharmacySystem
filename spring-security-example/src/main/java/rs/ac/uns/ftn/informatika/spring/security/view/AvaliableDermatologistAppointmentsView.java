package rs.ac.uns.ftn.informatika.spring.security.view;

public class AvaliableDermatologistAppointmentsView {
	private Long id;
	private String dermatologistFirstName;
	private String dermatologistLastName;
	private String date;
	private String time;
	private String duration;
	private String price;
	private String grade;
	
	
	
	public AvaliableDermatologistAppointmentsView() {
		super();
	}
	public AvaliableDermatologistAppointmentsView(Long id,String dermatologistFirstName, String dermatologistLastName,String date, String time, String duration,
			String price, String grade) {
		super();
		this.id=id;
		this.dermatologistFirstName = dermatologistFirstName;
		this.dermatologistLastName = dermatologistLastName;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.price = price;
		this.grade = grade;
	}

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDermatologistFirstName() {
		return dermatologistFirstName;
	}
	public void setDermatologistFirstName(String dermatologistFirstName) {
		this.dermatologistFirstName = dermatologistFirstName;
	}
	public String getDermatologistLastName() {
		return dermatologistLastName;
	}
	public void setDermatologistLastName(String dermatologistLastName) {
		this.dermatologistLastName = dermatologistLastName;
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
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	
}
