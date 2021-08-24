package rs.ac.uns.ftn.informatika.spring.security.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.spring.security.model.MedicineWithQuantity;
import rs.ac.uns.ftn.informatika.spring.security.model.Suplier;
import rs.ac.uns.ftn.informatika.spring.security.model.SuplierOffer;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.repository.MedicineWithQuantityRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.SuplierOfferRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.*;
import rs.ac.uns.ftn.informatika.spring.security.view.MedicineForOrderView;
import rs.ac.uns.ftn.informatika.spring.security.view.NewOrderDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.OfferView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/suplierOffer", produces = MediaType.APPLICATION_JSON_VALUE)
public class SuplierOfferController {
    @Autowired
    private UserService userService;
    @Autowired
    private MedicineOrderService medicineOrderService;

    @Autowired
    private SuplierService suplierService;

    @Autowired
    private SuplierOfferService suplierOfferService;

    @Autowired
    private SuplierOfferRepository suplierOfferRepository;

    @PostMapping("/makeOffer")
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseEntity<?> makeOffer(@RequestBody OfferView offerView) {
       if( this.suplierOfferService.saveOffer(offerView) != null) {
           return new ResponseEntity<>(HttpStatus.OK);
       }else
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
    @PostMapping("/editOffer")
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public ResponseEntity<?> editOffer(@RequestBody OfferView offerView) {
        if( this.suplierOfferService.editOffer(offerView) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/getAllOrdersBySuplier/{email}")
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public List<SuplierOffer> getAllOrdersBySuplier(@PathVariable(name="email") String email) {
        User user = this.userService.findByEmail(email);
        Suplier suplier = this.suplierService.findSuplierByUser(user);

        List<SuplierOffer> so = new ArrayList<>();
        for (SuplierOffer offer : suplier.getOffers()) {
            so.add(offer);
        }
        return so;
    }

    @GetMapping("/getSupplierOfferById/{supplierId}")
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public SuplierOffer getSupplierOfferById(@PathVariable(name="supplierId") String supplierId) {

        return this.suplierOfferRepository.findById(Long.valueOf(supplierId)).get();
    }
}
