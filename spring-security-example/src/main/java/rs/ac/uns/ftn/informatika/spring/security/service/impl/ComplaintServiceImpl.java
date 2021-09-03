package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistComplaint;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistComplaint;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyComplaint;
import rs.ac.uns.ftn.informatika.spring.security.repository.DermatologistComplaintRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacistComplaintRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyComplaintRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.ComplaintService;
import rs.ac.uns.ftn.informatika.spring.security.service.EmailService;
import rs.ac.uns.ftn.informatika.spring.security.view.AnswerOnComplaintView;
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
    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private EmailService emailService;


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

    @Override
    public DermatologistComplaint saveDerm(DermatologistComplaint dermatologistComplaint) {
        return this.dermatologistComplaintRepository.save(dermatologistComplaint);
    }

    @Override
    public PharmacyComplaint savePharmacy(PharmacyComplaint pharmacyComplaint) {
        return this.pharmacyComplaintRepository.save(pharmacyComplaint);
    }

    @Override
    public PharmacistComplaint savePharmacist(PharmacistComplaint pharmacistComplaint) {
        return this.pharmacistComplaintRepository.save(pharmacistComplaint);
    }

    @Override
    @Transactional(readOnly = false)
    public void sendAnswer(AnswerOnComplaintView answerInfo) {
        List<PharmacistComplaint> pharmacistComplaints = this.complaintService.findAllPharmacistComplaint();
        List<DermatologistComplaint> dermatologistComplaints = this.complaintService.findAllDermatologistComplaint();
        List<PharmacyComplaint> pharmacyComplaints = this.complaintService.findAllPharmacyComplaint();

        String sendToEmail =null;

        if(answerInfo.getComplaintTip().equals("PHARMACY")) {
            PharmacyComplaint ph = pharmacyComplaintRepository.findPharmacyComplaintById(Long.parseLong(answerInfo.getComplaintId()));
            if (!ph.isAnswered()) {
                sendToEmail = ph.getPatient().getUser().getEmail();
                ph.setAnswered(true);
                this.complaintService.savePharmacy(ph);

            }
        }else   if(answerInfo.getComplaintTip().equals("PHARMACIST")){
            PharmacistComplaint ph = pharmacistComplaintRepository.findPharmacistComplaintById(Long.parseLong(answerInfo.getComplaintId()));

            if (!ph.isAnswered()) {
                        sendToEmail = ph.getPatient().getUser().getEmail();
                        ph.setAnswered(true);
                        this.complaintService.savePharmacist(ph);

                }

        }else   if(answerInfo.getComplaintTip().equals("DERMATOLOGIST")) {
            DermatologistComplaint ph = dermatologistComplaintRepository
                    .findDermatologistComplaintById(Long.parseLong(answerInfo.getComplaintId()));

            if (!ph.isAnswered()) {
                        sendToEmail = ph.getPatient().getUser().getEmail();
                        ph.setAnswered(true);
                        this.complaintService.saveDerm(ph);

            }
        }
        try {
            System.out.println("USAOOOOOOOOOOO DA POSALJE AAAAAAADMIN MEJL naaaaaa");
            System.out.println(sendToEmail);
            emailService.sendEmail(sendToEmail,"We value your opinion for our services",answerInfo.getAnswer());
            System.out.println("Mejl je poslat");

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
