/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized.admin;

import com.neblina.balero.service.BlacklistService;
import com.neblina.balero.service.repository.BlacklistRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/blacklist")
public class AdminBlacklistController {

    private static final Logger log = LogManager.getLogger(AdminBlacklistController.class.getName());

    @Autowired
    private BlacklistService blacklistService;

    @Autowired
    private BlacklistRepository blacklistRepository;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"", "/"} )
    public String blacklist(Model model) {
        model.addAttribute("blacklists", blacklistRepository.findAll());
        return "authorized/blacklist";
    }

}
