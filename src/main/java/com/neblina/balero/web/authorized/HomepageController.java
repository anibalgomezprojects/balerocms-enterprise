/**
 * Balero CMS v2 Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized;

import com.neblina.balero.service.BlockService;
import com.neblina.balero.service.repository.BlockRepository;
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

@Controller
@RequestMapping("/admin/homepage")
public class HomepageController {

    private static final Logger log = LogManager.getLogger(HomepageController.class.getName());

    @Autowired
    private BlockService blockService;

    @Autowired
    private BlockRepository blockRepository;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"", "/"} )
    public String homepage(Model model) {
        model.addAttribute("blocks", blockRepository.findAll());
        return "authorized/homepage";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String homepageEditGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("blocks", blockRepository.findOneById(id));
        return "authorized/homepage_save";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String homepageEditPost(Model model, @PathVariable("id") Long id,
                                   String name,
                                   String content,
                                   String code) {
        blockService.saveBlock(
                id,
                name,
                content,
                code
        );
        model.addAttribute("success", 1);
        model.addAttribute("blocks", blockRepository.findOneById(id));
        return "authorized/homepage_save";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String homepageEditGet() {
        return "authorized/homepage_new";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String homepageNew(Model model,
                              @RequestParam("name") String name,
                              @RequestParam("content") String content,
                              @RequestParam("code") String code) {
        blockService.createBlock(
                name,
                content,
                code
        );
        model.addAttribute("success", 1);
        return "authorized/homepage_new";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String homepageDelete(Model model, @PathVariable("id") Long id) {
        blockService.deleteBlock(id);
        return "redirect:/admin/homepage";
    }

}
