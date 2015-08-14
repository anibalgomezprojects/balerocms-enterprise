/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized;

import com.neblina.balero.domain.User;
import com.neblina.balero.service.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class AdminUserController {

    private static final Logger log = LogManager.getLogger(AdminUserController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = {"", "/"} )
    public String rootIndex() {
        return "redirect:/user/dashboard";
    }

    @Secured("ROLE_USER")
    @RequestMapping("/dashboard")
    public String dashboardUser() {
        return "authorized/dashboard";
    }

    @Secured("ROLE_USER")
    @RequestMapping("/profile")
    public String profileGet(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        log.debug("Username: " + username);
        User user = userRepository.findOneByUsername(username);
        model.addAttribute("user", user);
        return "authorized/profile";
    }

}