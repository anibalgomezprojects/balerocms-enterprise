/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.handler;

import com.neblina.balero.domain.Blacklist;
import com.neblina.balero.service.BlacklistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author Anibal Gomez
 * Based on:
 * progrnotes.blogspot.mx/2012/07/spring-security-authenification-failure.html
 */
@Component
public class UserAuthenticationErrorHandler implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final Logger log = LoggerFactory.getLogger(UserAuthenticationErrorHandler.class);

    @Autowired
    private BlacklistService blacklistService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            Object userName = event.getAuthentication().getPrincipal();
            Object credentials = event.getAuthentication().getCredentials();
            log.warn("Failed login using USERNAME " +
                    userName + " and PASSWORD " + credentials + " from " + ip.getHostAddress());
            blacklistService.addIpToBlacklist(ip.getHostAddress());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 60000)
    public void blacklistChecker() {
        try {
            List<Blacklist> ips = blacklistService.getAllIps();
            for(Blacklist blacklist: ips) {
                blacklistService.updateTimer(blacklist.getIp());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}