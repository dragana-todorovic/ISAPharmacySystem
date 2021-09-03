package rs.ac.uns.ftn.informatika.spring.security.service;

import org.springframework.web.bind.annotation.RequestBody;
import rs.ac.uns.ftn.informatika.spring.security.model.*;
import rs.ac.uns.ftn.informatika.spring.security.view.AnswerOnComplaintView;
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
    void sendAnswer(AnswerOnComplaintView answerInfo);
}
