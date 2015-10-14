/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized.user;

import com.neblina.balero.domain.Blog;
import com.neblina.balero.domain.User;
import com.neblina.balero.service.UserService;
import com.neblina.balero.service.repository.BlogRepository;
import com.neblina.balero.service.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/user/api")
public class UserAPIController {

    private static final Logger log = LogManager.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BlogRepository blogRepository;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public User getUserProfileInJSON() {
        String username = userService.getMyUsername();
        return userRepository.findOneByUsername(username);
    }


    @Secured("ROLE_USER")
    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus saveSubscribedUserToJSON() {
        userService.updateSubscribedStatus();
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/subscribe/{email}", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus saveEmailSubscribebStatusInJSON(@PathVariable("email") String email) {
        log.debug("POST Request -> /subscribe/" + email);
        Base64.Decoder decoder = Base64.getDecoder();
        userService.updateSubscribedStatusByEmail(new String(decoder.decode(email)));
        return HttpStatus.OK;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    @ResponseBody
    public List<Blog> getUserPostsInJSON() {
        String username = userService.getMyUsername();
        return blogRepository.findAllByAuthor(username);
    }

}
