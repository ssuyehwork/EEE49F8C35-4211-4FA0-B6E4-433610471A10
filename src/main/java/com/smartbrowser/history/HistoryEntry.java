package com.smartbrowser.history;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * 历史记录条目模型
 */
public class HistoryEntry implements Serializable {
    private String id;
    private String url;
    private String title;
    private Date visitTime;
    private String favicon;

    public HistoryEntry() {
        this.id = UUID.randomUUID().toString();
        this.visitTime = new Date();
    }

    public HistoryEntry(String url, String title) {
        this();
        this.url = url;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    @Override
    public String toString() {
        return "HistoryEntry{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", visitTime=" + visitTime +
                '}';
    }
}
