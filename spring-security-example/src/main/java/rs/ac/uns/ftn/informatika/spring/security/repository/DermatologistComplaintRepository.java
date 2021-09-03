package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import rs.ac.uns.ftn.informatika.spring.security.model.DermatologistComplaint;
import rs.ac.uns.ftn.informatika.spring.security.model.PharmacyComplaint;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

public interface DermatologistComplaintRepository extends JpaRepository<DermatologistComplaint, Long> {
    List<DermatologistComplaint> findAll();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from DermatologistComplaint p where p.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="0")})
    public DermatologistComplaint findDermatologistComplaintById(long id);
}
