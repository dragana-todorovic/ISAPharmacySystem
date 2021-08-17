package rs.ac.uns.ftn.informatika.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.service.ComplaintService;
import rs.ac.uns.ftn.informatika.spring.security.view.ComplaintView;

import java.util.List;

@RestController
@RequestMapping(value = "/complaint", produces = MediaType.APPLICATION_JSON_VALUE)
public class ComplaintController {
    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/getAllComplaint")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<List<ComplaintView>> getAllComplaint()   {
       return new ResponseEntity<>(this.complaintService.getAllComplaints(), HttpStatus.OK);
    }

}
