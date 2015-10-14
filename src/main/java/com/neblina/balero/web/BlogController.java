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
import com.neblina.balero.domain.Page;
import com.neblina.balero.service.CommentService;
import com.neblina.balero.service.repository.*;
import org.apache.catalina.filters.ExpiresFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private static final Logger log = LogManager.getLogger(BlogController.class.getName());

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PropertyRepository propertyRepository;

    @RequestMapping(value = "" )
    String blogIndex(Model model, Locale locale) {
        model.addAttribute("settings", settingRepository.findOneByCode(locale.getLanguage()));
        model.addAttribute("pages", pageRepository.findAllByCode(locale.getLanguage()));
        model.addAttribute("properties", propertyRepository.findOneById(1L));
        Pageable lastTen = new PageRequest(0, 10);
        //model.addAttribute("posts", blogRepository.findAllByCode(locale.getLanguage(), null));
        model.addAttribute("posts", blogRepository.findByStatusAndCode("success", locale.getLanguage(), null));
        model.addAttribute("lastTen", blogRepository.findByStatusAndCode("success", locale.getLanguage(), lastTen));
        return "silbato/blog";
    }

    @RequestMapping(value = "/{permalink}", method = RequestMethod.GET)
    String postIndex(Model model, @PathVariable("permalink") String permalink, Locale locale,
                     HttpServletResponse response,
                     @CookieValue(value = "commentCookie", defaultValue = "init") String commentCookie) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        model.addAttribute("settings", settingRepository.findOneByCode(locale.getLanguage()));
        model.addAttribute("pages", pageRepository.findAllByCode(locale.getLanguage()));
        model.addAttribute("comments", commentRepository.findAllByPostPermalink(permalink));
        model.addAttribute("properties", propertyRepository.findOneById(1L));
        model.addAttribute("username", username);
        if(!commentCookie.equals("commented")) {
            model.addAttribute("addComment", 1);
        }
        try {
            Blog blog = blogRepository.findOneByPermalink(permalink);
            if(blog.getPermalink() == null) {
                throw new Exception("Error 404");
            }
            blog.setHits(blog.getHits()+1);
            blogRepository.save(blog);
            model.addAttribute("posts", blogRepository.findOneByPermalink(permalink));
        } catch (Exception e) {
            model.addAttribute("error404", 1);
        }
        return "silbato/post";
    }

    @RequestMapping(value = "/{permalink}", method = RequestMethod.POST)
    String postAddComment(Model model, @PathVariable("permalink") String permalink,
                          @RequestParam("content") String content,
                          Locale locale,
                          HttpServletResponse response) {
        log.debug("Request method POST -> /blog/" + permalink);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
            Cookie userCookie = new Cookie("commentCookie", "commented");
            response.addCookie(userCookie);
            commentService.createComment(
                    content,
                    locale.getLanguage(),
                    username,
                    permalink
            );
        return "redirect:/blog/" + permalink;
    }

}