package com.smartbrowser.bookmark.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 书签模型
 */
public class Bookmark implements Serializable {
    private String id;
    private String name;
    private String url;
    private Date createTime;
    private Date lastVisitTime;
    private int visitCount;
    private String icon;
    private List<BookmarkTag> tags;
    private transient BookmarkFolder parent; // 使用 transient 避免序列化循环

    public Bookmark() {
        this.id = UUID.randomUUID().toString();
        this.createTime = new Date();
        this.tags = new ArrayList<>();
        this.visitCount = 0;
    }

    public Bookmark(String name, String url) {
        this();
        this.name = name;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(Date lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<BookmarkTag> getTags() {
        return tags;
    }

    public void setTags(List<BookmarkTag> tags) {
        this.tags = tags;
    }

    public BookmarkFolder getParent() {
        return parent;
    }

    public void setParent(BookmarkFolder parent) {
        this.parent = parent;
    }

    public void incrementVisitCount() {
        this.visitCount++;
        this.lastVisitTime = new Date();
    }

    public void addTag(BookmarkTag tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public void removeTag(BookmarkTag tag) {
        tags.remove(tag);
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
