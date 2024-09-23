package com.dangbei.healingspace.provider.dal.net.http.entity.column;

import java.io.Serializable;

public class ColumnInfo1 implements Serializable {
    private String title;
    private String imageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "ColumnInfo{" +
                "title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
