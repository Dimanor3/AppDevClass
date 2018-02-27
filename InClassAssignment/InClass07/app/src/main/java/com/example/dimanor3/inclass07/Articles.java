/*
* Assignment#: InClass07
* File Name: Articles.java
* Full Name: Ryan Haines, Bijan Razavi, Sonia Alcantara Tuscano, Kushal Tiwari
* */

package com.example.dimanor3.inclass07;

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

	@Override
	public String toString () {
		return "Articles{" +
				"title='" + title + '\'' +
				", description='" + description + '\'' +
				", publishedAt='" + publishedAt + '\'' +
				", imgURL='" + imgURL + '\'' +
				'}';
	}
}
