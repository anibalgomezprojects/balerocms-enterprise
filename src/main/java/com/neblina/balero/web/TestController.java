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
import com.neblina.balero.domain.User;
import com.neblina.balero.service.BlogService;
import com.neblina.balero.service.repository.BlogRepository;
import com.neblina.balero.service.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestController {

    private static final Logger log = LogManager.getLogger(TestController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogService blogService;

    @RequestMapping("/log")
    @ResponseBody
    @Transactional(readOnly = true)
    public String helloLog() {
        String userDir = System.getProperty("user.dir");
        log.debug(userDir + " - debug log");
        log.info("Hello world - info log");
        log.warn("Hello world - warn log");
        log.error("Hello world - error log");
        log.trace("Entering application. - trace log");
        return "Check console";
    }

    @RequestMapping("/test")
    @ResponseBody
    @Transactional(readOnly = true)
    public List<User> helloWorld() {
        return this.userRepository.findAll();
    }

    @RequestMapping("/users")
    @ResponseBody
    @Transactional(readOnly = true)
    public Iterable<User> helloUsers() {
        return this.userRepository.findAll();
    }

    @RequestMapping("/create_post")
    @ResponseBody
    public List<Blog> printPosts() {
        blogService.createPost("test", "Unit Test", "Short Description",
                "Full Description", "en", "unit-test", "malware_bytes");
        return this.blogRepository.findAll();
    }

}
