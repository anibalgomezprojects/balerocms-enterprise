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
import com.neblina.balero.service.UserService;
import com.neblina.balero.service.repository.BlogRepository;
import com.neblina.balero.service.repository.CommentRepository;
import com.neblina.balero.service.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;


@Controller
@RequestMapping("/user/blog")
public class DashboardUserBlogController {

    private static final Logger log = LogManager.getLogger(DashboardUserBlogController.class.getName());

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Secured("ROLE_USER")
    @RequestMapping(value = {"", "/"} )
    public String blog(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        model.addAttribute("posts", blogRepository.findAllByAuthor(username));
        return "authorized/blog";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/{id}/{permalink}", method = RequestMethod.GET)
    public String blogEditGet(Model model,
                              @PathVariable("id") Long id,
                              @PathVariable("permalink") String permalink) {
        model.addAttribute("comments", commentRepository.findAllByPostPermalink(permalink));
        model.addAttribute("posts", blogRepository.findOneById(id));
        model.addAttribute("user", userService.getUserType());
        return "authorized/blog_edit";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String blogEditPost(Model model, @PathVariable("id") Long id,
                                    @RequestParam("bloname") String bloname,
                                    @RequestParam("title") String title,
                                    @RequestParam("introPost") String introPost,
                                    @RequestParam("fullPost") String fullPost,
                                    @RequestParam("code") String code,
                                    @RequestParam("permalink") String permalink
                               ) {
        blogService.savePost(
                id,
                bloname,
                title,
                introPost,
                fullPost,
                code,
                permalink
        );
        model.addAttribute("success", 1);
        model.addAttribute("posts", blogRepository.findOneById(id));
        return "authorized/blog_edit";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String blogEditGet(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        LocalDate today = LocalDate.now();
        model.addAttribute("date", today);
        model.addAttribute("user", userRepository.findOneByUsername(username));
        log.debug("Date: " + today);
        return "authorized/blog_new";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String blogNew(Model model,
                              @RequestParam("bloname") String bloname,
                              @RequestParam("title") String title,
                              @RequestParam("introPost") String introPost,
                              @RequestParam("fullPost") String fullPost,
                              @RequestParam("code") String code,
                              @RequestParam("permalink") String permalink,
                              @RequestParam("author") String author) {
        blogService.createPost(
                bloname,
                title,
                introPost,
                fullPost,
                code,
                permalink,
                author
        );
        return "redirect:/user/blog";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String pageDelete(Model model, @PathVariable("id") Long id) {
        blogService.deletePost(id);
        return "redirect:/user/blog";
    }

}
