package rs.ac.uns.ftn.informatika.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.ftn.informatika.spring.security.model.*;
import rs.ac.uns.ftn.informatika.spring.security.service.*;
import rs.ac.uns.ftn.informatika.spring.security.view.EditPharmacyView;
import rs.ac.uns.ftn.informatika.spring.security.view.RegisterPharmacyAdminView;
import rs.ac.uns.ftn.informatika.spring.security.view.RegisterView;

@RestController
@RequestMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private DermatologistService dermatologistService;

    @Autowired
    private PharmacyAdminService pharmacyAdminService;

    @Autowired
    private SuplierService suplierService;

    @Autowired
    private SystemAdminService systemAdminService;

    @Autowired
    private PharmacyService pharmacyService;

    @PostMapping("/dermatologist")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<?> addDermatologist(@RequestBody RegisterView registerUserView) {

        User existUserByEmail = this.userService.findByEmail(registerUserView.getEmail());
        if (existUserByEmail != null) {
            //log.warn("User with this email already exists");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = this.userService.saveUserDermatologist(registerUserView);
        Dermatologist dermatologist = new Dermatologist();
        dermatologist.setUser(user);
        this.dermatologistService.save(dermatologist);

        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PostMapping("/supplier")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<?> addSupplier(@RequestBody RegisterView registerUserView) {

        User existUserByEmail = this.userService.findByEmail(registerUserView.getEmail());
        if (existUserByEmail != null) {
            //log.warn("User with this email already exists");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = this.userService.saveUserSupplier(registerUserView);
        Suplier suplier = new Suplier();
        suplier.setUser(user);
        this.suplierService.save(suplier);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PostMapping("/pharmacyAdmin")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<?> addPharmacyAdmin(@RequestBody RegisterPharmacyAdminView registerUserView) {

        User existUserByEmail = this.userService.findByEmail(registerUserView.getEmail());
        if (existUserByEmail != null) {
            //log.warn("User with this email already exists");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = this.userService.saveUserPharmacyAdmin(registerUserView);
        Pharmacy pharmacy = this.pharmacyService.findById(Long.valueOf(registerUserView.getPharmacyId())).get();
        PharmacyAdmin pharmacyAdmin = new PharmacyAdmin();
        pharmacyAdmin.setUser(user);
        pharmacyAdmin.setPharmacy(pharmacy);
        this.pharmacyAdminService.save(pharmacyAdmin);
        return new ResponseEntity<>( HttpStatus.OK);
    }
    @PostMapping("/systemAdmin")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<?> addSystemAdmin(@RequestBody RegisterView registerUserView) {

        User existUserByEmail = this.userService.findByEmail(registerUserView.getEmail());
        if (existUserByEmail != null) {
            //log.warn("User with this email already exists");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = this.userService.saveUserAdminSystem(registerUserView);
        SystemAdmin systemAdmin = new SystemAdmin();
        systemAdmin.setUser(user);
        this.systemAdminService.save(systemAdmin);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PostMapping("/pharmacy")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<?> addPharmacy(@RequestBody EditPharmacyView pharmacyView) {
        Pharmacy newPharmacy = new Pharmacy();
        Address a = new Address();
        a.setStreet(pharmacyView.getStreet());
        a.setCity(pharmacyView.getCity());
        a.setCoordX("0");
        a.setCoordY("0");
        newPharmacy.setName(pharmacyView.getName());
        newPharmacy.setAddress(a);
        newPharmacy.setDescription(pharmacyView.getDescription());
        this.pharmacyService.save(newPharmacy);
        return new ResponseEntity<>( HttpStatus.OK);
    }

}
