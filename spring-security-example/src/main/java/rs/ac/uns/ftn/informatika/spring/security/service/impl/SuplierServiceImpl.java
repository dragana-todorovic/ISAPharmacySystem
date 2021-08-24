package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.Suplier;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.repository.SuplierRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.SuplierService;

@Service
public class SuplierServiceImpl implements SuplierService {
    @Autowired
    private SuplierRepository suplierRepository;

    @Override
    public Suplier save(Suplier suplier) {
        Suplier newSuplier = this.suplierRepository.save(suplier);
        return newSuplier;
    }

    @Override
    public Suplier findSuplierByUser(User user) {
        return this.suplierRepository.findByUser(user);
    }
}
