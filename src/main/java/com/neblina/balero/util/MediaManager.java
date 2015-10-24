/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author Anibal Gomez <anibalgomez@icloud.com>
 * @copyright Copyright (C) 2015 (20/10/15) ) Neblina Software. Derechos reservados.
 * @license Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.util;

import com.neblina.balero.domain.Media;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MediaManager {

    private static final Logger log = LogManager.getLogger(MediaManager.class.getName());

    /**
     * Get Relative Path Of Specific Folder
     * @author Anibal Gomez
     * @param resource
     * @return
     */
    public String getResourcePath(String resource) {
        return System.getProperty("user.dir") +
                "/src/main/resources" + resource;
    }

    /**
     * Create Image Gallery Info Object
     * @author Anibal Gomez
     * Based on:
     * http://stackoverflow.com/questions/6300675/java-mixed-arraylists
     * http://stackoverflow.com/questions/672916/how-to-get-image-height-and-width-using-java
     * @param resource
     * @return
     * @throws IOException
     */
    public List<Media> retrieveImageGalleryList(String resource) throws IOException {
        List<Media> list = new ArrayList<>();
        // Java 8 List Directory
        Files.walk(Paths.get(getResourcePath(resource))).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                try {
                    // |jpg|png|jpeg|bmp|gif|
                    if(filePath.toString().endsWith(".jpg") ||
                            filePath.toString().endsWith(".png") ||
                            filePath.toString().endsWith(".jpeg") ||
                            filePath.toString().endsWith(".bmp") ||
                            filePath.toString().endsWith(".gif")) {
                        // Image info (width, height, etc)
                        BufferedImage bimg = ImageIO.read(new File(filePath.toString()));
                        // Build FileGallery Object
                        // Merge Objects
                        Media fileGallery = new Media();
                        fileGallery.setFileName(filePath.getFileName().toString());
                        fileGallery.setWidth(bimg.getWidth());
                        fileGallery.setHeight(bimg.getHeight());
                        // Add object
                        list.add(fileGallery);
                        // Clean
                        bimg = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return list;
    }

    public void deleteResourceFile(String fileName) {
        try {
            File file = new File(getResourcePath("/static/images/uploads/" + fileName));
            if(!file.exists()) {
                throw new Exception("File Do Not Exists!" + file);
            }
            file.delete();
            log.debug("Deleting: " + file);
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

}
