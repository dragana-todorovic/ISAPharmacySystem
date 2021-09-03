package rs.ac.uns.ftn.informatika.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistComplaint;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistComplaint;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyComplaint;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyComplaintRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.ComplaintService;
import rs.ac.uns.ftn.informatika.spring.security.service.EmailService;
import rs.ac.uns.ftn.informatika.spring.security.view.AnswerOnComplaintView;
import rs.ac.uns.ftn.informatika.spring.security.view.ComplaintView;

import java.util.List;

@RestController
@RequestMapping(value = "/complaint", produces = MediaType.APPLICATION_JSON_VALUE)
public class ComplaintController {
    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private EmailService emailService;


    @GetMapping("/getAllComplaint")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<List<ComplaintView>> getAllComplaint()   {
       return new ResponseEntity<>(this.complaintService.getAllComplaints(), HttpStatus.OK);
    }

    //send
    //	emailService.sendEmail(patient.getUser().getEmail(),"Successfully bought medicine by ePrescription","You have successfullbought medicine by ePrescription");
	//		System.out.println("Mejl je poslat");

    @PostMapping("/sendAnswer")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<?> sendAnswer(@RequestBody AnswerOnComplaintView answerInfo) {
        this.complaintService.sendAnswer(answerInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
