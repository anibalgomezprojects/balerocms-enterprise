/**
 * Balero CMS v2 Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized;

import com.neblina.balero.service.SettingService;
import com.neblina.balero.service.repository.SettingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LogManager.getLogger(AdminController.class.getName());

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private SettingService settingService;

    @RequestMapping(value = {"", "/"} )
    public String rootIndex() {
        return "redirect:/admin/dashboard";
    }

    @Secured("ROLE_ADMIN")
    //@PreAuthorize("true")
    @RequestMapping("/dashboard")
    public String dashboardIndex() {
        return "authorized/dashboard";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/settings")
    public String settings(Model model) {
        model.addAttribute("settings", settingRepository.findOneByCode("en_US"));
        return "authorized/settings";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public String saveSettings(Model model,
                               @RequestParam(value = "title") String title,
                               @RequestParam(value = "titleHeader") String titleHeader,
                               @RequestParam(value = "administratorEmail") String administratorEmail,
                               @RequestParam(value = "tags") String tags,
                               @RequestParam(value = "footer") String footer,
                               @RequestParam(value = "offline") int offline,
                               @RequestParam(value = "offlineMessage") String offlineMessage) {
        log.debug("Saving Settings... Offline value: " + offline);
        model.addAttribute("success", 1);
        model.addAttribute("settings", settingRepository.findOneByCode("en_US"));
        settingService.saveSettings(
                "en_US",
                title,
                titleHeader,
                administratorEmail,
                tags,
                footer,
                offline,
                offlineMessage

        );
        return "authorized/settings";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/languages")
    public String languages() {
        return "authorized/languages";
    }

}
