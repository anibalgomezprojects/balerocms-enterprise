/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author Anibal Gomez <anibalgomez@icloud.com>
 * @copyright Copyright (C) 2015 (20/10/15) ) Neblina Software. Derechos reservados.
 * @license Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.service;

import com.neblina.balero.Application;
import com.neblina.balero.domain.Media;
import com.neblina.balero.util.MediaManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class MediaManagerTest {

    private static final Logger log = LogManager.getLogger(MediaManagerTest.class.getName());

    @Test
    public void printUploadsFolderPath() throws IOException {
        MediaManager mediaManager = new MediaManager();
        List<Media> list = mediaManager.retrieveImageGalleryList();
        int i = 0;
        for(Media file : list) {
            i++;
            log.debug("List<FileGallery>: [ " + i + " ] " +
                            "file Name: " + file.getFileName() + " " +
                            "File Width: " + file.getWidth() + " " +
                            "File Height: " + file.getHeight() + " "
            );
        }
        assertThat(list.size(), greaterThan(0));
    }


}
