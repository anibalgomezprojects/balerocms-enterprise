/**
 * Silbato Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.web;

import com.neblina.balero.service.EmailService;
import com.neblina.balero.service.repository.BlockRepository;
import com.neblina.balero.service.repository.MailRepository;
import com.neblina.balero.service.repository.SettingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
public class NewsletterController {

    private static final Logger log = LogManager.getLogger(NewsletterController.class.getName());

    @Autowired
    private MailRepository mailRepository;

    @RequestMapping(value = "/newsletter", method = RequestMethod.POST)
    String newsletterPost(Model model) {
        return "newsletter_thanks";
    }

}
