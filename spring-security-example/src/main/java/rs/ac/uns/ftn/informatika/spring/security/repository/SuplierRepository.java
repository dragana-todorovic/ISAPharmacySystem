
package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.spring.security.model.SuplierOffer;

import rs.ac.uns.ftn.informatika.spring.security.model.Suplier;

public interface SuplierRepository extends JpaRepository<Suplier, Long> {
}