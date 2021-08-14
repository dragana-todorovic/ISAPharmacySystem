package rs.ac.uns.ftn.informatika.spring.security.service;

import rs.ac.uns.ftn.informatika.spring.security.model.LoyaltyScale;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.view.LoyaltyScaleView;
import rs.ac.uns.ftn.informatika.spring.security.view.UserRegisterView;

import java.util.List;

public interface LoyaltyScaleService {
    List<LoyaltyScale> findAll();
    LoyaltyScale save(LoyaltyScale loyaltyScale);
    void editLoyalty(LoyaltyScaleView loyaltyScaleView);
}
