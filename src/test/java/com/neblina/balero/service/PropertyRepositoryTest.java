/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.service;

import com.neblina.balero.Application;
import com.neblina.balero.config.TestConfig;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class, TestConfig.class}, loader = SpringApplicationContextLoader.class)
public class PropertyRepositoryTest extends TestCase {

    @Autowired
    private PropertyService propertyService;

    @Test
    public void checkIfOfflineStatusIsOff() {
        assertThat(propertyService.isOfflineStatus(), is(false));
        System.out.println("Offline value is: " + propertyService.isOfflineStatus());
    }

    @Test
    public void checkDefaultLanguageIsEnglish() {
        assertThat(propertyService.getMainLanguage(), is(propertyService.getMainLanguage()));
        System.out.println("Default language is: " + propertyService.getMainLanguage());
    }

}

