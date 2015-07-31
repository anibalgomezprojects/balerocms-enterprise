/**
 * Silbato Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.web;

import com.neblina.balero.domain.User;
import com.neblina.balero.service.UserService;
import com.neblina.balero.service.repository.SettingRepository;
import com.neblina.balero.service.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LogManager.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SettingRepository settingRepository;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(Model model,
                               @ModelAttribute(value="user") @Valid User user, BindingResult bindingResultUser) {
        if(bindingResultUser.hasErrors()) {
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
                           @RequestParam(value = "email") String email,
                           @ModelAttribute(value="user") @Valid User user, BindingResult bindingResultUser) {
        log.debug("Creating user... " + username);
        if(!password.equals(passwordVerify)) {
            bindingResultUser.rejectValue("passwordVerify", "error.passwordVerify", "Do not match.");
        }
        if(bindingResultUser.hasErrors()) {
            model.addAttribute("settings", settingRepository.findAll());
            return "silbato/register";
        }
        User usr = userRepository.findOneByUsername(username);
        if(usr != null) {
            log.debug("Username value: " + usr.getUsername());
        }
        if(usr == null) {
            log.debug("Username NOT found");
            userService.createUserAccount(username, password, passwordVerify, firstName, lastName, email, "USER");
        }
        return "redirect:/login";
    }

}
