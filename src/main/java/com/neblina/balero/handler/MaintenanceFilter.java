/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.handler;

import com.neblina.balero.service.BlacklistService;
import com.neblina.balero.service.PropertyService;
import com.neblina.balero.service.SettingService;
import com.neblina.balero.service.repository.PropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MaintenanceFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(MaintenanceFilter.class);

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private BlacklistService blacklistService;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        String url = request.getRequestURL().toString();
        if(propertyService.isOfflineStatus() == true && !url.contains("offline")) {
            if(blacklistService.isIpBanned() == false) {
                if(url.contains("css") || url.contains("bootstrap") || url.contains("js") || url.contains("font") ||
                        url.contains("images") || url.contains("admin") || url.contains("logout") || url.contains("login") ||
                        url.contains("error") || url.contains("mail/list")) {
                    log.debug("Resource file or url allowed: " + url);
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