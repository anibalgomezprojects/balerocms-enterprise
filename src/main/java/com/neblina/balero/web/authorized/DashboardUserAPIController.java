/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized;

import com.neblina.balero.domain.Property;
import com.neblina.balero.domain.User;
import com.neblina.balero.service.PropertyService;
import com.neblina.balero.service.UserService;
import com.neblina.balero.service.repository.PropertyRepository;
import com.neblina.balero.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/api")
public class DashboardUserAPIController {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public User getUserProfileInJSON() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        return userRepository.findOneByUsername(username);
    }


    @Secured("ROLE_USER")
    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus saveSubscribedUserToJSON() {
        userService.updateSubscribedStatus();
        return HttpStatus.OK;
    }

}
