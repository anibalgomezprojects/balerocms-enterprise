/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.service;

import com.neblina.balero.domain.Homepage;
import com.neblina.balero.domain.Settings;
import com.neblina.balero.service.repository.HomepageRepository;
import com.neblina.balero.service.repository.SettingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomepageService {

    private final Logger log = LoggerFactory.getLogger(HomepageService.class);

    @Autowired
    private HomepageRepository homepageRepository;

    public void saveBlock(Long id,
                          String name,
                          String content,
                          String code) {
        Homepage homepage = homepageRepository.findOneById(id);
        homepage.setName(name);
        homepage.setContent(content);
        homepage.setCode(code);
        homepageRepository.save(homepage);
    }

}
