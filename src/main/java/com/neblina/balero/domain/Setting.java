/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Setting {
    @Id
    @GeneratedValue
    private Long id;
    private String code;
    private String title;
    private String titleHeader;
    private String tags;
    private String footer;
    private String offlineMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleHeader() {
        return titleHeader;
    }

    public void setTitleHeader(String titleHeader) {
        this.titleHeader = titleHeader;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public void setOfflineMessage(String offlineMessage) {
        this.offlineMessage = offlineMessage;
    }

    public String getOfflineMessage() {
        return offlineMessage;
    }


    @Override
    public String toString() {
        return "Setting [" +
                "id=" + this.id + "," +
                "code=" + this.code + "," +
                "title=" + this.title + "," +
                "titleHeader=" + this.titleHeader + "," +
                "tags=" + this.tags + "," +
                "footer=" + this.footer + ", " +
                "offlineMessage=" + this.offlineMessage
                + "]";
    }

}