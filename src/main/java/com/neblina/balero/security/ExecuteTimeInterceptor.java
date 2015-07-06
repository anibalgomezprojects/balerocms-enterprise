/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Adapted for Spring Boot
 * @author lastprophet
 *
 * Based on:
 * http://www.concretepage.com/spring/spring-mvc/spring-handlerinterceptor-annotation-example-webmvcconfigureradapter
 * http://www.mkyong.com/spring-mvc/spring-mvc-handler-interceptors-example/
 */
public class ExecuteTimeInterceptor extends HandlerInterceptorAdapter{

    private final Logger log = LoggerFactory.getLogger(ExecuteTimeInterceptor.class);

    @Override
    //before the actual handler will be executed
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        return true;
    }

    @Override
    //after the handler is executed
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView)
            throws Exception {

        long startTime = (Long)request.getAttribute("startTime");

        long endTime = System.currentTimeMillis();

        long executeTime = endTime - startTime;

        //modified the exisitng modelAndView
        modelAndView.addObject("executeTime",executeTime);

        //log it
        if(log.isDebugEnabled()){
            log.debug("[" + handler + "] executeTime : " + executeTime + "ms");
        }
    }
}