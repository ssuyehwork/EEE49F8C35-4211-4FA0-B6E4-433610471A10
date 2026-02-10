package com.smartbrowser.bookmark.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * \u4e66\u7b7e\u6807\u7b7e\u6a21\u578b
 */
public class BookmarkTag implements Serializable {
    private String name;
    private String color;

    public BookmarkTag() {}

    public BookmarkTag(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookmarkTag that = (BookmarkTag) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "BookmarkTag{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
