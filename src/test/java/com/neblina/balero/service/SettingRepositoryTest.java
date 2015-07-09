/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.service;

import com.neblina.balero.Application;
import com.neblina.balero.domain.Setting;
import com.neblina.balero.service.repository.SettingRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class SettingRepositoryTest extends TestCase {

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private SettingService settingService;

    @Test
    public void checkIfOfflineStatusIsOff() {
        List<Setting> settings = (List<Setting>) settingRepository.findAll();
        assertThat(settings.size(), is(1));
        assertThat(settings, contains(
                allOf(
                        hasProperty("id", is(1L)),
                        hasProperty("offline", is(0))
                )
        ));
        assertThat(settingService.getOfflineStatus("en_US"), is(0));
        System.out.println("Offline value is: " + settingService.getOfflineStatus("en_US"));
    }

}

