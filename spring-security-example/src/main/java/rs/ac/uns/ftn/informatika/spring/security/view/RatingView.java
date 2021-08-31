package rs.ac.uns.ftn.informatika.spring.security.view;

public class RatingView {
	private Long id;
	private String firstName;
	private String lastName;
	private int patientsGrade;
	
	
	public RatingView() {
		super();
	}

	public RatingView(Long id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public RatingView(Long id, String firstName, String lastName, int patientsGrade) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.patientsGrade = patientsGrade;
	}


	public Long getid() {
		return id;
	}


	public void setid(Long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public int getPatientsGrade() {
		return patientsGrade;
	}


	public void setPatientsGrade(int patientsGrade) {
		this.patientsGrade = patientsGrade;
	}


	@Override
	public String toString() {
		return "RatingDermatologistView [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", patientsGrade=" + patientsGrade + "]";
	}
	
	
}
