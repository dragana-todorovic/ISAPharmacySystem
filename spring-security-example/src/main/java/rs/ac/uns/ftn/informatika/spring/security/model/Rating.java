package rs.ac.uns.ftn.informatika.spring.security.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="RATING")
public class Rating {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
	
	 @Column(name = "rating", nullable = false)
	private int rating;
	 
	 @Column(name = "patient_id", nullable = false)
	 private long patient_id;
		 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public long getPatient() {
		return patient_id;
	}

	public void setPatient(long patient) {
		this.patient_id = patient;
	}
	
}
