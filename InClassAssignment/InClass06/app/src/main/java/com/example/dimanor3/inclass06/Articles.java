/*
* Assignment#: InClass06
* File Name: Articles.java
* Full Name: Bijan Razavi, Kushal Tiwari, Ryan Haines, Sonia Alcantara Tuscano
* */

package com.example.dimanor3.inclass06;

import java.io.Serializable;

/**
 * Created by Dimanor3 on 2/19/2018.
 */

public class Articles implements Serializable {
    String title, description, publishedAt, imgURL;

    public Articles (String title, String description, String publishedAt, String imgURL) {

        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.imgURL = imgURL;
    }

    public String getTitle () {
        return title;
    }

    public String getDescription () {
        return description;
    }

    public String getPublishedAt () {
        return publishedAt;
    }

    public String getImgURL () {
        return imgURL;
    }
}
