package com.choicely.myapplication.imagegallery;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GalleryData extends RealmObject {
    @PrimaryKey
    private long id;
    private String url, urlName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }
}
