/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.handler;

import com.neblina.balero.domain.Blacklist;
import com.neblina.balero.domain.Setting;
import com.neblina.balero.service.SettingService;
import com.neblina.balero.service.repository.BlacklistRepository;
import com.neblina.balero.service.repository.SettingRepository;
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
public class IpFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(IpFilter.class);

    @Autowired
    private BlacklistRepository blacklistRepository;

    /**
     * @author Anibal Gomez <anibalgomez@icloud.com>
     * References: spring.io/guides/gs/rest-service-cors/
     * @param req
     * @param res
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        log.debug("Loading Filter...");
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        String url = request.getRequestURL().toString();
        InetAddress ip = InetAddress.getLocalHost();
        log.debug(request.getRequestURL() + ": " + ip.getHostAddress() + ":" + request.getRemoteAddr());
        Blacklist blacklist = blacklistRepository.findOneByIp(ip.getHostAddress());
        if(blacklist != null) {
            if (ip.getHostAddress().equals(blacklist.getIp()) && blacklist.getAttemps() > 7) {
                log.debug("User IP: " + blacklist.getIp());
                if (!url.contains("banned")) {
                    log.info("Redirecting to banned page.");
                    response.sendRedirect("/banned/");
                }
                //return; // die();
            }
        }
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}