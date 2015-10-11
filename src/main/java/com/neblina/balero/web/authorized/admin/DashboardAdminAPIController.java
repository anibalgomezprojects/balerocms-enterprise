/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized.admin;

import com.neblina.balero.domain.Property;
import com.neblina.balero.domain.User;
import com.neblina.balero.service.PropertyService;
import com.neblina.balero.service.UserService;
import com.neblina.balero.service.repository.PropertyRepository;
import com.neblina.balero.service.repository.SettingRepository;
import com.neblina.balero.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class DashboardAdminAPIController {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Secured("ROLE_ADMIN")
    @RequestMapping("/properties")
    @ResponseBody
    public Property getPropertiesJSON() {
        return propertyRepository.findOneById(1L);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/properties", method = RequestMethod.POST)
    @ResponseBody
    public Property postPropertiesJSON(@RequestParam("offline") boolean offline,
                                       @RequestParam("multiLanguage") boolean multiLanguage) {
        propertyService.setOfflineStatus(offline);
        propertyService.setMultiLanguage(multiLanguage);
        return propertyRepository.findOneById(1L);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsersJSON() {
        return userRepository.findAll();
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public User getAdminProfileInJSON() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        return userRepository.findOneByUsername(username);
    }


    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus saveAdminSubscribebStatusToJSON() {
        userService.updateSubscribedStatus();
        return HttpStatus.OK;
    }

}
