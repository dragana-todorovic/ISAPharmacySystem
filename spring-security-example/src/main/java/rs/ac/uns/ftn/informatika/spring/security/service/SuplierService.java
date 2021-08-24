package rs.ac.uns.ftn.informatika.spring.security.service;

import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyAdmin;
import rs.ac.uns.ftn.informatika.spring.security.model.Suplier;
import rs.ac.uns.ftn.informatika.spring.security.model.User;

public interface SuplierService {
    Suplier save(Suplier suplier);
    Suplier findSuplierByUser(User user);

}
