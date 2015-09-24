/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.util;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@Configuration
@Profile("prod")
@PropertySource("classpath:application-prod.yml")
public class AssetPipeline {

    private static final Logger log = LogManager.getLogger(AssetPipeline.class.getName());

    @Value("${balerocms.minification}")
    private boolean balerocmsMinification;

    public AssetPipeline() {
        log.debug("Running Balero CMS Resource Compiler...");
    }

    public void compress(String file) {
        String html = "", sCurrentLine;
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            while ((sCurrentLine = input.readLine()) != null) {
                html += sCurrentLine;
            }
            HtmlCompressor compressor = new HtmlCompressor();
            String compressedHtml = compressor.compress(html);
            if(balerocmsMinification == true) {
                log.debug("Compiling Resource... " + file);
                FileWriter output = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(output);
                bw.write(compressedHtml);
                bw.close();
            }
        } catch (Exception e) {
            System.out.println("Asset Pipeline Error: " + e.getMessage());
        }
    }

    public String multiPlatformResourcesPath(String file) {
        String resource = System.getProperty("user.dir") +
                "/src/main/resources/" + file;
        return resource.replace("\\", "/");
    }

    public ArrayList<String> getHtmlResourceFileList(String directory) throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        Files.walk(Paths.get(multiPlatformResourcesPath(directory))).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                String file = filePath.getFileName().toString();
                String ext = file.substring(file.lastIndexOf("."));
                if(ext.equals(".html")) {
                    list.add(filePath.toString());
                }
            }
        });
        return list;
    }

}
