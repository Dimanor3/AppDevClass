/*
* Assignment# 4
* File Name: RSSParser.java
* Full Name: Ryan Haines, Bijan Razavi, Sonia Alcantara Tuscano, Kushal Tiwari
* */

package com.example.ryanhaines.homework4;

import android.util.Log;
import android.util.Xml;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.XmlStreamReader;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ryanhaines on 2/21/18.
 */

public class RSSParser {
	public static class RSSPullParser {
		static public ArrayList<Headlines> parseHeadline (InputStream inputStream) throws XmlPullParserException, IOException {
			ArrayList<Headlines> headlines = new ArrayList<> ();
			Headlines headline = null;
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance ();
			XmlPullParser parser = factory.newPullParser ();
			parser.setInput (inputStream, "UTF-8");


			int event = parser.getEventType ();


			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
					case XmlPullParser.START_DOCUMENT:
						Log.d("demo","Document Started");
						break;
					case XmlPullParser.START_TAG:
						if (parser.getName().equals ("item")) {
							headline = new Headlines ();
						} else if (headline != null && parser.getName().equals ("title")) {
							headline.title = parser.nextText ().trim ();
						} else if (headline != null && parser.getName().equals ("description")) {
							headline.description = parser.nextText().trim();
							headline.description = headline.description.split("<")[0];
						}else if (headline != null && parser.getName().equals ("media:content")) {
							headline.imageURL = parser.getAttributeValue(null, "url");
						} else if (headline != null && parser.getName().equals ("feedburner:origLink")) {
							headline.newsLink = parser.nextText ().trim ();
						} else if (headline != null && parser.getName().equals ("pubDate")) {
							headline.datePublished = parser.nextText ().trim ();
						}
						break;
					case XmlPullParser.END_TAG:
						if (parser.getName ().equals ("item")) {
							headlines.add (headline);
							headline = null;
						}
						break;
					default:
						break;
				}
				event = parser.next ();
			}

			return headlines;
		}
	}
}
