/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.web;

import com.neblina.balero.domain.Page;
import com.neblina.balero.service.PropertyService;
import com.neblina.balero.service.repository.PageRepository;
import com.neblina.balero.service.repository.SettingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
@RequestMapping("/page")
public class PageController {

    private static final Logger log = LogManager.getLogger(PageController.class.getName());

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PropertyService propertyService;

    @RequestMapping(value = "/{permalink}" )
    String pageIndex(Model model, @PathVariable("permalink") String permalink, Locale locale) {
        model.addAttribute("settings", settingRepository.findOneByCode(locale.getLanguage()));
        model.addAttribute("pages", pageRepository.findAllByCode(locale.getLanguage()));
        model.addAttribute("properties", propertyService.findOneById(1L));
        try {
            Page page = pageRepository.findOneByPermalink(permalink);
            if(page.getPermalink() == null) {
                throw new Exception("Error 404");
            }
            model.addAttribute("staticPages", pageRepository.findOneByPermalink(permalink));
        } catch (Exception e) {
            model.addAttribute("error404", 1);
        }
        return propertyService.getTemplate() + "/page";
    }

}
