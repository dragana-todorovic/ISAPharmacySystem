package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.spring.security.model.LoyaltyProgram;
import rs.ac.uns.ftn.informatika.spring.security.model.PatientCategory;
import rs.ac.uns.ftn.informatika.spring.security.repository.LoyaltyProgramRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.LoyaltyProgramService;
import rs.ac.uns.ftn.informatika.spring.security.service.LoyaltyScaleService;
import rs.ac.uns.ftn.informatika.spring.security.view.LoyaltyProgramView;
import rs.ac.uns.ftn.informatika.spring.security.view.LoyaltyScaleView;

import java.util.List;
import java.util.Optional;

@Service
public class LoyaltyProgramServiceImpl implements LoyaltyProgramService {
    @Autowired
    private LoyaltyProgramRepository loyaltyProgramRepository;

    @Override
    public List<LoyaltyProgram> findAll() {
        List<LoyaltyProgram> result = loyaltyProgramRepository.findAll();
        return result;
    }

    @Override
    public LoyaltyProgram save(LoyaltyProgram LoyaltyProgram) {
        return null;
    }

    @Override
    public void editLoyaltyProgram(LoyaltyProgramView loyaltyProgramView) {
        List<LoyaltyProgram> lp =this.loyaltyProgramRepository.findAll();
        for(LoyaltyProgram l : lp){
            l.setAdvisingPoints(Integer.parseInt(loyaltyProgramView.getAdvisingPoints()));
            l.setAppointmentPoints(Integer.parseInt(loyaltyProgramView.getAppointmentPoints()));
            this.loyaltyProgramRepository.save(l);
        }
    }


}