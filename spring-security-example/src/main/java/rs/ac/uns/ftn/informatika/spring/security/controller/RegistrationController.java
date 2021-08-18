package rs.ac.uns.ftn.informatika.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.spring.security.view.RegisterView;

import  rs.ac.uns.ftn.informatika.spring.security.service.UserService;
@RestController
@RequestMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/dermatologist")
    @PreAuthorize("hasRole('ADMIN_SYSTEM')")
    public ResponseEntity<String> addUser(@RequestBody RegisterView registerUserView) {
      /*  User existUser = this.userService.findByUsername(userRequest.getUsername());
        User existUserByEmail = this.userService.findByEmail(userRequest.getEmail());
        if (existUserByEmail != null) {
            //log.warn("User with this email already exists");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            //return null;
            //	throw new ResourceConflictException(userRequest.getId(), "Username already exists");
        }

        User user = this.userService.save(userRequest);
        HttpHeaders headers = new HttpHeaders();*/
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }
}
