package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.spring.security.model.LoyaltyScale;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.Patient;
import rs.ac.uns.ftn.informatika.spring.security.model.PatientCategory;
import rs.ac.uns.ftn.informatika.spring.security.repository.LoyaltyScaleRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.PatientRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.LoyaltyScaleService;
import rs.ac.uns.ftn.informatika.spring.security.service.PatientService;
import rs.ac.uns.ftn.informatika.spring.security.view.LoyaltyScaleView;

import java.util.List;

@Service
public class LoyaltyScaleServiceImpl implements LoyaltyScaleService {
    @Autowired
    private LoyaltyScaleRepository loyaltyScaleRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<LoyaltyScale> findAll() {
        List<LoyaltyScale> result = loyaltyScaleRepository.findAll();
        return result;
    }

    @Override
    public LoyaltyScale save(LoyaltyScale loyaltyScale) {
        return null;
    }

    @Override
    public void editLoyalty(LoyaltyScaleView loyaltyScaleView) {
        LoyaltyScale loyaltyScaleS = this.loyaltyScaleRepository.findByCategory(PatientCategory.SILVER);
        loyaltyScaleS.setNeededPoints(Integer.parseInt( loyaltyScaleView.getSilverNeededPoints()));
        loyaltyScaleS.setDisccount(Integer.parseInt(loyaltyScaleView.getSilverDiscount()));
        this.loyaltyScaleRepository.save(loyaltyScaleS);
        LoyaltyScale loyaltyScaleG = this.loyaltyScaleRepository.findByCategory(PatientCategory.GOLD);
        loyaltyScaleG.setNeededPoints(Integer.parseInt( loyaltyScaleView.getGoldNeededPoints()));
        loyaltyScaleG.setDisccount(Integer.parseInt(loyaltyScaleView.getGoldDiscount()));
        this.loyaltyScaleRepository.save(loyaltyScaleG);
    }

    @Override
    public void updateCathegoryOfPatients(){
        LoyaltyScale loyaltyScaleSilver = this.loyaltyScaleRepository.findByCategory(PatientCategory.SILVER);
        LoyaltyScale loyaltyScaleGold = this.loyaltyScaleRepository.findByCategory(PatientCategory.GOLD);

        List<Patient> patients= this.patientRepository.findAll();
        for(Patient p:patients){
            if(p.getPoints() >= loyaltyScaleGold.getNeededPoints()) {
                p.setCategory(PatientCategory.GOLD);
                this.patientRepository.save(p);
            }else if( p.getPoints() >= loyaltyScaleSilver.getNeededPoints()){
                p.setCategory(PatientCategory.SILVER);
                this.patientRepository.save(p);
            }else{
                p.setCategory(PatientCategory.REGULAR);
                this.patientRepository.save(p);
            }
        }
    }

    @Override
    public void updateCathegoryOfPatient(Patient p) {
        LoyaltyScale loyaltyScaleSilver = this.loyaltyScaleRepository.findByCategory(PatientCategory.SILVER);
        LoyaltyScale loyaltyScaleGold = this.loyaltyScaleRepository.findByCategory(PatientCategory.GOLD);

        if(p.getPoints() >= loyaltyScaleGold.getNeededPoints()) {
            p.setCategory(PatientCategory.GOLD);
            this.patientRepository.save(p);
        }else if( p.getPoints() >= loyaltyScaleSilver.getNeededPoints()){
            p.setCategory(PatientCategory.SILVER);
            this.patientRepository.save(p);
        }else{
            p.setCategory(PatientCategory.REGULAR);
            this.patientRepository.save(p);
        }
    }
}
