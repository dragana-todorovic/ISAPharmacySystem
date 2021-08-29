package rs.ac.uns.ftn.informatika.spring.security.view;

public class OfferMedicineListView {
    private  String  quantity;
    private String email;
    private  String medicineWithQuantityId;

    public OfferMedicineListView() {
    }

    public OfferMedicineListView(String quantity, String email, String medicineWithQuantityId) {
        this.quantity = quantity;
        this.email = email;
        this.medicineWithQuantityId = medicineWithQuantityId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMedicineWithQuantityId() {
        return medicineWithQuantityId;
    }

    public void setMedicineWithQuantityId(String medicineWithQuantityId) {
        this.medicineWithQuantityId = medicineWithQuantityId;
    }
}
