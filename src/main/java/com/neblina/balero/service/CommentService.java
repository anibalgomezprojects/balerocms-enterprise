/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.service;

import com.neblina.balero.domain.Blog;
import com.neblina.balero.domain.Comment;
import com.neblina.balero.service.repository.BlogRepository;
import com.neblina.balero.service.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class CommentService {

    private final Logger log = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private BlogRepository blogRepository;

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
        // +1 Comment
        List<Comment> comments = commentRepository.findAllByPostPermalink(postPermalink);
        Blog post = blogRepository.findOneByPermalink(postPermalink);
        post.setComments(comments.size());
        blogRepository.save(post);
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
        // -1 Comment
        Blog post = blogRepository.findOneByPermalink(comment.getPostPermalink());
        post.setComments(post.getComments()-1);
        blogRepository.save(post);
        commentRepository.delete(comment);
    }

    public List<Comment> findAllByPostPermalink(String permalink) {
        return commentRepository.findAllByPostPermalink(permalink);
    }

    public Comment findOneById(Long id) {
        return commentRepository.findOneById(id);
    }

}
