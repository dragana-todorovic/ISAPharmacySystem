package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.spring.security.model.LoyaltyScale;
import rs.ac.uns.ftn.informatika.spring.security.model.PatientCategory;
import rs.ac.uns.ftn.informatika.spring.security.model.User;

import java.util.List;

public interface LoyaltyScaleRepository extends JpaRepository<LoyaltyScale, Long> {
    List<LoyaltyScale> findAll();
    LoyaltyScale findByCategory(PatientCategory category);
}
