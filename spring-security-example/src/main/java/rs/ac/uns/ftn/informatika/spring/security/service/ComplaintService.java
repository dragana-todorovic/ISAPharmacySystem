package rs.ac.uns.ftn.informatika.spring.security.service;

import rs.ac.uns.ftn.informatika.spring.security.model.*;
import rs.ac.uns.ftn.informatika.spring.security.view.ComplaintView;

import java.util.List;

public interface ComplaintService {
    List<PharmacyComplaint> findAllPharmacyComplaint();
    List<DermatologistComplaint> findAllDermatologistComplaint();
    List<PharmacistComplaint> findAllPharmacistComplaint();
    List<ComplaintView> getAllComplaints();
    DermatologistComplaint saveDerm(DermatologistComplaint dermatologistComplaint);
    PharmacyComplaint savePharmacy(PharmacyComplaint pharmacyComplaint);
    PharmacistComplaint savePharmacist(PharmacistComplaint pharmacistComplaint);
}
