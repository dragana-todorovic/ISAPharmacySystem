package rs.ac.uns.ftn.informatika.spring.security.view;

public class PharmacyWithMedicationView {
	private Long id;
	private String pharmacyName;
	private String street;
    private String city; 
    private double medicinePrice;
    private double avrageGrade;
   
	public PharmacyWithMedicationView() {
		super();
	}
	public PharmacyWithMedicationView(String pharmacyName, String street, String city,Long id) {
		super();
		this.id=id;
		this.pharmacyName = pharmacyName;
		this.street = street;
		this.city = city;
		
	}

	public PharmacyWithMedicationView(Long id, String pharmacyName, String street, String city, double medicinePrice,double avrageGrade) {
		this.id = id;
		this.pharmacyName = pharmacyName;
		this.street = street;
		this.city = city;
		this.medicinePrice = medicinePrice;
		this.avrageGrade=avrageGrade;
	}

	public double getMedicinePrice() {
		return medicinePrice;
	}

	public void setMedicinePrice(double medicinePrice) {
		this.medicinePrice = medicinePrice;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getAvrageGrade() {
		return avrageGrade;
	}
	public void setAvrageGrade(double avrageGrade) {
		this.avrageGrade = avrageGrade;
	}
	/*public double getMedicinePrice() {
		return medicinePrice;
	}

	public void setMedicinePrice(double medicinePrice) {
		this.medicinePrice = medicinePrice;
	}*/
	@Override
	public String toString() {
		return "PharmacyWithMedicationView [pharmacyName=" + pharmacyName + ", street=" + street + ", city=" + city
				+ ", medicinePrice="  + "]";
	}

}
