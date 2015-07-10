/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.handler;

import com.neblina.balero.domain.Blacklist;
import com.neblina.balero.service.SettingService;
import com.neblina.balero.service.repository.BlacklistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

@Component
public class MaintenanceFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(MaintenanceFilter.class);

    @Autowired
    private SettingService settingService;

    @Autowired
    private BlacklistRepository blacklistRepository;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        log.debug("Loading Maintenance Filter...");
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        //Setting settings = settingRepository.findOneByCode("en_US");
        String url = request.getRequestURL().toString();
        InetAddress ip = InetAddress.getLocalHost();
        log.info("Interceptor: Pre-handle: " + url);
        log.debug("offline value: " + settingService.getOfflineStatus("en_US"));
        Blacklist blacklist = blacklistRepository.findOneByIp(ip.getHostAddress());
        if(settingService.getOfflineStatus("en_US") == 1) {
            if (url.contains("offline")) {
                log.info("I'm in offline page, do nothing.");
            } else if(url.contains("bootstrap")) {
                log.info("Bootstrap resource file, do nothing.");
            } else if(url.contains("css")) {
                log.info("Css resource file, do nothing.");
            } else if(url.contains("images")) {
                log.info("Images resource file, do nothing.");
            } else if(url.contains("js")) {
                log.info("Js resource file, do nothing.");
            } else if(url.contains("font")) {
                log.info("Images resource file, do nothing.");
            } else if(url.contains("admin")) {
                log.info("Admin url, do nothing.");
            } else if(url.contains("login")) {
                log.info("Login url, do nothing.");
            } else if(url.contains("logout")) {
                log.info("Logout url, do nothing.");
            } else if(url.contains("banned")) {
                log.info("Banned url, do nothing.");
            } else if(settingService.getOfflineStatus("en_US") == 1 && !url.contains("offline")) {
                if (blacklist != null) {
                    if (ip.getHostAddress().equals(blacklist.getIp()) && blacklist.getAttemps() > 7) {
                        log.debug("Banned, do nothing..");
                    }
                    if (!ip.getHostAddress().equals(blacklist.getIp())) {
                        log.debug("Redirecting to offline page.");
                        response.sendRedirect("/offline/");
                    }
                } else {
                    log.debug("Redirecting to offline page.");
                    response.sendRedirect("/offline/");
                }
            }
        }
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}