package rs.ac.uns.ftn.informatika.spring.security.view;

public class SubscribePatientOnPharmacyView {
    private String patientEmail;
    private String pharmacyId;

    public SubscribePatientOnPharmacyView() {
    }

    public SubscribePatientOnPharmacyView(String patientEmail, String pharmacyId) {
        this.patientEmail = patientEmail;
        this.pharmacyId = pharmacyId;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = pharmacyId;
    }
}
