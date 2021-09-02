package rs.ac.uns.ftn.informatika.spring.security.service;

import rs.ac.uns.ftn.informatika.spring.security.model.EPrescription;

import java.util.List;

public interface EPrescriptionService {
    EPrescription save(EPrescription ePrescription);
    List<EPrescription> findAll();
}
