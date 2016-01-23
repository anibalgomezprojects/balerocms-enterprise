/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.config;

import com.github.dtrunk90.thymeleaf.jawr.JawrDialect;
import com.neblina.balero.interceptors.ExecuteTimeInterceptor;
import com.neblina.balero.interceptors.LocaleInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.util.Locale;
import java.util.Properties;

@Configuration
public class SpringConfig extends WebMvcConfigurerAdapter {

    private static final Logger log = LogManager.getLogger(SpringConfig.class.getName());

    /**
     * Environment Variables (application.properties)
     */
    @Autowired
    private Environment env;

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/",
            "classpath:/templates/", "classpath:/resources/templates/",
    };

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/offline").setViewName("offline");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Bean
    public ClassLoaderTemplateResolver emailTemplateResolver(){
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("mail/");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(1);
        return templateResolver;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:static/i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(10); //reload messages every 10 seconds
        return messageSource;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", env.getProperty("spring.mail.smtp.auth"));
        mailProperties.put("mail.smtp.starttls.enable", env.getProperty("spring.mail.smtp.starttls"));
        mailProperties.put("mail.smtp.starttls.required", env.getProperty("spring.mail.smtp.starttls"));
        mailSender.setJavaMailProperties(mailProperties);
        mailSender.setHost(env.getProperty("spring.mail.host"));
        mailSender.setPort(Integer.parseInt(env.getProperty("spring.mail.port")));
        mailSender.setProtocol(env.getProperty("spring.mail.protocol"));
        mailSender.setUsername(env.getProperty("spring.mail.username"));
        mailSender.setPassword(env.getProperty("spring.mail.password"));
        return mailSender;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.forLanguageTag("en"));
        return slr;
    }

    /**
     @Bean
     public LocaleChangeInterceptor localeChangeInterceptor() {
     LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
     lci.setParamName("lang");
     return lci;
     }
     **/

    @Bean
    public LocaleInterceptor localeInterceptor() {
        LocaleInterceptor li = new LocaleInterceptor();
        li.setParamName("lang");
        return li;
    }

    /**
     * Interceptors
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeInterceptor());
        //registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(new ExecuteTimeInterceptor());
    }

    /**
     * ThymeLeaf JAWR Integration With Additional Features
     * Based on: https://github.com/comsysto/spring-boot-demo-apps
     * spring-boot-thymeleaf-jawr
     * @author (c) 2016 Anibal Gomez
     */
    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        final SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setMessageSource(messageSource());
        engine.addDialect(new JawrDialect());
        engine.addDialect(new SpringSecurityDialect());
        engine.setTemplateResolver(templateResolver());
        return engine;
    }

    @Bean
    public TemplateResolver templateResolver() {
        final TemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

}