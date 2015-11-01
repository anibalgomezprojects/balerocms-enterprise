/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.config;

import com.neblina.balero.handler.ExecuteTimeInterceptor;
import com.neblina.balero.handler.LocaleInterceptor;
import com.neblina.balero.util.AssetPipeline;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;
import java.util.ArrayList;
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

    @Bean
    public AssetPipeline compile() throws IOException {
        log.debug("Minification: " + env.getProperty("balerocms.minification"));
        boolean balerocmsMinification = Boolean.parseBoolean(env.getProperty("balerocms.minification"));
        AssetPipeline resources = new AssetPipeline();
        if(balerocmsMinification == true) {
            ArrayList<String> templates = resources.getHtmlResourceFileList("templates/");
            for(int i = 0; i < templates.size(); i++) resources.compress(templates.get(i));
        }
        return resources;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/offline").setViewName("offline");
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

     @Bean
     public LocaleChangeInterceptor localeChangeInterceptor() {
     LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
     lci.setParamName("lang");
     return lci;
     }

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
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(new ExecuteTimeInterceptor());
    }

}