/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized.admin;

import com.neblina.balero.domain.User;
import com.neblina.balero.service.BaleroService;
import com.neblina.balero.service.SettingService;
import com.neblina.balero.service.UserService;
import com.neblina.balero.service.repository.*;
import com.neblina.balero.util.MediaManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Locale;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LogManager.getLogger(AdminController.class.getName());

    @Autowired
    private SettingService settingService;

    @Autowired
    private UserService userService;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private BaleroService baleroService;

    @Secured("ROLE_ADMIN")
    //@PreAuthorize("true")
    @RequestMapping(value = {"", "/", "/dashboard" })
    public String dashboardIndex(Model model) {
        //model.asMap().clear();
        String username = userService.getMyUsername();
        model.addAttribute("users", userService.findOneByUsername(username));
        model.addAttribute("totalPages", pageRepository.findAll().size());
        model.addAttribute("totalUsers", userService.findAll().size());
        model.addAttribute("totalBlocks", blockRepository.findAll().size());
        model.addAttribute("totalPosts", blogRepository.findAll().size());
        model.addAttribute("totalComments", commentRepository.findAll().size());
        model.addAttribute("profiles", environment.getActiveProfiles()[0]);
        return "authorized/dashboard";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/settings")
    public String settings(Model model) throws IOException {
        MediaManager mediaManager = new MediaManager();
        model.addAttribute("version", baleroService.getVersion());
        model.addAttribute("templates", mediaManager.retrieveTemplates());
        return "authorized/settings";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public String saveSettings(Model model,
                               Locale locale,
                               @RequestParam(value = "title") String title,
                               @RequestParam(value = "titleHeader") String titleHeader,
                               // Properties
                               @RequestParam(value = "administratorEmail") String administratorEmail,
                               @RequestParam(value = "url") String url,
                               @RequestParam(value = "mainLanguage") String mainLanguage,
                               // Properties
                               @RequestParam(value = "tags") String tags,
                               @RequestParam(value = "footer") String footer,
                               @RequestParam(value = "offlineMessage") String offlineMessage,
                               @RequestParam(value = "template") String template) {
        model.addAttribute("success", 1);
        settingService.saveSettings(
                locale.getLanguage(),
                title,
                titleHeader,
                // Properties
                administratorEmail,
                url,
                mainLanguage,
                template,
                // Properties
                tags,
                footer,
                offlineMessage

        );
        return "redirect:/admin/settings";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/languages")
    public String languages() {
        return "authorized/languages";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/profile")
    public String profileGet(Model model) {
        String username = userService.getMyUsername();
        log.debug("Username: " + username);
        User user = userService.findOneByUsername(username);
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
        User user = userService.findOneByUsername("admin");
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
        User user = userService.findOneByUsername("admin");
        model.addAttribute("user", user);
        userService.changeAdminPassword(newPassword);
        return "authorized/profile";
    }

}
