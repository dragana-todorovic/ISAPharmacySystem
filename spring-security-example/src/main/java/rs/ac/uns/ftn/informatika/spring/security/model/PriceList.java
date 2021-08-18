package rs.ac.uns.ftn.informatika.spring.security.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PRICELIST")
public class PriceList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<MedicinePrice> medicinePriceList = new HashSet<MedicinePrice>();
	
	/*@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<AppoitmentPrice> appoitmentPriceList = new HashSet<AppoitmentPrice>();*/
	
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<MedicinePrice> getMedicinePriceList() {
		return medicinePriceList;
	}

	public void setMedicinePriceList(Set<MedicinePrice> medicinePriceList) {
		this.medicinePriceList = medicinePriceList;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	
	
}
