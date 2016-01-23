/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author Anibal Gomez <anibalgomez@icloud.com>
 * @copyright Copyright (C) 2016 Neblina Software. Derechos reservados.
 * @license Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockServletContext;

import javax.servlet.ServletContext;

public class TestConfig {

    private static final Logger log = LogManager.getLogger(TestConfig.class.getName());

    /**
     * Bean To Bypassing Jawr Unit Testing
     * @author Anibal Gomez
     */
    @Bean
    public ServletContext servletContext() {
        log.debug("Initizalizing test Configurastion TestConfig");
        MockServletContext servletContext = new MockServletContext();
        return  servletContext;
    }

}
