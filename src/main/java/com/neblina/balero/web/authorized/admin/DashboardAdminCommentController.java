/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized.admin;

import com.neblina.balero.service.CommentService;
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


@Controller
@RequestMapping("/admin/comment")
public class DashboardAdminCommentController {

    private static final Logger log = LogManager.getLogger(DashboardAdminCommentController.class.getName());

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"", "/"} )
    public String comment() {
        return "redirect:/admin/blog/";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String commentEditGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("comments", commentRepository.findOneById(id));
        return "authorized/comment_edit";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String commentEditPost(Model model, @PathVariable("id") Long id,
                                   String content
                               ) {
        commentService.saveComment(
                id,
                content
        );
        model.addAttribute("success", 1);
        return "redirect:/admin/comment/" + id;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String commentDelete(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
        return "redirect:/admin/blog";
    }

}
