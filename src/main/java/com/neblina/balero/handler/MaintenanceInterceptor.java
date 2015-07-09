package com.neblina.balero.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neblina.balero.domain.Setting;
import com.neblina.balero.service.SettingService;
import com.neblina.balero.service.repository.SettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MaintenanceInterceptor extends HandlerInterceptorAdapter{

    private final Logger log = LoggerFactory.getLogger(MaintenanceInterceptor.class);

    /**
    @Autowired
    private SettingService settingService;

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
        //Setting settings = settingRepository.findOneByCode("en_US");
        String url = request.getRequestURL().toString();
        log.info("Interceptor: Pre-handle: " + url);
        log.debug("offline value: " + settingService.getOfflineStatus("en_US"));
        boolean status = true;
        if(settingService.getOfflineStatus("en_US") == 1) {
            if (url.contains("offline")) {
                log.info("I'm in offline page, do nothing.");
                status = true;
            } else {
                log.info("Redirecting to offline page.");
                response.sendRedirect("/offline/");
                status = false;
            }
        }
        if(settingService.getOfflineStatus("en_US") == 0) {
            status = true;
        }
        return status;
    }
    **/

}