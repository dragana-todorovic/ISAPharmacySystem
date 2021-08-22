package rs.ac.uns.ftn.informatika.spring.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.spring.security.model.Dermatologist;
import rs.ac.uns.ftn.informatika.spring.security.model.SystemAdmin;
import rs.ac.uns.ftn.informatika.spring.security.repository.SystemAdminRepository;
import rs.ac.uns.ftn.informatika.spring.security.service.SystemAdminService;

@Service
public class SystemAdminServiceImpl  implements SystemAdminService {
    @Autowired
    private SystemAdminRepository systemAdminRepository;
    @Override
    public SystemAdmin save(SystemAdmin systemAdmin) {
        SystemAdmin newSysAdmin = this.systemAdminRepository.save(systemAdmin);
        return newSysAdmin;
    }
}
