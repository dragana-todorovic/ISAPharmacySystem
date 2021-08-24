package rs.ac.uns.ftn.informatika.spring.security.view;

public class PharamcistForCounselingView {
	private Long id;
	private String firstName;
	private String lastName;
	private double avrageGrade;
	
	public PharamcistForCounselingView() {
		super();
	}
	public PharamcistForCounselingView(Long id, String firstName, String lastName, double avrageGrade) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.avrageGrade = avrageGrade;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public double getAvrageGrade() {
		return avrageGrade;
	}
	public void setAvrageGrade(double avrageGrade) {
		this.avrageGrade = avrageGrade;
	}
	@Override
	public String toString() {
		return "PharamcistForCounseling [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", avrageGrade=" + avrageGrade + "]";
	}
	
}
