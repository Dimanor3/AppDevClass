package com.example.dimanor3.inclass11;

public class ImageSearchItem {
	String url;
	int likes, views;

	public ImageSearchItem () {
	}

	public ImageSearchItem (String url, int likes, int views) {
		this.url = url;
		this.likes = likes;
		this.views = views;
	}

	public String getUrl () {
		return url;
	}

	public void setUrl (String url) {
		this.url = url;
	}

	public int getLikes () {
		return likes;
	}

	public void setLikes (int likes) {
		this.likes = likes;
	}

	public int getViews () {
		return views;
	}

	public void setViews (int views) {
		this.views = views;
	}
}
