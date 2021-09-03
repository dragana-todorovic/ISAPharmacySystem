package rs.ac.uns.ftn.informatika.spring.security.model;

import javax.persistence.*;

@Entity
@Table(name="LOYALTYPROGRAM")
public class LoyaltyProgram {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appointmentPoints", nullable = false)
    private int appointmentPoints;

    @Column(name = "advisingPoints", nullable = false)
    private int advisingPoints;

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "int default 1")
    private long version;
    public LoyaltyProgram() {
    }

    public LoyaltyProgram(Long id, int appointmentPoints, int advisingPoints) {
        this.id = id;
        this.appointmentPoints = appointmentPoints;
        this.advisingPoints = advisingPoints;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAppointmentPoints() {
        return appointmentPoints;
    }

    public void setAppointmentPoints(int appointmentPoints) {
        this.appointmentPoints = appointmentPoints;
    }

    public int getAdvisingPoints() {
        return advisingPoints;
    }

    public void setAdvisingPoints(int advisingPoints) {
        this.advisingPoints = advisingPoints;
    }
}
