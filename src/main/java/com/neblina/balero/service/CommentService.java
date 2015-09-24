/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.service;

import com.neblina.balero.domain.Comment;
import com.neblina.balero.service.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class CommentService {

    private final Logger log = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private CommentRepository commentRepository;

    public void createComment(String content,
                           String code,
                           String author,
                           String postPermalink) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCode(code);
        comment.setAuthor(author);
        LocalDate today = LocalDate.now();
        comment.setBlodate(today);
        comment.setPostPermalink(postPermalink);
        commentRepository.save(comment);
    }

    public void saveComment(
                            Long id,
                            String content) {
        Comment comment = commentRepository.findOneById(id);
        comment.setId(id);
        comment.setContent(content);
        commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findOneById(id);
        commentRepository.delete(comment);
    }

}
