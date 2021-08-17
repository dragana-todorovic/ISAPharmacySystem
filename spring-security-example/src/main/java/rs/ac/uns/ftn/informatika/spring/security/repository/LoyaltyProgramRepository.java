package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.spring.security.model.LoyaltyProgram;
import rs.ac.uns.ftn.informatika.spring.security.model.LoyaltyScale;
import rs.ac.uns.ftn.informatika.spring.security.model.PatientCategory;

import java.util.List;

public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram, Long> {
    List<LoyaltyProgram> findAll();
}
