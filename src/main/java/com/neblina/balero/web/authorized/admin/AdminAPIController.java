/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized.admin;

import com.neblina.balero.domain.Media;
import com.neblina.balero.domain.Property;
import com.neblina.balero.domain.User;
import com.neblina.balero.service.PropertyService;
import com.neblina.balero.service.UserService;
import com.neblina.balero.service.repository.PropertyRepository;
import com.neblina.balero.service.repository.UserRepository;
import com.neblina.balero.util.MediaManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class AdminAPIController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserService userService;

    private static final Logger log = LogManager.getLogger(AdminPageController.class.getName());

    @Secured("ROLE_ADMIN")
    @RequestMapping("/properties")
    @ResponseBody
    public Property getPropertiesJSON() {
        return propertyService.findOneById(1L);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/properties", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus postPropertiesJSON(@RequestParam("offline") boolean offline,
                                       @RequestParam("multiLanguage") boolean multiLanguage) {
        propertyService.setOfflineStatus(offline);
        propertyService.setMultiLanguage(multiLanguage);
        return HttpStatus.OK;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsersJSON() {
        return userService.findAll();
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public User getAdminProfileInJSON() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        return userService.findOneByUsername(username);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus saveAdminSubscribebStatusToJSON() {
        userService.updateSubscribedStatus();
        return HttpStatus.OK;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/uploads", method = RequestMethod.GET)
    @ResponseBody
    public List<Media> getUploadsJSON() throws IOException {
        MediaManager mediaManager = new MediaManager();
        List<Media> list = mediaManager.retrieveImageGalleryList();
        return list;
    }

    /**
     *
     * @param data Is a JSON Object.
     *             It Needs to be deserialized to a Java Object
     * @return
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/uploads", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpStatus deleteUploadsJSON(@RequestBody List<Media> data)  {
        log.debug("POST /admin/api/uploads");
        MediaManager mediaManager = new MediaManager();
        for(int i = 0; i < data.size(); i++) {
            String fileName = data.get(i).getFileName();
            try {
                if(fileName.equals("bsd_daemon.png")) {
                    throw new Exception("Can't Delete Unit Test File (bsd_daemon.png).");
                }
                mediaManager.deleteResourceFile(fileName);
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
            log.debug("JSON Row: " + fileName);
        }
        return HttpStatus.OK;
    }

}
