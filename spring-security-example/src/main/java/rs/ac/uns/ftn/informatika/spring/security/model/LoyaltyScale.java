package rs.ac.uns.ftn.informatika.spring.security.model;

import javax.persistence.*;

@Entity
@Table(name="LOYALTYSCALE")
public class LoyaltyScale {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "category", nullable = false)
	private PatientCategory category;
	
	@Column(name = "neededPoints", nullable = false)
	private int neededPoints;
	
	@Column(name = "discount", nullable = false)
	private int disccount;

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Version
	@Column(name = "version", nullable = false, columnDefinition = "int default 1")
	private long version;

	public LoyaltyScale(Long id, PatientCategory category, int neededPoints, int disccount) {
		this.id = id;
		this.category = category;
		this.neededPoints = neededPoints;
		this.disccount = disccount;
	}

	public LoyaltyScale() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PatientCategory getCategory() {
		return category;
	}

	public void setCategory(PatientCategory category) {
		this.category = category;
	}

	public int getNeededPoints() {
		return neededPoints;
	}

	public void setNeededPoints(int neededPoints) {
		this.neededPoints = neededPoints;
	}

	public int getDisccount() {
		return disccount;
	}

	public void setDisccount(int disccount) {
		this.disccount = disccount;
	}

	@Override
	public String toString() {
		return "LoyaltyScale{" +
				"id=" + id +
				", category=" + category +
				", neededPoints=" + neededPoints +
				", disccount=" + disccount +
				'}';
	}
}
