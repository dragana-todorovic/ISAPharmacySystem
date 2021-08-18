package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyComplaint;

import java.util.List;

public interface PharmacyComplaintRepository extends JpaRepository<PharmacyComplaint, Long> {
    List<PharmacyComplaint> findAll();
}
