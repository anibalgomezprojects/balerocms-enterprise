/**
 * Balero CMS v2 Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized;

import com.neblina.balero.service.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/mailing-list")
public class MailingListController {

    private static final Logger log = LogManager.getLogger(MailingListController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"", "/"} )
    public String mailingList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "authorized/mailing_list";
    }

}
