/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized.admin;

import com.neblina.balero.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

    private static final Logger log = LogManager.getLogger(AdminUserController.class.getName());

    @Autowired
    private UserService userService;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "")
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "authorized/user";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/")
    public String userListRedirect() {
        return "redirect:/admin/user";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String userGet(Model model,
                                  @PathVariable("id") Long id) {
        model.addAttribute("users", userService.findOneById(id));
        return "authorized/user_edit";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String userPost(Model model,
                                  @PathVariable("id") Long id,
                                  @RequestParam("firstName") String firstName,
                                  @RequestParam("lastName") String lastName,
                                  @RequestParam("email") String email) {
        model.addAttribute("success", 1);
        model.addAttribute("users", userService.findOneById(id));
        userService.updateUserInfo(
                id,
                firstName,
                lastName,
                email
        );
        return "authorized/user_edit";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteUserGet(Model model,
                                  @PathVariable("id") Long id) {
        model.addAttribute("success", 1);
        model.addAttribute("users", userService.findOneById(id));
        userService.deleteUserEmail(id);
        return "redirect:/admin/user";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String userEmailListPost(@RequestParam("firstName") String firstName,
                                @RequestParam("email") String email,
                                Model model) {
        model.addAttribute("success", 1);
        userService.createUserAccount("temp", "temp", "temp", firstName, "temp", email, true, "ROLE_ANONYMOUS", "user");
        return "redirect:/admin/user";
    }

}
