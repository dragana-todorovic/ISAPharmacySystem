package rs.ac.uns.ftn.informatika.spring.security.view;


import javax.persistence.Column;

public class LoyaltyProgramView {
    private String appointmentPoints;
    private String advisingPoints;

    public LoyaltyProgramView() {
    }

    public LoyaltyProgramView(String appointmentPoints, String advisingPoints) {
        this.appointmentPoints = appointmentPoints;
        this.advisingPoints = advisingPoints;
    }

    public String getAppointmentPoints() {
        return appointmentPoints;
    }

    public void setAppointmentPoints(String appointmentPoints) {
        this.appointmentPoints = appointmentPoints;
    }

    public String getAdvisingPoints() {
        return advisingPoints;
    }

    public void setAdvisingPoints(String advisingPoints) {
        this.advisingPoints = advisingPoints;
    }
}
