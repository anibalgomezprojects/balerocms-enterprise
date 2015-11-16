/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.service;

import com.neblina.balero.domain.Information;
import com.neblina.balero.service.repository.InformationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformationService {

    private final Logger log = LoggerFactory.getLogger(InformationService.class);

    @Autowired
    private InformationRepository informationRepository;

    @Scheduled(fixedRate = 3600000) // 1 Hour
    public void deleteOldRecords() {
        log.debug("Limpiando registro tracking...");
        List<Information> informations = informationRepository.findAll();
        for(Information information : informations) {
            information.setId(information.getId());
            informationRepository.delete(information);
        }
    }

}
