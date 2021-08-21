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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="SUPLIER")
public class Suplier {
	
	 @Id
	 @Column(name = "id")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<SuplierOffer> offers = new HashSet<SuplierOffer>();

	public Suplier() {
	}

	public Suplier(Long id, User user, Set<SuplierOffer> offers) {
		this.id = id;
		this.user = user;
		this.offers = offers;
	}

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

	public Set<SuplierOffer> getOffers() {
		return offers;
	}

	public void setOffers(Set<SuplierOffer> offers) {
		this.offers = offers;
	}
}
