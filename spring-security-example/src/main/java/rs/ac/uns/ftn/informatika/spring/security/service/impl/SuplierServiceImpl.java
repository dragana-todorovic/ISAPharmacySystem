package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.spring.security.model.*;
import rs.ac.uns.ftn.informatika.spring.security.repository.SuplierRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineService;
import rs.ac.uns.ftn.informatika.spring.security.service.SuplierService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.view.OfferMedicineListView;

@Service
public class SuplierServiceImpl implements SuplierService {
    @Autowired
    private SuplierRepository suplierRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SuplierService suplierService;

    @Autowired
    private MedicineService medicineService;

    @Override
    public Suplier save(Suplier suplier) {
        Suplier newSuplier = this.suplierRepository.save(suplier);
        return newSuplier;
    }

    @Override
    public Suplier findSuplierByUser(User user) {
        return this.suplierRepository.findByUser(user);
    }

    @Override
    public MedicineWithQuantity editSupplierMedicineQuantity(OfferMedicineListView offerView) {
        User user = this.userService.findByEmail(offerView.getEmail());
        Suplier suplier = this.suplierService.findSuplierByUser(user);

        //MedicineWithQuantity medicineWithQuantity = new MedicineWithQuantity();
        for (MedicineWithQuantity mq : suplier.getMedicineWithQuantity()) {
            if(mq.getId() == Long.parseLong(offerView.getMedicineWithQuantityId())){
                mq.setQuantity(Integer.parseInt(offerView.getQuantity()));
                this.suplierService.save(suplier);
                return mq;
            }
        }
        return null;
    }

    @Override
    public void addNewMedicineToList(OfferMedicineListView offerView) {
        User user = this.userService.findByEmail(offerView.getEmail());
        Suplier suplier = this.suplierService.findSuplierByUser(user);

        //in this case medicineWithQuantity is just medicineId i want to add to my list
       Medicine medicine = this.medicineService.findById(Long.valueOf(offerView.getMedicineWithQuantityId()));
       MedicineWithQuantity medicineWithQuantity = new MedicineWithQuantity(medicine, Integer.parseInt(offerView.getQuantity()));
        suplier.getMedicineWithQuantity().add(medicineWithQuantity);
        this.suplierService.save(suplier);
    }
}
