package com.example.ryanhaines.finalpractice;

/**
 * Created by ryanhaines on 5/2/18.
 */

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
public class RSSParser {
    public static class RSSPullParser {
        static public ArrayList<Apps> parseApps (InputStream inputStream) throws XmlPullParserException, IOException {
            ArrayList<Apps> apps = new ArrayList<> ();
            Apps app = null;
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
                        if (parser.getName().equals ("feed")) {
                            Log.d("demo", "This is running maybe?");
                            app = new Apps();
                        } else if (app != null && parser.getName().equals ("title")) {
                            app.title = parser.nextText ().trim ();
                            Log.d("demo", app.title);
                        } else if (app != null && parser.getName().equals ("image")) {
                            app.imgUrl = parser.nextText().trim();
                        }else if (app != null && parser.getName().equals ("summary")) {
                            app.summary = parser.getAttributeValue(null, "url");
                        } else if (app != null && parser.getName().equals ("artist:label")) {
                            app.developerName = parser.nextText ().trim ();
                        } else if (app != null && parser.getName().equals ("category:term")) {
                            app.category = parser.nextText ().trim ();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName ().equals ("name")) {
                            apps.add (app);
                            app = null;
                        }
                        break;
                    default:
                        break;
                }
                event = parser.next ();
            }

            return apps;
        }
    }
}
