package rs.ac.uns.ftn.informatika.spring.security.view;

public class OfferView {
    private String medicineOrderId;
    private String price;
    private String deliveryDate;
    private String supplierEmail;
    private String time;

    public OfferView(String medicineOrderId, String price, String deliveryDate, String supplierEmail, String time) {
        this.medicineOrderId = medicineOrderId;
        this.price = price;
        this.deliveryDate = deliveryDate;
        this.supplierEmail = supplierEmail;
        this.time = time;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public OfferView() {
    }

    public String getMedicineOrderId() {
        return medicineOrderId;
    }

    public void setMedicineOrderId(String medicineOrderId) {
        this.medicineOrderId = medicineOrderId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }
}
