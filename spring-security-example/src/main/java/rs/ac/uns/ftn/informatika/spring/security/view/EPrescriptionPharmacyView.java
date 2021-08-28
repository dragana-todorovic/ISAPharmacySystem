package rs.ac.uns.ftn.informatika.spring.security.view;

import java.util.List;

public class EPrescriptionPharmacyView {
    private List<String> medicineCodes;
    private List<String> medicineCodesQuantity;
    private long pharmacyId;
    private double totalPrice;
    private double pharmacyAverageRating;
    private String pharmacyName;
    private String pharmacyAddress;

    public EPrescriptionPharmacyView() {
    }

    public EPrescriptionPharmacyView(List<String> medicineCodes, List<String> medicineCodesQuantity, long pharmacyId, double totalPrice, double pharmacyAverageRating, String pharmacyName, String pharmacyAddress) {
        this.medicineCodes = medicineCodes;
        this.medicineCodesQuantity = medicineCodesQuantity;
        this.pharmacyId = pharmacyId;
        this.totalPrice = totalPrice;
        this.pharmacyAverageRating = pharmacyAverageRating;
        this.pharmacyName = pharmacyName;
        this.pharmacyAddress = pharmacyAddress;
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

    public long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getPharmacyAverageRating() {
        return pharmacyAverageRating;
    }

    public void setPharmacyAverageRating(double pharmacyAverageRating) {
        this.pharmacyAverageRating = pharmacyAverageRating;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }
}
