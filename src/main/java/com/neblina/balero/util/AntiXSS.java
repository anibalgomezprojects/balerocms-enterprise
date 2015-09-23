/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.util;

import org.owasp.html.Sanitizers;

public class AntiXSS {

    /**
     * Sanitize common elements b, p, etc. img and a.
     * @author Anibal Gomez
     * @param input Unsafe Input
     * @return Safe Output
     */
    public String blind(String input) {
        org.owasp.html.PolicyFactory policy = Sanitizers.STYLES
                .and(Sanitizers.FORMATTING)
                .and(Sanitizers.IMAGES)
                .and(Sanitizers.LINKS);
        String output = policy.sanitize(input);
        return output;
    }

}
