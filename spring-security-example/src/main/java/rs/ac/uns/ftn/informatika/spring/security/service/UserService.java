package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import  rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.view.RegisterPharmacyAdminView;
import rs.ac.uns.ftn.informatika.spring.security.view.RegisterView;
import  rs.ac.uns.ftn.informatika.spring.security.view.UserRegisterView;

public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findAll ();
	User save(UserRegisterView userRequest);
    User saveUserAdminSystem(RegisterView userRequest);
    User saveUserSupplier(RegisterView userRequest);
    User saveUserPharmacyAdmin(RegisterPharmacyAdminView userRequest);
    User saveUserDermatologist(RegisterView userRequest);
    Boolean changePassword(String email, String password);
    void editPersonalData(UserRegisterView u);
	User findByEmailAndPassword(String email, String password) throws UsernameNotFoundException;
}
