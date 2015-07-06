package com.neblina.balero.service;

import com.neblina.balero.domain.Mail;
import com.neblina.balero.domain.User;
import com.neblina.balero.service.repository.MailRepository;
import com.neblina.balero.service.repository.UserRepository;
import com.neblina.balero.util.PasswordGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LogManager.getLogger(UserService.class.getName());

    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    public UserService(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailRepository mailRepository;


    public void createUserAccount(String userName, String password, String passwordVerify, String firstName, String lastName,
                                  String email, String roles) {
        PasswordGenerator pwd = new PasswordGenerator();
        Mail mail= new Mail();
        mail.setEmail(email);
        mailRepository.save(mail);
        User user = new User();
        log.debug("Creating user... " + userName + " id: " + mail.getEmailId());
        user.setUsername(userName);
        user.setPassword(pwd.generatePassword(password));
        user.setPasswordVerify(pwd.generatePassword(passwordVerify));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmailId(mail.getEmailId());
        user.setRoles(roles);
        userRepository.save(user);
        //inMemoryUserDetailsManager.createUser(new User("demo", "demo", new ArrayList<GrantedAuthority>()));
        //AuthorityUtils.createAuthorityList("ROLE_USER")
        //List<User> findUser = userRepository.findOneByUsername(userName);
        try {
            inMemoryUserDetailsManager.createUser(new org.springframework.security.core.userdetails.User(userName, pwd.generatePassword(password), AuthorityUtils.createAuthorityList("ROLE_USER")));
        } catch (Exception e) {
            log.debug("inMemoryUserDetailsManager: " + e.getMessage());
        }
    }

    public List<User> getUserByUsername(String username) {
        List<User> user = userRepository.findOneByUsername(username);
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

}
