/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.web;

import com.neblina.balero.domain.Blog;
import com.neblina.balero.service.BlogService;
import com.neblina.balero.service.repository.BlogRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api")
public class APIController {

    private static final Logger log = LogManager.getLogger(APIController.class.getName());

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogService blogService;

    @RequestMapping("/blog")
    @ResponseBody
    @Transactional(readOnly = true)
    public List<Blog> blog(Model model, Locale locale) {
        log.debug("Call for API /blog");
        return blogRepository.findAllByCode(locale.getLanguage(), null);
    }

    @RequestMapping("/blog/{id}")
    @ResponseBody
    @Transactional(readOnly = true)
    public List<Blog> blogId(Locale locale, @PathVariable("id") Long id) {
        log.debug("Call for API /blog/" + id);
        blogService.setLikes(id);
        return blogRepository.findAllByCode(locale.getLanguage(), null);
    }

}
