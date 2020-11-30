package com.choicely.myapplication.receiptsaver;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ReceiptData extends RealmObject {

    @PrimaryKey
    private long id;
    private String title, imagePath, date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
