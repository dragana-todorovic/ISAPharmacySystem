package rs.ac.uns.ftn.informatika.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineShape;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineType;
import rs.ac.uns.ftn.informatika.spring.security.service.MedicineService;
import rs.ac.uns.ftn.informatika.spring.security.view.MedicineView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/medicine", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicineController {
    @Autowired
    private MedicineService medicineService;

    @GetMapping("/getAllMedicine")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public List<Medicine> getAllMedicine()   {
        return this.medicineService.findAll();
    }


    @PostMapping("/addNewMedicine")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<?> addNewMedicine(@RequestBody MedicineView medicineView) {
        Medicine medicine = new Medicine();
        System.out.println("-------------------------------------------");
        System.out.println(medicineView.isWithPrescription());
        medicine.setCode(medicineView.getCode());
        medicine.setName(medicineView.getName());
        //type shape
        if(medicineView.getType().equals("ANTIBIOTIC")) {
            medicine.setType(MedicineType.ANTIBIOTIC);
        }else if(medicineView.getType().equals("ANESTHETIC")) {
            medicine.setType(MedicineType.ANESTHETIC);
        }else
            medicine.setType(MedicineType.ANTIHISTAMINE);

        if(medicineView.getShape().equals("CAPSULE")) {
            medicine.setShape(MedicineShape.CAPSULE);
        }
        else if(medicineView.getShape().equals("TABLET"))
            medicine.setShape(MedicineShape.TABLET);
        else if(medicineView.getShape().equals("POWDER"))
            medicine.setShape(MedicineShape.POWDER);
        else if(medicineView.getShape().equals("CREAM"))
            medicine.setShape(MedicineShape.CREAM);
        else if(medicineView.getShape().equals("OIL"))
            medicine.setShape(MedicineShape.OIL);
        else
            medicine.setShape(MedicineShape.SYRUP);
        medicine.setContent(medicineView.getContent());
        medicine.setProducer(medicineView.getProducer());
        medicine.setWithprescription(medicineView.isWithPrescription());
        //substituteMedicineCodes
        Set<String> subMedicine = new HashSet<String>(medicineView.getSubstituteMedicineCodes());
        medicine.setSubstituteMedicineCodes(subMedicine);
        medicine.setNotes(medicineView.getNotes());
        medicine.setContradiction(medicineView.getContradiction());
        medicine.setAdviseddailydose(medicineView.getAdviseddailydose());
        this.medicineService.save(medicine);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
