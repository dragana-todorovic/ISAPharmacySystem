package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistComplaint;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyComplaint;

import java.util.List;

public interface DermatologistComplaintRepository extends JpaRepository<DermatologistComplaint, Long> {
    List<DermatologistComplaint> findAll();
}
