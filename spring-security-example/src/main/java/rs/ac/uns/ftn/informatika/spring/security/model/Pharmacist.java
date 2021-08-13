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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="PHARMACIST")
public class Pharmacist {
	 @Id
	 @Column(name = "id")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Rating> ratings = new HashSet<Rating>();
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private WorkingTime workingTime;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<HolidayRequest> holidayRequests = new HashSet<HolidayRequest>();

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

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	public WorkingTime getWorkingTimes() {
		return workingTime;
	}

	public void setWorkingTimes(WorkingTime workingTimes) {
		this.workingTime = workingTimes;
	}

	public Set<HolidayRequest> getHolidayRequests() {
		return holidayRequests;
	}

	public void setHolidayRequests(Set<HolidayRequest> holidayRequests) {
		this.holidayRequests = holidayRequests;
	}

	@Override
	public String toString() {
		return "Pharmacist [id=" + id + ", user=" + user + ", ratings=" + ratings + ", workingTime=" + workingTime
				+ ", holidayRequests=" + holidayRequests + "]";
	}


}
