package rs.ac.uns.ftn.informatika.spring.security.service;

import rs.ac.uns.ftn.informatika.spring.security.model.*;
import rs.ac.uns.ftn.informatika.spring.security.view.OfferMedicineListView;
import rs.ac.uns.ftn.informatika.spring.security.view.OfferView;

public interface SuplierService {
    Suplier save(Suplier suplier);
    Suplier findSuplierByUser(User user);
    MedicineWithQuantity editSupplierMedicineQuantity(OfferMedicineListView offerView);
    void addNewMedicineToList(OfferMedicineListView offerView);
}
