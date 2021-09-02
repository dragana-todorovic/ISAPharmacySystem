package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.EPrescription;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.repository.EPrescriptionRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.EPrescriptionService;

import java.util.List;

@Service
public class EPrescriptionServiceImpl implements EPrescriptionService {

    @Autowired
    private EPrescriptionRepository ePrescriptionRepository;

    @Override
    public EPrescription save(EPrescription ePrescription) {
        return this.ePrescriptionRepository.save(ePrescription);
    }

    @Override
    public List<EPrescription> findAll() {
        List<EPrescription> result = ePrescriptionRepository.findAll();
        return result;
    }
}
