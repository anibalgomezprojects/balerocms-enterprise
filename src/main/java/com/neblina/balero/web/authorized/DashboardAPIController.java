/**
 * Silbato Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized;

import com.neblina.balero.domain.Property;
import com.neblina.balero.service.PropertyService;
import com.neblina.balero.service.repository.PropertyRepository;
import com.neblina.balero.service.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api")
public class DashboardAPIController {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyService propertyService;

    @Secured("ROLE_ADMIN")
    @RequestMapping("/settings")
    @ResponseBody
    public Property getSettingsJSON() {
        return propertyRepository.findOneById(1L);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    @ResponseBody
    public Property postSettingsJSON(@RequestParam("offline") boolean offline) {
        propertyService.setOfflineStatus(offline);
        return propertyRepository.findOneById(1L);
    }

}
