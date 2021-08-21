package rs.ac.uns.ftn.informatika.spring.security.model;

import javax.persistence.*;

@Entity
@Table(name="SYSTEMADMIN")
public class SystemAdmin {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    public SystemAdmin() {
    }

    public SystemAdmin(Long id, User user) {
        this.id = id;
        this.user = user;
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

}
