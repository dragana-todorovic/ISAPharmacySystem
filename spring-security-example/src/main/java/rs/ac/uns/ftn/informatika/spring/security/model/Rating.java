package rs.ac.uns.ftn.informatika.spring.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RATING")
public class Rating {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
	
	 @Column(name = "rating", nullable = false)
	private int rating;

}
