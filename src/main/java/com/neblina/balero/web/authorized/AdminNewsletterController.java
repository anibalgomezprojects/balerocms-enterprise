/**
 * Balero CMS v2 Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized;

import com.neblina.balero.domain.User;
import com.neblina.balero.service.EmailService;
import com.neblina.balero.service.UserService;
import com.neblina.balero.service.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/admin/newsletter")
public class AdminNewsletterController {

    private static final Logger log = LogManager.getLogger(AdminNewsletterController.class.getName());

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"", "/"} )
    public String homepage(Model model) {
        model.addAttribute("totalUsers", userService.getTotalUsers());
        return "authorized/newsletter";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public String newsletterPost(@RequestParam("subject") String subject,
                                 @RequestParam("messageBody") String messageBody,
                                 Model model, Locale locale) throws MessagingException {
        List<User> users = userRepository.findAll();
        for(User user: users) {
            log.debug("Sending Email To..." + user.getEmail());
            this.emailService.sendSimpleMail(user.getFirstName(), user.getEmail(), subject, messageBody, locale);
        }
        model.addAttribute("totalUsers", userService.getTotalUsers());
        model.addAttribute("success", 1);
        return "authorized/newsletter";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String listPost(@RequestParam("firstName") String firstName,
                           @RequestParam("email") String email,
                           Model model, Locale locale) {
        model.addAttribute("success", 1);
        userService.createUserAccount("temp", "temp", "temp", firstName, "temp", email, 1, "USER");
        return "authorized/newsletter";
    }

}
