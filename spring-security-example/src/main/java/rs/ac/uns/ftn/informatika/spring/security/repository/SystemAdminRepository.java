package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.spring.security.model.Suplier;
import rs.ac.uns.ftn.informatika.spring.security.model.SystemAdmin;

public interface SystemAdminRepository extends JpaRepository<SystemAdmin, Long> {
}
