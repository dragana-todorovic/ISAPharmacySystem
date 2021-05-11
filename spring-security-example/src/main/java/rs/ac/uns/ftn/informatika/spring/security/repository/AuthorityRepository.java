package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.informatika.spring.security.model.Authority;
import rs.ac.uns.ftn.informatika.spring.security.model.User;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	Authority findByName(String name);

	@Query(nativeQuery = true, value = "select aa.name from users u\n" +
			"left join user_authority au on u.id =au.user_id\n" +
			"left join authority aa on aa.id = au.authority_id\n" +
			"where au.user_id= :user_id")
	String findAuthorityIdByUserId(@Param("user_id") Long user_id);
}
