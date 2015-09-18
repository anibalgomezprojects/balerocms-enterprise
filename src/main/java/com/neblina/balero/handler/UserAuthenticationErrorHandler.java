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
import com.neblina.balero.service.repository.BlacklistRepository;
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

    @Autowired
    private BlacklistRepository blacklistRepository;

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
        log.debug("Updating timer to IP's List...");
        try {
            List<Blacklist> ips = blacklistRepository.findAll();
            for(Blacklist blacklist: ips) {
                blacklist.setTimer(blacklist.getTimer()-60000);
                log.debug("Remaining for: " + blacklist.getIp() +
                        " Time: " + blacklist.getTimer());
                blacklistRepository.save(blacklist);
                if(blacklist.getTimer() <= 0) {
                    blacklist.setIp(blacklist.getIp());
                    log.debug("Deleting ip: " + blacklist.getIp());
                    blacklistRepository.delete(blacklist);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}