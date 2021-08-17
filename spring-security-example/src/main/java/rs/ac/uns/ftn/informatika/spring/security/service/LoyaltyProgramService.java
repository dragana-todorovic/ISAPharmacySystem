package rs.ac.uns.ftn.informatika.spring.security.service;

import rs.ac.uns.ftn.informatika.spring.security.model.LoyaltyProgram;
import rs.ac.uns.ftn.informatika.spring.security.view.LoyaltyProgramView;
import rs.ac.uns.ftn.informatika.spring.security.view.LoyaltyScaleView;

import java.util.List;

public interface LoyaltyProgramService {
    List<LoyaltyProgram> findAll();
    LoyaltyProgram save(LoyaltyProgram loyaltyProgram);
    void editLoyaltyProgram(LoyaltyProgramView loyaltyProgramView);
}
