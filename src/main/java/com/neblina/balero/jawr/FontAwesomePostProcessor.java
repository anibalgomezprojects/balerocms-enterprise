package com.neblina.balero.jawr;

import net.jawr.web.resource.bundle.postprocess.BundleProcessingStatus;
import net.jawr.web.resource.bundle.postprocess.ResourceBundlePostProcessor;

public class FontAwesomePostProcessor implements ResourceBundlePostProcessor {

    public StringBuffer postProcessBundle(BundleProcessingStatus status, StringBuffer bundleString) {
        // FIXME: use request.contextPath
        String bundle = bundleString.toString();
        bundle = bundle.replaceAll("../../static/font-awesome/", "/font-awesome/");
        bundleString = new StringBuffer(bundle);
        return bundleString;
    }

}
