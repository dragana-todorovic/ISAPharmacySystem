package rs.ac.uns.ftn.informatika.spring.security.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="PATIENTS")
public class Patient {
	 @Id
	 @Column(name = "id")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;
	
	@Column(name = "points", nullable = false)
	private int points = 0;
	
	@Column(name = "category", nullable = false)
	private  PatientCategory category = PatientCategory.REGULAR;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Set<Medicine> allergiesMedicine = new HashSet<Medicine>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public PatientCategory getCategory() {
		return category;
	}

	public void setCategory(PatientCategory category) {
		this.category = category;
	}

	public Set<Medicine> getAllergies() {
		return allergiesMedicine;
	}

	public void setAllergies(Set<Medicine> allergiesMedicine) {
		this.allergiesMedicine = allergiesMedicine;
	}

	public Patient(Long id, User user, int points, PatientCategory category, Set<Medicine> allergiesMedicine) {
		super();
		this.id = id;
		this.user = user;
		this.points = points;
		this.category = category;
		this.allergiesMedicine = allergiesMedicine;
	}

	public Patient() {
		super();
	}
	 
	
}
