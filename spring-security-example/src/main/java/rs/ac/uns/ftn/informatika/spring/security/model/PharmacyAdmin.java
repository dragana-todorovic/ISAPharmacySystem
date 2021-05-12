package rs.ac.uns.ftn.informatika.spring.security.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="PHARMACYADMIN")
public class PharmacyAdmin{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Pharmacy pharmacy;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User user;
	
	public PharmacyAdmin() {
		super();
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
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

	@Override
	public String toString() {
		return "PharmacyAdmin [id=" + id + ", pharmacy=" + pharmacy + ", user=" + user + "]";
	}
	
	
	
 
}
