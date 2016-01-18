/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.web;

import com.neblina.balero.service.PropertyService;
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

    private static final Logger log = LogManager.getLogger(IndexController.class.getName());

    @Autowired
    private PropertyService propertyService;

    @RequestMapping("/")
    String home() {
        // TODO: Need to be implemented in PopulateGlobalAttribute
        return propertyService.getTemplate() + "/index";
    }

    @RequestMapping("/logout")
    String logout() {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return "redirect:/login";
    }

    @RequestMapping("/offline")
    String offline() {
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
