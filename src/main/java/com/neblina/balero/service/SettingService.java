/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.service;

import com.neblina.balero.domain.Setting;
import com.neblina.balero.service.repository.SettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingService {

    private final Logger log = LoggerFactory.getLogger(SettingService.class);

    @Autowired
    private SettingRepository settingRepository;

    public void saveSettings(String code,
                             String title,
                             String titleHeader,
                             String administratorEmail,
                             String tags,
                             String footer) {
        Setting settings = settingRepository.findOneByCode(code);
        settings.setCode(code);
        settings.setTitle(title);
        settings.setTitleHeader(titleHeader);
        settings.setAdministratorEmail(administratorEmail);
        settings.setTags(tags);
        settings.setFooter(footer);
        settingRepository.save(settings);
    }

}
