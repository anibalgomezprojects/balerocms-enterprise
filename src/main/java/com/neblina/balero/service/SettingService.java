/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.service;

import com.neblina.balero.domain.Property;
import com.neblina.balero.domain.Setting;
import com.neblina.balero.service.repository.PropertyRepository;
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

    @Autowired
    private PropertyRepository propertyRepository;

    public void saveSettings(String code,
                             String title,
                             String titleHeader,
                             // Properties
                             String administratorEmail,
                             String url,
                             String mainLanguage,
                             // Properties
                             String tags,
                             String footer,
                             String offlineMessage) {
        Setting settings = settingRepository.findOneByCode(code);
        Property properties = propertyRepository.findOneById(1L);
        settings.setCode(code);
        settings.setTitle(title);
        settings.setTitleHeader(titleHeader);
        // Properties
        properties.setAdministratorEmail(administratorEmail);
        properties.setUrl(url);
        properties.setMainLanguage(mainLanguage);
        // Properties
        settings.setTags(tags);
        settings.setFooter(footer);
        settings.setOfflineMessage(offlineMessage);
        settingRepository.save(settings);
        propertyRepository.save(properties);
    }

    public Setting findOneByCode(String code) {
        return settingRepository.findOneByCode(code);
    }

}
