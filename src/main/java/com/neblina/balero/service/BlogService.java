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
import com.neblina.balero.service.repository.BlogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class BlogService {

    private final Logger log = LoggerFactory.getLogger(BlogService.class);

    @Autowired
    private BlogRepository blogRepository;

    public void createPost(String bloname,
                           String title,
                           String introPost,
                           String fullPost,
                           String code,
                           String permalink,
                           String author,
                           String status) {
        Blog blog = new Blog();
        blog.setBloname(bloname);
        blog.setTitle(title);
        blog.setIntroPost(introPost);
        blog.setFullPost(fullPost);
        blog.setCode(code);
        blog.setPermalink(permalink);
        blog.setAuthor(author);
        blog.setHits(0);
        blog.setLikes(0);
        //Current Date
        LocalDate today = LocalDate.now();
        blog.setBlodate(today);
        blog.setStatus(status);
        blog.setComments(0);
        blogRepository.save(blog);
    }

    public void savePost(Long id,
                         String bloname,
                         String title,
                         String introPost,
                         String fullPost,
                         String code,
                         String permalink,
                         String status) {
        Blog blog = blogRepository.findOneById(id);
        blog.setBloname(bloname);
        blog.setTitle(title);
        blog.setIntroPost(introPost);
        blog.setFullPost(fullPost);
        blog.setCode(code);
        blog.setPermalink(permalink);
        blog.setStatus(status);
        blogRepository.save(blog);
    }

    public void deletePost(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        Blog blog = blogRepository.findOneById(id);
        if(blog.getAuthor().equals(username)) {
            blogRepository.delete(blog);
        }
    }

    public void setLikes(Long id) {
        Blog blog = blogRepository.findOneById(id);
        blog.setLikes(blog.getLikes()+1);
        blogRepository.save(blog);
        log.debug("Likes: " + blog.getLikes());
    }

}
