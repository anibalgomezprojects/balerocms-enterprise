/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author Anibal Gomez <anibalgomez@icloud.com>
 * @copyright Copyright (C) 2016 (17/01/16) ) Neblina Software. Derechos reservados.
 * @license Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.handler;

import com.neblina.balero.service.PropertyService;
import com.neblina.balero.service.repository.BlockRepository;
import com.neblina.balero.service.repository.PageRepository;
import com.neblina.balero.service.repository.SettingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Locale;

@ControllerAdvice
public class PopulateGlobalAttribute {

    private static final Logger log = LogManager.getLogger(PopulateGlobalAttribute.class.getName());

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PropertyService propertyService;

    /**
     * Balero CMS Global Models
     *
     * Referers:
     * http://www.concretepage.com/spring/spring-mvc/spring-mvc-modelattribute-annotation-example
     * https://gerrydevstory.com/2014/03/06/using-handlerinterceptor-or-controlleradvice-to-make-spring-mvc-model-attribute-available-everywhere/
     * @param model
     * @param locale
     */
    @ModelAttribute
    public void addAttributes(Model model,
                              Locale locale) {
        String lang = locale.getLanguage();
        log.debug("Current Language: " + lang);
        model.addAttribute("settings", settingRepository.findOneByCode(lang));
        model.addAttribute("blocks", blockRepository.findAllByCode(lang));
        model.addAttribute("pages", pageRepository.findAllByCode(lang));
        model.addAttribute("properties", propertyService.findOneById(1L));
    }

}
