package rs.ac.uns.ftn.informatika.spring.security.service;

import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistComplaint;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistComplaint;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyComplaint;
import rs.ac.uns.ftn.informatika.spring.security.view.ComplaintView;

import java.util.List;

public interface ComplaintService {
    List<PharmacyComplaint> findAllPharmacyComplaint();
    List<DermatologistComplaint> findAllDermatologistComplaint();
    List<PharmacistComplaint> findAllPharmacistComplaint();
    List<ComplaintView> getAllComplaints();
}
