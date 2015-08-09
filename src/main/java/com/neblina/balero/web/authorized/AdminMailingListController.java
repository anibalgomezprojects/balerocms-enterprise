/**
 * Balero CMS v2 Project: Proyecto 100% Mexicano de código libre.
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/mailing-list")
public class AdminMailingListController {

    private static final Logger log = LogManager.getLogger(AdminMailingListController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"", "/"} )
    public String mailingList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "authorized/mailing_list";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String mailingListGet(Model model,
                                  @PathVariable("id") Long id) {
        model.addAttribute("users", userRepository.findOneById(id));
        return "authorized/mailing_list_edit";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String mailingListPost(Model model,
                                  @PathVariable("id") Long id,
                                  @RequestParam("email") String email) {
        model.addAttribute("success", 1);
        model.addAttribute("users", userRepository.findOneById(id));
        userService.updateUserEmail(id, email);
        return "authorized/mailing_list_edit";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteMailingListPost(Model model,
                                  @PathVariable("id") Long id) {
        model.addAttribute("success", 1);
        model.addAttribute("users", userRepository.findOneById(id));
        userService.deleteUserEmail(id);
        return "redirect:/admin/mailing-list/";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/subscribe/{id}", method = RequestMethod.GET)
    public String updateMailingListSubscribedGet(Model model,
                                                 @PathVariable("id") Long id,
                                                 @RequestParam("status") int status) {
        userService.updateSubscribedStatus(id, status);
        return "redirect:/admin/mailing-list/";
    }

}
