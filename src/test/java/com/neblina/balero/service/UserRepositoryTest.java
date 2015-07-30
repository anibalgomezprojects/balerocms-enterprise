/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.service;

import com.neblina.balero.Application;
import com.neblina.balero.domain.Mail;
import com.neblina.balero.domain.User;
import com.neblina.balero.service.repository.UserRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.constraints.AssertTrue;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class UserRepositoryTest extends TestCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Test
    public void createNewUsernameDemoAndVerifyIfIsItExists() {
        System.out.println("Creando Usuario Demo...");
        userService.createUserAccount("demo", "demo", "demo", "Pepito", "Perez", "demo@localhost.com", "ADMIN, USER");
        List<User> users = userService.getUserByUsername("demo");
        for(User user: users) {
            System.out.println("array: " + users);
        }
        assertThat(users, contains(
                allOf(
                        hasProperty("username", is("demo"))
                )
        ));
    }

    @Test
    public void printAllRegisteredUsers() {
        System.out.println("Getting Registered Users...");
        List<User> remoteUsers = userService.getAllUsers();
        int i = 0;
        for(User user: remoteUsers) {
            i++;
            System.out.println("User[" + i + "] " + user.getUsername());
        }
    }

    @Test
    public void printAllMailingistUsers() {
        int totalUsers = emailService.getTotalUsers();
        System.out.println("Getting Mailing List Users: " + totalUsers);
        assertThat(totalUsers, is(4));
    }

}

