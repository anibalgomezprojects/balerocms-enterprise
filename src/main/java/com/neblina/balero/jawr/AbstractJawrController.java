package com.neblina.balero.jawr;

import net.jawr.web.servlet.JawrSpringController;

public class AbstractJawrController extends JawrSpringController {

    public AbstractJawrController(String type) {
        setConfigLocation("jawr.properties");
        setControllerMapping("/jawr/");
        setType(type);

    }
}
