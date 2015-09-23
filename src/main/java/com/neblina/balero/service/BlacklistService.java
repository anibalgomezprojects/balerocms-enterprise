/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.service;

import com.neblina.balero.domain.Blacklist;
import com.neblina.balero.service.repository.BlacklistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class BlacklistService {

    private final Logger log = LoggerFactory.getLogger(BlacklistService.class);

    @Autowired
    private BlacklistRepository blacklistRepository;

    public void addIpToBlacklist(String ip) {
        try {
            log.debug("Adding user to blacklist...: " + ip);
            Blacklist blacklist = blacklistRepository.findOneByIp(this.getUserIp());
            if(blacklist == null) { // not found
                log.debug("Usuario no encontrado en la lista negra. Agregando a..." + ip);
                Blacklist bannedUser = new Blacklist();
                bannedUser.setIp(ip);
                bannedUser.setAttemps(1);
                bannedUser.setTimer(300000); // 5 minutes
                blacklistRepository.save(bannedUser);
            }
            if(blacklist != null) {
                log.debug("Usuario encontrado en la lista negra. Intentos->" + blacklist.getAttemps());
                blacklist.setAttemps(blacklist.getAttemps() + 1);
                blacklist.setTimer(300000); // 5 minutes
                blacklistRepository.save(blacklist);
            }
        } catch (Exception e) {
            log.debug("Balero->addIpToBlacklist" + e.getMessage());
        }
    }

    public String getUserIp() throws UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        return ip.getHostAddress();
    }

    public boolean isIpBanned() throws UnknownHostException {
        boolean status = false;
        Blacklist blacklist = blacklistRepository.findOneByIp(this.getUserIp());
        if(blacklist == null) { // not found->not banned
            status = false;
        }
        if(blacklist != null) { // found->banned
            if(blacklist.getAttemps() > 5) {
                status = true;
            }
        }
        return status;
    }

}
