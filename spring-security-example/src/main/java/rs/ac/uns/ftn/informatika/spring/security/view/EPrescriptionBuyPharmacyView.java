package rs.ac.uns.ftn.informatika.spring.security.view;

import java.util.List;

public class EPrescriptionBuyPharmacyView {
    private List<String> medicineCodes;
    private List<String> medicineCodesQuantity;
    private String pharmacyId;
    private String patientEmail;

    public EPrescriptionBuyPharmacyView(List<String> medicineCodes, List<String> medicineCodesQuantity, String pharmacyId, String patientEmail) {
        this.medicineCodes = medicineCodes;
        this.medicineCodesQuantity = medicineCodesQuantity;
        this.pharmacyId = pharmacyId;
        this.patientEmail = patientEmail;
    }

    public EPrescriptionBuyPharmacyView() {
    }

    public List<String> getMedicineCodes() {
        return medicineCodes;
    }

    public void setMedicineCodes(List<String> medicineCodes) {
        this.medicineCodes = medicineCodes;
    }

    public List<String> getMedicineCodesQuantity() {
        return medicineCodesQuantity;
    }

    public void setMedicineCodesQuantity(List<String> medicineCodesQuantity) {
        this.medicineCodesQuantity = medicineCodesQuantity;
    }

    public String getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }
}
