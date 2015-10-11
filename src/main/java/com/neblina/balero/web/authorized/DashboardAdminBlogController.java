/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized;

import com.neblina.balero.service.BlogService;
import com.neblina.balero.service.repository.BlogRepository;
import com.neblina.balero.service.repository.CommentRepository;
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

import java.time.LocalDate;


@Controller
@RequestMapping("/admin/blog")
public class DashboardAdminBlogController {

    private static final Logger log = LogManager.getLogger(DashboardAdminBlogController.class.getName());

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"", "/"} )
    public String blog(Model model) {
        model.addAttribute("posts", blogRepository.findAll());
        model.addAttribute("user", "admin");
        return "authorized/blog";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}/{permalink}", method = RequestMethod.GET)
    public String blogEditGet(Model model,
                              @PathVariable("id") Long id,
                              @PathVariable("permalink") String permalink) {
        model.addAttribute("comments", commentRepository.findAllByPostPermalink(permalink));
        model.addAttribute("posts", blogRepository.findOneById(id));
        model.addAttribute("user", "admin");
        return "authorized/blog_edit";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}/{permalink}", method = RequestMethod.POST)
    public String blogEditPost(Model model, @PathVariable("id") Long id,
                               @PathVariable("permalink") String urlPermalink,
                               @RequestParam("bloname") String bloname,
                               @RequestParam("title") String title,
                               @RequestParam("introPost") String introPost,
                               @RequestParam("fullPost") String fullPost,
                               @RequestParam("code") String code,
                               @RequestParam("permalink") String permalink,
                               @RequestParam("status") String status) {
        blogService.savePost(
                id,
                bloname,
                title,
                introPost,
                fullPost,
                code,
                permalink,
                status
        );
        model.addAttribute("success", 1);
        model.addAttribute("posts", blogRepository.findOneById(id));
        model.addAttribute("user", "admin");
        return "authorized/blog_edit";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String blogEditGet(Model model) {
        LocalDate today = LocalDate.now();
        model.addAttribute("date", today);
        log.debug("Date: " + today);
        return "authorized/blog_new";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String blogNew(Model model,
                              @RequestParam("bloname") String bloname,
                              @RequestParam("title") String title,
                              @RequestParam("introPost") String introPost,
                              @RequestParam("fullPost") String fullPost,
                              @RequestParam("code") String code,
                              @RequestParam("permalink") String permalink,
                              @RequestParam("author") String author,
                              @RequestParam("status") String status) {
        blogService.createPost(
                bloname,
                title,
                introPost,
                fullPost,
                code,
                permalink,
                author,
                status
        );
        return "redirect:/admin/blog";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String pageDelete(Model model, @PathVariable("id") Long id) {
        blogService.deletePost(id);
        return "redirect:/admin/blog";
    }

}
