package com.neblina.balero.service;

import com.neblina.balero.domain.User;
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

    public void createUserAccount(String userName, String password, String passwordVerify, String firstName, String lastName,
                                  String email, String roles) {
        PasswordGenerator pwd = new PasswordGenerator();
        User user = new User();
        user.setUsername(userName);
        user.setPassword(pwd.generatePassword(password));
        user.setPasswordVerify(pwd.generatePassword(passwordVerify));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setRoles(roles);
        userRepository.save(user);
        try {
            if(!userName.equals("temp") && !password.equals("temp")) {
                log.debug("Creating user '" + userName + "' with User id: " + user.getId() + " and Email: " + email);
                inMemoryUserDetailsManager.createUser(new org.springframework.security.core.userdetails.User(userName, pwd.generatePassword(password), AuthorityUtils.createAuthorityList("ROLE_USER")));
            }
        } catch (Exception e) {
            log.debug("inMemoryUserDetailsManager: " + e.getMessage());
        }
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findOneByUsername(username);
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }


    public void saveAdminProfile(String firstName,
                                 String lastName,
                                 String email) {
        User user = userRepository.findOneByUsername("admin");
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setPasswordVerify(user.getPasswordVerify());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        userRepository.save(user);
    }

    public void changeAdminPassword(String password, String passwordVerify) {
        User user = userRepository.findOneByUsername("admin");
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        user.setPassword(passwordGenerator.generatePassword(password));
        user.setPasswordVerify(passwordGenerator.generatePassword(passwordVerify));
        userRepository.save(user);
    }

    public int getTotalUsers() {
        return userRepository.findAll().size();
    }

}
