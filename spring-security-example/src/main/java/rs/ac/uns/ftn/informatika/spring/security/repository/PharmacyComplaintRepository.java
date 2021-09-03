package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import rs.ac.uns.ftn.informatika.spring.security.model.Medicine;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyComplaint;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

public interface PharmacyComplaintRepository extends JpaRepository<PharmacyComplaint, Long> {
    List<PharmacyComplaint> findAll();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from PharmacyComplaint p where p.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="0")})
    public PharmacyComplaint findPharmacyComplaintById(long id);
}
