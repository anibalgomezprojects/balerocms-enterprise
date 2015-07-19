/**
 * Silbato Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.web;

import com.neblina.balero.service.repository.BlockRepository;
import com.neblina.balero.service.repository.PageRepository;
import com.neblina.balero.service.repository.SettingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
public class IndexController {

    private static final Logger log = LogManager.getLogger(TestController.class.getName());

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private PageRepository pageRepository;

    @RequestMapping("/")
    String home(Model model) {
        model.addAttribute("settings", settingRepository.findAll());
        model.addAttribute("blocks", blockRepository.findAll());
        model.addAttribute("pages", pageRepository.findAll());
        return "silbato/index";
    }

    @RequestMapping("/logout")
    String logout() {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return "redirect:/login";
    }

    @RequestMapping("/offline")
    String offline(Model model) {
        model.addAttribute("settings", settingRepository.findAll());
        return "offline";
    }

    @RequestMapping("/banned")
    String banned(Model model, ServletRequest req) throws UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        HttpServletRequest request = (HttpServletRequest) req;
        model.addAttribute("ip", ip);
        model.addAttribute("info", request.getRemoteAddr());
        return "banned";
    }

}
