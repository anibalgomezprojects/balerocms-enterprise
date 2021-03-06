package com.neblina.balero.jawr;

import net.jawr.web.JawrConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Controller(value="JawrBinaryController")
public class JawrBinaryController extends AbstractJawrController {

    @Autowired
    private ServletContext servletContext;

    /**
     * Constructor
     */
    public JawrBinaryController() {
        super(JawrConstant.BINARY_TYPE);
    }

    /* (non-Javadoc)
     * @see net.jawr.web.servlet.JawrSpringController#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    @RequestMapping(value = { "/jawr/**/**.gif", "/jawr/**/**.ico", "/jawr/**/**.png", "/jawr/**/**.jpg", "/jawr/**/**.jpeg", "/jawr/**/**.woff", "/jawr/**/**.ttf", "/jawr/**/**.eot" }, method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        return super.handleRequest(request, response);
    }

    /* (non-Javadoc)
     * @see net.jawr.web.servlet.JawrSpringController#afterPropertiesSet()
     */
    @Override
    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        setServletContext(servletContext);
        super.afterPropertiesSet();
    }
}