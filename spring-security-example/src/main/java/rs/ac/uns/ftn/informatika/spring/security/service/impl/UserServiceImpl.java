package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.Authority;
import rs.ac.uns.ftn.informatika.spring.security.model.User;
import rs.ac.uns.ftn.informatika.spring.security.view.RegisterPharmacyAdminView;
import rs.ac.uns.ftn.informatika.spring.security.view.RegisterView;
import rs.ac.uns.ftn.informatika.spring.security.view.UserRegisterView;
import rs.ac.uns.ftn.informatika.spring.security.repository.UserRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.AuthorityService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityService authService;

	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Usao u servis");
		User u = userRepository.findByUsername(username);
		return u;
	}
	@Override
	public User findByEmail(String username) throws UsernameNotFoundException {
		System.out.println("Usao u servis");
		User u = userRepository.findByEmail(username);
		return u;
	}
	@Override
	public User findByEmailAndPassword(String email,String password) throws UsernameNotFoundException {
		
		User u = userRepository.findByEmailAndPassword(email, passwordEncoder.encode(password));
		return u;
	}
	public User findById(Long id) throws AccessDeniedException {
		User u = userRepository.findById(id).orElseGet(null);
		return u;
	}

	public List<User> findAll() throws AccessDeniedException {
		List<User> result = userRepository.findAll();
		return result;
	}
	@Override
	public User saveUserAdminSystem(RegisterView userRequest) {
		User u = new User();
		u.setEmail(userRequest.getEmail());
		u.setUsername(userRequest.getEmail());
		u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		u.setFirstName(userRequest.getFirstName());
		u.setLastName(userRequest.getLastName());
		u.setCountry(userRequest.getCountry());
		u.setCity(userRequest.getCity());
		u.setAddress(userRequest.getAddress());
		u.setPhone(userRequest.getPhoneNumber());
		u.setEnabled(true);

		List<Authority> auth = authService.findByname("ROLE_ADMIN_SYSTEM");
		u.setAuthorities(auth);

		u = this.userRepository.save(u);
		return u;
	}

	@Override
	public User saveUserSupplier(RegisterView userRequest) {
		User u = new User();
		u.setEmail(userRequest.getEmail());
		u.setUsername(userRequest.getEmail());
		u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		u.setFirstName(userRequest.getFirstName());
		u.setLastName(userRequest.getLastName());
		u.setCountry(userRequest.getCountry());
		u.setCity(userRequest.getCity());
		u.setAddress(userRequest.getAddress());
		u.setPhone(userRequest.getPhoneNumber());
		u.setEnabled(true);

		List<Authority> auth = authService.findByname("ROLE_SUPPLIER");
		u.setAuthorities(auth);
		u = this.userRepository.save(u);
		return u;
	}

	@Override
	public User saveUserPharmacyAdmin(RegisterPharmacyAdminView userRequest) {
		User u = new User();
		u.setEmail(userRequest.getEmail());
		u.setUsername(userRequest.getEmail());
		u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		u.setFirstName(userRequest.getFirstName());
		u.setLastName(userRequest.getLastName());
		u.setCountry(userRequest.getCountry());
		u.setCity(userRequest.getCity());
		u.setAddress(userRequest.getAddress());
		u.setPhone(userRequest.getPhoneNumber());
		u.setEnabled(true);

		List<Authority> auth = authService.findByname("ROLE_ADMIN_PHARMACY");
		u.setAuthorities(auth);
		u = this.userRepository.save(u);
		return u;
	}

	@Override
	public User saveUserDermatologist(RegisterView userRequest) {
		User u = new User();
		u.setEmail(userRequest.getEmail());
		u.setUsername(userRequest.getEmail());
		u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		u.setFirstName(userRequest.getFirstName());
		u.setLastName(userRequest.getLastName());
		u.setCountry(userRequest.getCountry());
		u.setCity(userRequest.getCity());
		u.setAddress(userRequest.getAddress());
		u.setPhone(userRequest.getPhoneNumber());
		u.setEnabled(true);

		List<Authority> auth = authService.findByname("ROLE_DERMATOLOGIST");
		u.setAuthorities(auth);
		u = this.userRepository.save(u);
		return u;
	}

	@Override
	public User save(UserRegisterView userRequest) {	
		User u = new User();
		u.setEmail(userRequest.getEmail());
		u.setUsername(userRequest.getEmail());
		// pre nego sto postavimo lozinku u atribut hesiramo je
		u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
	//	u.setPassword(userRequest.getPassword());
		u.setFirstName(userRequest.getFirstname());
		u.setLastName(userRequest.getLastname());
		u.setCountry(userRequest.getCountry());
		u.setCity(userRequest.getCity());
		u.setAddress(userRequest.getAddress());
		u.setPhone(userRequest.getPhone());
		u.setEnabled(true);
		u.setLogged(true);

		List<Authority> auth = authService.findByname("ROLE_PATIENT");
		u.setAuthorities(auth);
		
		u = this.userRepository.save(u);

		return u;
		
	}
	@Override
	public Boolean changePassword(String email, String password) {
		User u = findByEmail(email);
		System.out.println("USAOOOOOOOOOOOOOOOOOOOOOO U CHANGEEEEE$$$$$$$$$$$$");
		System.out.println("USEEEEEEEEEEEEER" + u);
		if(u == null) {
			return false;
		}
		u.setLogged(true);
		u.setPassword(passwordEncoder.encode(password));
		this.userRepository.save(u);
		return true;
		
		
	}
	@Override
	public void editPersonalData(UserRegisterView u) {
		System.out.println(u.getEmail() + "userrrr");
		User user = findByEmail(u.getEmail());
		user.setFirstName(u.getFirstname());
		user.setLastName(u.getLastname());
		user.setAddress(u.getAddress());
		user.setCity(u.getCity());
		user.setCountry(u.getCountry());
		user.setPhone(u.getPhone());
		this.userRepository.save(user);
	}

}
