package rs.ac.uns.ftn.informatika.spring.security.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


// POJO koji implementira Spring Security UserDetails interfejs koji specificira
// osnovne osobine Spring korisnika (koje role ima, da li je nalog zakljucan, istekao, da li su kredencijali istekli)
@Entity
@Table(name="PHARMACY")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pharmacy {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Address address;

    @Column(name = "description")
    private String description;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MedicineWithQuantity> medicineWithQuantity = new HashSet<MedicineWithQuantity>();
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Rating> ratings = new HashSet<Rating>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PriceList> priceList = new HashSet<PriceList>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MedicineReservation> medicineReservations = new HashSet<MedicineReservation>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MedicineOrder> medicineOrders = new HashSet<MedicineOrder>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ActionAndBenefit> actionsAndBenefits = new HashSet<ActionAndBenefit>();
   
    @Version
    @Column(name = "version")
    private long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Pharmacy [id=" + id + ", name=" + name + ", address=" + address + ", description=" + description + "]";
	}


	public void setAddress(Address a) {
		this.address = a;
	}

	public Set<MedicineWithQuantity> getMedicineWithQuantity() {
		return medicineWithQuantity;
	}

	public void setMedicineWithQuantity(Set<MedicineWithQuantity> medicineWithQuantity) {
		this.medicineWithQuantity = medicineWithQuantity;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}


	public Set<MedicineReservation> getMedicineReservations() {
		return medicineReservations;
	}

	public void setMedicineReservations(Set<MedicineReservation> medicineReservations) {
		this.medicineReservations = medicineReservations;
	}

	public Set<MedicineOrder> getMedicineOrders() {
		return medicineOrders;
	}

	public void setMedicineOrders(Set<MedicineOrder> medicineOrders) {
		this.medicineOrders = medicineOrders;
	}

	public Set<ActionAndBenefit> getActionsAndBenefits() {
		return actionsAndBenefits;
	}

	public void setActionsAndBenefits(Set<ActionAndBenefit> actionsAndBenefits) {
		this.actionsAndBenefits = actionsAndBenefits;
	}

	public Set<PriceList> getPriceList() {
		return priceList;
	}

	public void setPriceList(Set<PriceList> priceList) {
		this.priceList = priceList;
	}
}
