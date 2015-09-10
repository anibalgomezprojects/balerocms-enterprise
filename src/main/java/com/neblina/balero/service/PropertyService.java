/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.service;

import com.neblina.balero.domain.Property;
import com.neblina.balero.service.repository.PropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    private final Logger log = LoggerFactory.getLogger(PropertyService.class);

    @Autowired
    private PropertyRepository propertyRepository;

    public void saveProperties(String administratorEmail,
                             boolean offline) {
        Property properties = propertyRepository.findOneById(1L);
        properties.setAdministratorEmail(administratorEmail);
        properties.setOffline(offline);
        propertyRepository.save(properties);
    }

    public boolean getOfflineStatus() {
        Property properties = propertyRepository.findOneById(1L);
        return properties .getOffline();
    }

    public void setOfflineStatus(boolean offline) {
        Property properties = propertyRepository.findOneById(1L);
        properties.setOffline(offline);
        propertyRepository.save(properties);
    }

}
