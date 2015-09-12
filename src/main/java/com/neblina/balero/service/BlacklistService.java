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
import java.util.List;

@Service
public class BlacklistService {

    private final Logger log = LoggerFactory.getLogger(BlacklistService.class);

    @Autowired
    private BlacklistRepository blacklistRepository;

    public void addIpToBlacklist(String ip) {
        log.debug("Adding user to blacklist...");
        try {
            Blacklist blacklist = blacklistRepository.findOneByIp(ip);
            if(blacklist == null) {
                throw new Exception();
            }
            blacklist.setAttemps(blacklist.getAttemps()+1);
            log.debug("Attemps: " + blacklist.getAttemps());
            if(blacklist.getAttemps() > 7) {
                blacklist.setTimer(60000 * 5);
            }
            blacklistRepository.save(blacklist);
        } catch (Exception e) {
            Blacklist blacklist = new Blacklist();
            blacklist.setIp(ip);
            blacklist.setAttemps(1);
            blacklist.setTimer(0);
            blacklistRepository.save(blacklist);
        }
    }

    public void deleteUserFromBlacklist(String ip) {
        log.debug("Deleting Banned IP...");
        Blacklist blacklist = blacklistRepository.findOneByIp(ip);
        blacklist.setIp(ip);
        blacklistRepository.delete(blacklist);
    }

    public void updateTimer(String ip) {
        log.debug("Updating timer to IP's List...");
        Blacklist blacklist = blacklistRepository.findOneByIp(ip);
        try {
            if(blacklist.getIp() == null) {
                throw new Exception("User Is Not On The Blacklist. Nothing To Do.");
            }
            if(blacklist.getTimer() <= 0) {
                deleteUserFromBlacklist(ip);
            }
            if(blacklist.getTimer() > 0) {
                blacklist.setTimer(blacklist.getTimer()-60000);
                log.debug(blacklist.getIp() + " " + blacklist.getTimer() + " Remaining...");
                blacklistRepository.save(blacklist);
            }
        } catch (Exception e) {
            log.debug("Error: " + e.getMessage());
        }
    }

    public List<Blacklist> getAllIps() {
        List<Blacklist> ips = blacklistRepository.findAll();
        return ips;
    }

    public String getUserIp() throws UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        return ip.getHostAddress();
    }

    /**
     * Check if User's IP is in blacklist.
     * @return true if its in blacklist, else return false its not.
     * @throws UnknownHostException
     */
    public boolean isIpBanned() throws UnknownHostException {
        boolean result = false;
        Blacklist blacklist = blacklistRepository.findOneByIp(getUserIp());
        try {
            if(blacklist == null) throw new Exception("User's IP not found in the blacklist.");
            if (getUserIp().equals(blacklist.getIp()) && blacklist.getAttemps() > 7) {
                result = true; // Banned
            } else {
                result = false; // Granted
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return result;
    }

}
