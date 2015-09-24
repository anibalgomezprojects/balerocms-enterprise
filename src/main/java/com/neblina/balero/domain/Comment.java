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
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String content;
    private String code;
    private String author;
    @Type(type="org.hibernate.type.LocalDateType")
    private LocalDate blodate;
    private String postPermalink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getBlodate() {
        return blodate;
    }

    public void setBlodate(LocalDate blodate) {
        this.blodate = blodate;
    }

    public String getPostPermalink() {
        return postPermalink;
    }

    public void setPostPermalink(String postPermalink) {
        this.postPermalink = postPermalink;
    }

    @Override
    public String toString() {
        return "Block [" +
                "id=" + this.id + "," +
                "content=" + this.content + "," +
                "code=" + this.code + "," +
                "author=" + this.author + ", " +
                "blodate=" + this.blodate + ", " +
                "postPermalink=" + this.postPermalink
                + "]";
    }


}