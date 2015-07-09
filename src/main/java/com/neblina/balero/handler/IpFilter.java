/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.handler;

import com.neblina.balero.domain.Blacklist;
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
        //response.setHeader("Access-Control-Allow-Origin", "*");
        //response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        //response.setHeader("Access-Control-Max-Age", "3600");
        //response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        log.debug(request.getRequestURL() + ": " + ip.getHostAddress() + ":" + request.getRemoteAddr());
        List<Blacklist> blacklists = blacklistRepository.findAll();
        for(Blacklist blacklist : blacklists) {
            log.debug("User IP: " + blacklist.getIp());
            if(ip.getHostAddress().equals(blacklist.getIp()) && blacklist.getAttemps() > 7) {
                if(!url.contains("banned")) {
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