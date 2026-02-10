package com.smartbrowser.bookmark.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 书签文件夹模型
 */
public class BookmarkFolder implements Serializable {
    private String id;
    private String name;
    private List<Bookmark> bookmarks;
    private List<BookmarkFolder> subFolders;
    private transient BookmarkFolder parent;
    private Date createTime;

    public BookmarkFolder() {
        this.id = UUID.randomUUID().toString();
        this.bookmarks = new ArrayList<>();
        this.subFolders = new ArrayList<>();
        this.createTime = new Date();
    }

    public BookmarkFolder(String name) {
        this();
        this.name = name;
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

    public List<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public List<BookmarkFolder> getSubFolders() {
        return subFolders;
    }

    public void setSubFolders(List<BookmarkFolder> subFolders) {
        this.subFolders = subFolders;
    }

    public BookmarkFolder getParent() {
        return parent;
    }

    public void setParent(BookmarkFolder parent) {
        this.parent = parent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void addChild(Bookmark bookmark) {
        bookmark.setParent(this);
        this.bookmarks.add(bookmark);
    }

    public void addChild(BookmarkFolder folder) {
        folder.setParent(this);
        this.subFolders.add(folder);
    }

    public void removeChild(Bookmark bookmark) {
        this.bookmarks.remove(bookmark);
    }

    public void removeChild(BookmarkFolder folder) {
        this.subFolders.remove(folder);
    }

    /**
     * 递归获取所有书签
     */
    public List<Bookmark> getAllBookmarks() {
        List<Bookmark> all = new ArrayList<>(bookmarks);
        for (BookmarkFolder folder : subFolders) {
            all.addAll(folder.getAllBookmarks());
        }
        return all;
    }

    @Override
    public String toString() {
        return "BookmarkFolder{" +
                "name='" + name + '\'' +
                ", bookmarksCount=" + bookmarks.size() +
                ", subFoldersCount=" + subFolders.size() +
                '}';
    }
}
