/**
 * Silbato Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.web;

import com.neblina.balero.domain.Mail;
import com.neblina.balero.domain.User;
import com.neblina.balero.service.UserService;
import com.neblina.balero.service.repository.SettingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LogManager.getLogger(TestController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private SettingRepository settingRepository;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(Model model,
                               @ModelAttribute(value="user") @Valid User user, BindingResult bindingResultUser,
                               @ModelAttribute(value="mail") @Valid Mail mail, BindingResult bindingResultMail) {
        if(bindingResultUser.hasErrors() || bindingResultMail.hasErrors()) {
            //model.addAttribute("user", user);
        }
        model.addAttribute("settings", settingRepository.findAll());
        return "silbato/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(Model model,
                           @RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "passwordVerify") String passwordVerify,
                           @RequestParam(value = "firstName") String firstName,
                           @RequestParam(value = "lastName") String lastName,
                           @RequestParam(value = "address") String address,
                           @ModelAttribute(value="user") @Valid User user, BindingResult bindingResultUser,
                           @ModelAttribute(value="mail") @Valid Mail mail, BindingResult bindingResultMail) {
        log.debug("Creating user... " + username);
        if(!password.equals(passwordVerify)) {
            bindingResultUser.rejectValue("passwordVerify", "error.passwordVerify", "Do not match.");
        }
        if(bindingResultUser.hasErrors() || bindingResultMail.hasErrors()) {
            model.addAttribute("settings", settingRepository.findAll());
            return "silbato/register";
        }
        List<User> userArray = userService.getUserByUsername("demo");
        if(userArray.isEmpty()) {
            userService.createUserAccount(username, password, passwordVerify, firstName, lastName, address, "USER");
        }
        if(!userArray.isEmpty()) {
            log.debug("User is already exists!");
        }
        return "redirect:/login";
    }

}
