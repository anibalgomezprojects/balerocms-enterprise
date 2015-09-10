/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized;

import com.neblina.balero.domain.User;
import com.neblina.balero.service.UserService;
import com.neblina.balero.service.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class DashboardUserController {

    private static final Logger log = LogManager.getLogger(DashboardUserController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"", "/"} )
    public String rootIndex() {
        return "redirect:/user/dashboard";
    }

    @Secured("ROLE_USER")
    @RequestMapping("/dashboard")
    public String dashboardUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        model.addAttribute("users", userRepository.findOneByUsername(username));
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

    @Secured("ROLE_USER")
    @RequestMapping(value = "/mailing-list/subscribe/{id}", method = RequestMethod.GET)
    public String updateMailingListSubscribedGet(Model model,
                                                 @PathVariable("id") Long id) {
        userService.updateSubscribedStatus(id);
        return "redirect:/user/dashboard/";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String passwordPost(Model model,
                               @RequestParam("newPassword") String newPassword) {
        log.debug("POST /user/password");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        model.addAttribute("success", 1);
        User user = userRepository.findOneByUsername(username);
        model.addAttribute("user", user);
        userService.changeUserPassword(newPassword);
        return "authorized/profile";
    }

}