package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacistComplaint;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyComplaint;

import java.util.List;

public interface PharmacistComplaintRepository extends JpaRepository<PharmacistComplaint, Long> {
    List<PharmacistComplaint> findAll();
}
