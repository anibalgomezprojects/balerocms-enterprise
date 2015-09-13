/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.domain;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Blog {

    @Id
    @GeneratedValue
    private Long id;
    private String bloname;
    private String title;
    private String introPost;
    private String fullPost;
    private String code;
    private String permalink;
    private String author;
    private int hits;
    private int likes;
    /**
     * Java 8 Hibernate Date
     * @author Anibal Gomez <anibalgomez@icloud.com>
     * References: https://hibernate.atlassian.net/browse/HHH-8844
    //@Type(type="LocalDateType")
    private LocalDate blodate;
    **/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBloname() {
        return bloname;
    }

    public void setBloname(String bloname) {
        this.bloname = bloname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroPost() {
        return introPost;
    }

    public void setIntroPost(String introPost) {
        this.introPost = introPost;
    }

    public String getFullPost() {
        return fullPost;
    }

    public void setFullPost(String fullPost) {
        this.fullPost = fullPost;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    /**
    public LocalDate getBlodate() {
        return blodate;
    }

    public void setBlodate(LocalDate blodate) {
        this.blodate = blodate;
    }
     **/

    @Override
    public String toString() {
        return "Block [" +
                "id=" + this.id + "," +
                "bloname=" + this.bloname + "," +
                "title=" + this.title + "," +
                "introPost=" + this.introPost + "," +
                "fullPost=" + this.fullPost + "," +
                "code=" + this.code + "," +
                "permalink=" + this.permalink + ", " +
                "author=" + this.author + ", " +
                "hits=" + this.hits + ", " +
                "likes=" + this.likes
                //"blodate=" + this.blodate
                + "]";
    }

}