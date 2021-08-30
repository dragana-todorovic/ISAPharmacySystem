package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.informatika.spring.security.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername( String username );
    User findByEmail(String email);
    @Query(nativeQuery = true, value = "select * from users \n"  +
			"where users.email=?1 and users.password =?2")
    User findByEmailAndPassword(String email,String password);
}

