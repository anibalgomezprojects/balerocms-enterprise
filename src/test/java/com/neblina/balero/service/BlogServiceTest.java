/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.service;

import com.neblina.balero.Application;
import com.neblina.balero.domain.Blog;
import com.neblina.balero.service.repository.BlogRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class BlogServiceTest extends TestCase {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogRepository blogRepository;

    @Test
    public void createTmpPost() {
        blogService.createPost("test", "Unit Test", "Short Description",
                "Full Description", "en", "unit-test", "malware_bytes");
        System.out.println("Creating Post...");
    }

    @Test
    public void selectRecordAndPrint() {
        Blog blog = blogRepository.findOneById(3L);
        System.out.println("Username is: " + blog.getAuthor() + " and Date: " + blog.getBlodate());
    }

}

