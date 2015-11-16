/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.handler;

import com.neblina.balero.domain.Information;
import com.neblina.balero.service.InformationService;
import com.neblina.balero.service.PropertyService;
import com.neblina.balero.service.repository.InformationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Adapted for Spring Boot
 * @author lastprophet
 */
public class LocaleInterceptor extends HandlerInterceptorAdapter{

    public static final String DEFAULT_PARAM_NAME = "locale";

    private String paramName = DEFAULT_PARAM_NAME;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private InformationRepository informationRepository;

    private final Logger log = LoggerFactory.getLogger(LocaleInterceptor.class);

    /**
     * Set the name of the parameter that contains a locale specification
     * in a locale change request. Default is "locale".
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * Return the name of the parameter that contains a locale specification
     * in a locale change request.
     */
    public String getParamName() {
        return this.paramName;
    }

    @Override
    //before the actual handler will be executed
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {

        String newLocale = request.getParameter(getParamName());
        if(newLocale != null) {
            Information information = new Information();
            information.setIp(getUserIp());
            information.setLocale(newLocale);
            informationRepository.save(information);
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            localeResolver.setLocale(request, response, StringUtils.parseLocaleString(information.getLocale()));
        }
        if(newLocale == null) {
            try {
                Information information = informationRepository.findOneByIp(getUserIp());
                if(information == null) {
                    throw new Exception("Ip Not Found");
                }
                LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
                localeResolver.setLocale(request, response, StringUtils.parseLocaleString(information.getLocale()));
            } catch (Exception e) {
                // ip not found set default language system
                LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
                localeResolver.setLocale(request, response, StringUtils.parseLocaleString(propertyService.getMainLanguage()));
            }
        }
        return true;

    }

    public String getUserIp() throws UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        return ip.getHostAddress();
    }

}