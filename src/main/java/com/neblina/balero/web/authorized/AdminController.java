/**
 * Balero CMS v2 Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized;

import com.neblina.balero.domain.Page;
import com.neblina.balero.domain.User;
import com.neblina.balero.service.SettingService;
import com.neblina.balero.service.UserService;
import com.neblina.balero.service.repository.BlockRepository;
import com.neblina.balero.service.repository.PageRepository;
import com.neblina.balero.service.repository.SettingRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private BlockRepository blockRepository;

    @RequestMapping(value = {"", "/"} )
    public String rootIndex() {
        return "redirect:/admin/dashboard";
    }

    @Secured("ROLE_ADMIN")
    //@PreAuthorize("true")
    @RequestMapping("/dashboard")
    public String dashboardIndex(Model model) {
        model.addAttribute("totalPages", pageRepository.findAll().size());
        model.addAttribute("totalUsers", userRepository.findAll().size());
        model.addAttribute("totalBlocks", blockRepository.findAll().size());
        log.debug("Total users: " + userRepository.findAll().size());
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

    @Secured("ROLE_ADMIN")
    @RequestMapping("/profile")
    public String profileGet(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        log.debug("Username: " + username);
        User user = userRepository.findOneByUsername(username);
        model.addAttribute("user", user);
        return "authorized/profile";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String profilePost(Model model,
                              @RequestParam("firstName") String firstName,
                              @RequestParam("lastName") String lastName,
                              @RequestParam("email") String email) {
        log.debug("POST /admin/profile");
        model.addAttribute("success", 1);
        User user = userRepository.findOneByUsername("admin");
        model.addAttribute("user", user);
        userService.saveAdminProfile(firstName, lastName, email);
        return "authorized/profile";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String passwordPost(Model model,
                               @RequestParam("newPassword") String newPassword) {
        log.debug("POST /admin/password");
        model.addAttribute("success", 1);
        User user = userRepository.findOneByUsername("admin");
        model.addAttribute("user", user);
        userService.changeAdminPassword(newPassword);
        return "authorized/profile";
    }

}
