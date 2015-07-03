/**
 * Balero CMS v2 Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized;

import com.neblina.balero.domain.Settings;
import com.neblina.balero.service.SettingsService;
import com.neblina.balero.service.repository.HomepageRepository;
import com.neblina.balero.service.repository.SettingsRepository;
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
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LogManager.getLogger(AdminController.class.getName());

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private HomepageRepository homepageRepository;

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
        model.addAttribute("settings", settingsRepository.findOneByCode("en_US"));
        return "authorized/settings";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public String saveSettings(Model model,
                               @RequestParam(value = "title") String title,
                               @RequestParam(value = "titleHeader") String titleHeader,
                               @RequestParam(value = "administratorEmail") String administratorEmail,
                               @RequestParam(value = "tags") String tags,
                               @RequestParam(value = "footer") String footer) {
        log.debug("Saving Settings...");
        model.addAttribute("success", 1);
        model.addAttribute("settings", settingsRepository.findOneByCode("en_US"));
        settingsService.saveSettings(
                "en_US",
                title,
                titleHeader,
                administratorEmail,
                tags,
                footer
        );
        return "authorized/settings";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/languages")
    public String languages() {
        return "authorized/languages";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/homepage")
    public String homepage(Model model) {
        model.addAttribute("homepage", homepageRepository.findAll());
        return "authorized/homepage";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/homepage/{id}", method = RequestMethod.GET)
    public String homepageEditGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("homepage", homepageRepository.findOneById(id));
        return "authorized/homepage_save";
    }

}
