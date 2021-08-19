package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistComplaint;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistComplaint;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyComplaint;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistComplaintRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistComplaintRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyComplaintRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.ComplaintService;
import rs.ac.uns.ftn.informatika.spring.security.view.ComplaintView;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComplaintServiceImpl  implements ComplaintService {
    @Autowired
    private PharmacyComplaintRepository pharmacyComplaintRepository;

    @Autowired
    private PharmacistComplaintRepository pharmacistComplaintRepository;

    @Autowired
    private DermatologistComplaintRepository dermatologistComplaintRepository;

    @Override
    public List<PharmacyComplaint> findAllPharmacyComplaint() {
        List<PharmacyComplaint> result = pharmacyComplaintRepository.findAll();
        return result;
    }

    @Override
    public List<DermatologistComplaint> findAllDermatologistComplaint(){
        List<DermatologistComplaint> result = dermatologistComplaintRepository.findAll();
        return result;
    }

    @Override
    public List<PharmacistComplaint> findAllPharmacistComplaint(){
        List<PharmacistComplaint> result = pharmacistComplaintRepository.findAll();
        return result;
    }

    @Override
    public List<ComplaintView> getAllComplaints() {
        List<PharmacyComplaint> pharmacyComplaints = pharmacyComplaintRepository.findAll();
        List<DermatologistComplaint> dermatologistComplaints = dermatologistComplaintRepository.findAll();
        List<PharmacistComplaint> pharmacistComplaints = pharmacistComplaintRepository.findAll();

        List<ComplaintView> result = new ArrayList<ComplaintView>();

        for (PharmacyComplaint pharmacyComplaint : pharmacyComplaints) {
            ComplaintView complaintView = new ComplaintView();
            complaintView.setId(pharmacyComplaint.getId());
            complaintView.setContent(pharmacyComplaint.getContent());
            complaintView.setComplainedOnName(pharmacyComplaint.getPharmacy().getName());
            complaintView.setAnswered(pharmacyComplaint.isAnswered());
            complaintView.setUserName(pharmacyComplaint.getPatient().getUser().getEmail());
            complaintView.setType("PHARMACY");
            result.add(complaintView);
        }

        for (PharmacistComplaint pharmacyComplaint : pharmacistComplaints) {
            ComplaintView complaintView = new ComplaintView();
            complaintView.setId(pharmacyComplaint.getId());
            complaintView.setContent(pharmacyComplaint.getContent());
            complaintView.setComplainedOnName(pharmacyComplaint.getPharmacist().getUser().getFirstName() + " " +pharmacyComplaint.getPharmacist().getUser().getLastName());
            complaintView.setAnswered(pharmacyComplaint.isAnswered());
            complaintView.setUserName(pharmacyComplaint.getPatient().getUser().getEmail());
            complaintView.setType("PHARMACIST");
            result.add(complaintView);
        }

        for (DermatologistComplaint pharmacyComplaint : dermatologistComplaints) {
            ComplaintView complaintView = new ComplaintView();
            complaintView.setId(pharmacyComplaint.getId());
            complaintView.setContent(pharmacyComplaint.getContent());
            complaintView.setComplainedOnName(pharmacyComplaint.getDermatologist().getUser().getFirstName() + " " +pharmacyComplaint.getDermatologist().getUser().getLastName());
            complaintView.setAnswered(pharmacyComplaint.isAnswered());
            complaintView.setUserName(pharmacyComplaint.getPatient().getUser().getEmail());
            complaintView.setType("DERMATOLOGIST");
            result.add(complaintView);
        }

        return result;
    }
}