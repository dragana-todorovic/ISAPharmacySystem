package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.spring.security.model.LoyaltyScale;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.PatientCategory;
import rs.ac.uns.ftn.informatika.spring.security.repository.LoyaltyScaleRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.LoyaltyScaleService;
import rs.ac.uns.ftn.informatika.spring.security.view.LoyaltyScaleView;

import java.util.List;

@Service
public class LoyaltyScaleServiceImpl implements LoyaltyScaleService {
    @Autowired
    private LoyaltyScaleRepository loyaltyScaleRepository;

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

}
