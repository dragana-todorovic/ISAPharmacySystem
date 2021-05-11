package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.List;

import  rs.ac.uns.ftn.informatika.spring.security.model.User;
import  rs.ac.uns.ftn.informatika.spring.security.view.UserRegisterView;

public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findAll ();
	User save(UserRegisterView userRequest);
    User saveUserAdminSystem(UserRegisterView userRequest);
}
