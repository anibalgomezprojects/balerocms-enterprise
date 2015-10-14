/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2014, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */

package com.neblina.balero.web;

import com.neblina.balero.service.EmailService;
import com.neblina.balero.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;


@Controller
@RequestMapping("/mail")
public class MailController {

    private static final Logger log = LogManager.getLogger(MailController.class.getName());

    @Autowired 
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"", "/"} )
    public String root() {
        return "redirect:/";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String addUserToMailListGet() {
        log.debug("Mailing List Get {}");
        return "redirect:/offline";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String addUserToMailList(@RequestParam("firstname") String firstname,
                                    @RequestParam("email") String email) {
        log.debug("Mailing List Post {}");
        try {
            userService.createUserAccount("temp", "temp",
                    "temp", firstname, "temp", email, true, "ROLE_USER", "user");
        } catch (Exception e) {
            log.debug("Error: " + e.getMessage());
        }
        return "mailing-list_added";
    }
    
}
