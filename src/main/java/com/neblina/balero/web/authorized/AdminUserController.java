/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class AdminUserController {

    private static final Logger log = LogManager.getLogger(AdminUserController.class.getName());

    @RequestMapping(value = {"", "/"} )
    public String rootIndex() {
        return "redirect:/user/dashboard";
    }

    @Secured("ROLE_USER")
    @RequestMapping("/dashboard")
    public String dashboardUser() {
        return "authorized/dashboard";
    }

}