/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized.admin;

import com.github.slugify.Slugify;
import com.neblina.balero.service.PageService;
import com.neblina.balero.service.PropertyService;
import com.neblina.balero.service.repository.PageRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/admin/page")
public class AdminPageController {

    private static final Logger log = LogManager.getLogger(AdminPageController.class.getName());

    @Autowired
    private PageService pageService;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PropertyService propertyService;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"", "/"} )
    public String page(Model model) {
        model.addAttribute("pages", pageRepository.findAll());
        return "authorized/page";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String pageEditGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("pages", pageRepository.findOneById(id));
        model.addAttribute("multiLang", propertyService.isMultiLanguage());
        return "authorized/page_edit";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String pageEditPost(Model model, @PathVariable("id") Long id,
                                   String name,
                                   String title,
                                   String content,
                                   String code,
                                   String permalink,
                                   String author) throws IOException {
        Slugify slg = new Slugify();
        String result = slg.slugify(permalink);
        pageService.savePage(
                id,
                name,
                title,
                content,
                code,
                result,
                author
        );
        model.addAttribute("success", 1);
        model.addAttribute("pages", pageRepository.findOneById(id));
        model.addAttribute("multiLang", propertyService.isMultiLanguage());
        return "authorized/page_edit";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String pageEditGet(Model model) {
        model.addAttribute("multiLang", propertyService.isMultiLanguage());
        model.addAttribute("mainLanguage", propertyService.getMainLanguage());
        return "authorized/page_new";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String pageNew(
                              @RequestParam("name") String name,
                              @RequestParam("title") String title,
                              @RequestParam("content") String content,
                              @RequestParam("code") String code,
                              @RequestParam("permalink") String permalink,
                              @RequestParam("author") String author) throws IOException {
        Slugify slg = new Slugify();
        String result = slg.slugify(permalink);
        pageService.createPage(
                name,
                title,
                content,
                code,
                result,
                author
        );
        return "redirect:/admin/page";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String pageDelete(
                             @PathVariable("id") Long id) {
        pageService.deletePage(id);
        return "redirect:/admin/page";
    }

}
