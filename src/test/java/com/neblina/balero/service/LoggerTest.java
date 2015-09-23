package com.neblina.balero.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class LoggerTest {

    private static final Logger log = LogManager.getLogger(LoggerTest.class.getName());

    @Test
    public void  printLog() {
        String userDir = System.getProperty("user.dir");
        log.debug(userDir + " - debug log");
        log.info("Message - info log");
        log.warn("Message - warn log");
        log.error("Message - error log");
        log.trace("Message - trace log");
    }

}
