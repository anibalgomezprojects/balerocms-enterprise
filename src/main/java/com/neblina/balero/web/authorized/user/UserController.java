/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized.user;

import com.neblina.balero.domain.User;
import com.neblina.balero.service.UserService;
import com.neblina.balero.service.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;
import java.util.Locale;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LogManager.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(Model model,
                               Locale locale,
                               @ModelAttribute(value="user") @Valid User user, BindingResult bindingResultUser) {
        if(bindingResultUser.hasErrors()) {
            //model.addAttribute("user", user);
        }
        String lang = locale.getLanguage();
        model.addAttribute("settings", settingRepository.findOneByCode(locale.getLanguage()));
        model.addAttribute("pages", pageRepository.findAllByCode(lang));
        model.addAttribute("properties", propertyRepository.findOneById(1L));
        return "silbato/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(Model model,
                           Locale locale,
                           @RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "passwordVerify") String passwordVerify,
                           @RequestParam(value = "firstName") String firstName,
                           @RequestParam(value = "lastName") String lastName,
                           @RequestParam(value = "email") String email,
                           @ModelAttribute(value="user") @Valid User user, BindingResult bindingResultUser) {
        log.debug("Creating user... " + username);
        model.addAttribute("properties", propertyRepository.findOneById(1L));
        if(!password.equals(passwordVerify)) {
            bindingResultUser.rejectValue("passwordVerify", "error.passwordVerify", "Do not match.");
        }
        if(username.contains("admin")) {
            bindingResultUser.rejectValue("username", "error.username", "You can't use this username.");
        }
        if(bindingResultUser.hasErrors()) {
            model.addAttribute("settings", settingRepository.findOneByCode(locale.getLanguage()));
            return "silbato/register";
        }
        User usr = userService.findOneByUsername(username);
        if(usr != null) {
            log.debug("Username value: " + usr.getUsername());
        }
        if(usr == null) {
            log.debug("Username NOT found");
            User usr2 = userService.findOneByEmail(email);
            if(usr2 != null) { // email found
                log.debug("Email already exists. Register with this email.");
                userService.deleteUserEmail(usr2.getId()); // Clean
                userService.createUserAccount(username, password, passwordVerify, firstName, lastName, usr2.getEmail(), true, "ROLE_USER", "user"); // Add
            } else {
                userService.createUserAccount(username, password, passwordVerify, firstName, lastName, email, true, "ROLE_USER", "user");
            }
        }
        return "redirect:/login";
    }


    @Secured("ROLE_USER")
    @RequestMapping(value = {"", "/", "/dashboard"} )
    public String dashboardUser(Model model) {
        String username = userService.getMyUsername();
        model.addAttribute("users", userService.findOneByUsername(username));
        model.addAttribute("posts", blogRepository.findAllByAuthor(username));
        model.addAttribute("url", "user");
        return "authorized/dashboard";
    }

    @Secured("ROLE_USER")
    @RequestMapping("/profile")
    public String profileGet(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        log.debug("Username: " + username);
        User user = userService.findOneByUsername(username);
        model.addAttribute("user", user);
        return "authorized/profile";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String profilePost(Model model,
                              @RequestParam("firstName") String firstName,
                              @RequestParam("lastName") String lastName,
                              @RequestParam("email") String email) {
        log.debug("POST /user/profile");
        String username = userService.getMyUsername();
        model.addAttribute("success", 1);
        User user = userService.findOneByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("url", "user");
        userService.saveUserProfile(firstName, lastName, email);
        return "authorized/profile";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String passwordPost(Model model,
                               @RequestParam("newPassword") String newPassword) {
        log.debug("POST /user/password");
        String username = userService.getMyUsername();
        model.addAttribute("success", 1);
        User user = userService.findOneByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("url", "user");
        userService.changeUserPassword(newPassword);
        return "authorized/profile";
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.GET)
    public String unsubscribeUserGET(@RequestParam("unsubscribe") String email) {
        log.debug("GET Request -> /subscribe?unsubscribe=" + email);
        Base64.Decoder decoder = Base64.getDecoder();
        userService.cancelSubscribedStatusByEmail(new String(decoder.decode(email)));
        return "subscribe";
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public String addUserToMailList(@RequestParam("firstname") String firstname,
                                    @RequestParam("email") String email) {
        log.debug("POST Request -> /user/subscribe {}");
        try {
            userService.createUserAccount("temp", "temp",
                    "temp", firstname, "temp", email, true, "ROLE_USER", "user");
        } catch (Exception e) {
            log.debug("Error: " + e.getMessage());
        }
        return "subscribe";
    }

}