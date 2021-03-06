package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.security.model.*;
import rs.ac.uns.ftn.informatika.spring.security.repository.PharmacyAdminRepository;
import rs.ac.uns.ftn.informatika.spring.security.repository.UserRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.AuthorityService;
import rs.ac.uns.ftn.informatika.spring.security.service.PharmacyAdminService;
import rs.ac.uns.ftn.informatika.spring.security.service.UserService;
import rs.ac.uns.ftn.informatika.spring.security.view.ActionAndBenefitDTO;
import rs.ac.uns.ftn.informatika.spring.security.view.UserRegisterView;

@Service
public class PharmacyAdminServiceImpl implements PharmacyAdminService {

	@Autowired
	private PharmacyAdminRepository pharmacyAdminRepository;


	@Override
	public PharmacyAdmin findPharmacyAdminByUser(User user) throws UsernameNotFoundException {
		List<PharmacyAdmin> getAll = pharmacyAdminRepository.findAll();
		for(PharmacyAdmin p : getAll) {
			System.out.println("###########################" + p.getUser().getId());
			if(p.getUser().getId().equals(user.getId())) {
				return p;
			}
		}
		return null;
	}

	@Override
	public PharmacyAdmin save(PharmacyAdmin pharmacyAdmin) {
		PharmacyAdmin pharmacyAdmin1 = this.pharmacyAdminRepository.save(pharmacyAdmin);
		return pharmacyAdmin1;
	}


}