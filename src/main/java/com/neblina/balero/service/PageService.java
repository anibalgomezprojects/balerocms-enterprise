/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.service;

import com.neblina.balero.domain.Page;
import com.neblina.balero.service.repository.PageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageService {

    private final Logger log = LoggerFactory.getLogger(PageService.class);

    @Autowired
    private PageRepository pageRepository;

    public void createPage(String name,
                           String title,
                           String content,
                           String code,
                           String permalink,
                           String author) {
        Page page = new Page();
        page.setName(name);
        page.setTitle(title);
        page.setContent(content);
        page.setCode(code);
        page.setPermalink(permalink);
        page.setAuthor(author);
        page.setHits(0);
        pageRepository.save(page);
    }

    public void savePage(Long id,
                         String name,
                         String title,
                         String content,
                         String code,
                         String permalink,
                         String author) {
        Page page = pageRepository.findOneById(id);
        page.setName(name);
        page.setTitle(title);
        page.setContent(content);
        page.setCode(code);
        page.setPermalink(permalink);
        page.setAuthor(author);
        page.setHits(page.getHits());
        pageRepository.save(page);
    }

    public void deletePage(Long id) {
        Page page = pageRepository.findOneById(id);
        pageRepository.delete(page);
    }

    public void setHits(Long id) {
        Page page = pageRepository.findOneById(id);
        page.setHits(page.getHits()+1);
        pageRepository.save(page);
        log.debug("hits: " + page.getHits());
    }

    public List<Page> findAll() {
        return pageRepository.findAll();
    }

    public Page findOneById(Long id) {
        return pageRepository.findOneById(id);
    }

}
