/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized.admin;

import com.neblina.balero.domain.User;
import com.neblina.balero.service.EmailService;
import com.neblina.balero.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/admin/email")
public class AdminEmailController {

    private static final Logger log = LogManager.getLogger(AdminEmailController.class.getName());

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"", "/"} )
    public String email(Model model) {
        model.addAttribute("totalUsers", userService.getTotalUsers());
        return "authorized/email";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public String emailPost(@RequestParam("subject") String subject,
                                 @RequestParam("messageBody") String messageBody,
                                 Model model, Locale locale) throws MessagingException, UnsupportedEncodingException {
        List<User> users = userService.findAll();
        for(User user: users) {
            log.debug("Sending Email To..." + user.getEmail());
            this.emailService.sendSimpleMail(user.getFirstName(), user.getEmail(), subject, messageBody, locale);
        }
        model.addAttribute("totalUsers", userService.getTotalUsers());
        model.addAttribute("success", 1);
        return "authorized/email";
    }

    /*
    * Send HTML mail with attachment.
    */
    // TODO: Need to be integrated
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/sendMailWithAttachment", method = RequestMethod.POST)
    public String sendMailWithAttachment(
            @RequestParam("recipientName") final String recipientName,
            @RequestParam("recipientEmail") final String recipientEmail,
            @RequestParam("attachment") final MultipartFile attachment,
            final Locale locale)
            throws MessagingException, IOException {

        this.emailService.sendMailWithAttachment(
                recipientName, recipientEmail, attachment.getOriginalFilename(),
                attachment.getBytes(), attachment.getContentType(), locale);
        return "redirect:sent.html";

    }

    /*
     * Send HTML mail with inline image
     */
    // TODO: Need to be integrated
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/sendMailWithInlineImage", method = RequestMethod.POST)
    public String sendMailWithInline(
            @RequestParam("recipientName") final String recipientName,
            @RequestParam("recipientEmail") final String recipientEmail,
            @RequestParam("image") final MultipartFile image,
            final Locale locale)
            throws MessagingException, IOException {

        this.emailService.sendMailWithInline(
                recipientName, recipientEmail, image.getName(),
                image.getBytes(), image.getContentType(), locale);
        return "redirect:sent.html";

    }

}
