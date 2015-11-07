/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.service;

import com.neblina.balero.domain.User;
import com.neblina.balero.service.impl.CustomUserDetailsManager;
import com.neblina.balero.service.repository.UserRepository;
import com.neblina.balero.util.PasswordGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LogManager.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsManager customUserDetailsManager;

    public void createUserAccount(String userName, String password, String passwordVerify, String firstName, String lastName,
                                  String email, boolean subscribed, String roles, String type) {
        PasswordGenerator pwd = new PasswordGenerator();
        User user = new User();
        user.setUsername(userName);
        user.setPassword(pwd.generatePassword(password));
        user.setPasswordVerify(pwd.generatePassword(passwordVerify));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setSubscribed(subscribed);
        user.setRoles(roles);
        user.setType(type);
        userRepository.save(user);
        try {
            if(!userName.equals("temp") && !password.equals("temp")) {
                log.debug("Creating user '" + userName + "' with User id: " + user.getId() + " and Email: " + email);
                //inMemoryUserDetailsManager.createUser(new org.springframework.security.core.userdetails.User(userName, pwd.generatePassword(password), AuthorityUtils.createAuthorityList("ROLE_USER")));
                customUserDetailsManager.loadUserByUsername(userName);
            }
        } catch (Exception e) {
            log.debug("Error: " + e.getMessage());
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
        user.setSubscribed(user.getSubscribed());
        userRepository.save(user);
    }

    public void saveUserProfile(String firstName,
                                 String lastName,
                                 String email) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        User user = userRepository.findOneByUsername(username);
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setPasswordVerify(user.getPasswordVerify());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setSubscribed(user.getSubscribed());
        userRepository.save(user);
    }

    public void changeAdminPassword(String newPassword) {
        User user = userRepository.findOneByUsername("admin");
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        user.setPassword(passwordGenerator.generatePassword(newPassword));
        user.setPasswordVerify(passwordGenerator.generatePassword(newPassword));
        userRepository.save(user);
        //inMemoryUserDetailsManager.changePassword(null, passwordGenerator.generatePassword(newPassword));
        customUserDetailsManager.changePassword(null, passwordGenerator.generatePassword(newPassword));
        log.debug("Changing password for admin...");
    }

    public void changeUserPassword(String newPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        User user = userRepository.findOneByUsername(username);
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        user.setPassword(passwordGenerator.generatePassword(newPassword));
        user.setPasswordVerify(passwordGenerator.generatePassword(newPassword));
        userRepository.save(user);
        customUserDetailsManager.changePassword(null, passwordGenerator.generatePassword(newPassword));
        log.debug("Changing password for admin...");
    }

    /**
     * @deprecated
     */
    public void updateUserEmail(Long id, String email) {
        User user = userRepository.findOneById(id);
        user.setEmail(email);
        userRepository.save(user);
        log.debug("Updating user's email.");
    }

    public void updateUserInfo(Long id, String firstName, String lastName, String email) {
        User user = userRepository.findOneById(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        userRepository.save(user);
        log.debug("Updating user's email.");
    }

    public void deleteUserEmail(Long id) {
        User user = userRepository.findOneById(id);
        user.setId(id);
        userRepository.delete(user);
        log.debug("Deleting user's email.");
    }

    public void updateSubscribedStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        User user = userRepository.findOneByUsername(username);
        user.setSubscribed(!user.getSubscribed());
        userRepository.save(user);
        log.debug("Updating subscribed status to: " + !user.getSubscribed()
        + " for: " + user.getUsername());
    }

    public void updateSubscribedStatusByEmail(String email) {
        log.debug("Updating subscribed for: " + email);
        User user = userRepository.findOneByEmail(email);
        user.setSubscribed(!user.getSubscribed());
        userRepository.save(user);
    }

    public void cancelSubscribedStatusByEmail(String email) {
        log.debug("Cancelling subscription for: " + email);
        User user = userRepository.findOneByEmail(email);
        user.setSubscribed(false);
        userRepository.save(user);
    }

    public int getTotalUsers() {
        return userRepository.findAll().size();
    }

    public int getSubscribedUsers() {
        return userRepository.findAllBySubscribed(true).size();
    }

    public String getMyUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        return username;
    }

    public String getUserType() {
        User user = userRepository.findOneByUsername(getMyUsername());
        return user.getType();
    }

    public User findOneByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOneById(Long id) {
        return userRepository.findOneById(id);
    }

    public User findOneByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }
}
