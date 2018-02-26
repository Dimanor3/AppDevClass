/*
* Assignment# 4
* File Name: Headlines.java
* Full Name: Ryan Haines, Bijan Razavi, Sonia Alcantara Tuscano, Kushal Tiwari
* */

package com.example.ryanhaines.homework4;

import java.io.Serializable;

/**
 * Created by ryanhaines on 2/21/18.
 */

public class Headlines {

	String title;
	String datePublished;
	String imageURL;
	String description;
	String newsLink;

	public Headlines () {
	}

	public Headlines (String title, String datePublished, String imageURL, String description, String newsLink) {
		this.title = title;
		this.datePublished = datePublished;
		this.imageURL = imageURL;
		this.description = description;
		this.newsLink = newsLink;
	}

	public String getTitle () {
		return title;
	}

	public String getDatePublished () {
		return datePublished;
	}

	public String getImageURL () {
		return imageURL;
	}

	public String getDescription () {
		return description;
	}

	public String getNewsLink () {
		return newsLink;
	}

	@Override
	public String toString () {
		return "Headlines{" + "title='" + title + '\'' + ", datePublished='" + datePublished + '\'' + ", imageURL='" + imageURL + '\'' + ", description='" + description + '\'' + ", link='" + newsLink + '\'' + '}';
	}
}

