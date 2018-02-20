/*
* Assignment# 6
* File Name: MainActivity.java
* Full Name: Bijan Razavi, Kushal Tiwari
* */

package com.example.dimanor3.inclass06;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    LinkedList<String> data = new LinkedList<> ();
    LinkedList<Articles> articles = new LinkedList<> ();

    int curItem = 0;

    RequestParams params = new RequestParams ();

    String newText = "", category;

    Button go;
    ImageButton previous, forward;
    TextView showCategories, title, publishedAt, newsDescription, outOf;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        go = (Button) findViewById (R.id.goButton);
        previous = (ImageButton) findViewById (R.id.previousButton);
        forward = (ImageButton) findViewById (R.id.nextButton);

        showCategories = (TextView) findViewById (R.id.showCategories);
        title = (TextView) findViewById (R.id.showTitle);
        publishedAt = (TextView) findViewById (R.id.publishedAt);
        newsDescription = (TextView) findViewById (R.id.newsDescription);
        outOf = (TextView) findViewById (R.id.outOf);

        data.add ("business");
        data.add ("entertainment");
        data.add ("general");
        data.add ("health");
        data.add ("science");
        data.add ("sports");
        data.add ("technology");

        params.addParameter ("country", "us");
        params.addParameter ("apikey", "615ebef4e6cc4072a1b6b9c05b020956");
    }

    public void go (View v) {
        if (isConnected ()) {
            //imageLinks.clear ();

            curItem = 0;

            handleData (data);
        } else {
            Toast.makeText (MainActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show ();
        }
    }

    public void previous (View v) {

    }

    public void next (View v) {

    }

    public void handleData (LinkedList<String> data) {
        this.data = data;
        final CharSequence[] charSequence = data.toArray (new CharSequence[data.size ()]);

        AlertDialog.Builder builder = new AlertDialog.Builder (this);

        builder.setTitle ("Choose a Keyword")
                .setItems (charSequence, new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int which) {
                        newText = charSequence[which].toString ();

                        showCategories.setText (newText);

                        params.addParameter ("category", newText);

                        String url = params.getEncodedUrl ("https://newsapi.org/v2/top-headlines");

                        new GetDataParamsUsingGetAsync (MainActivity.this).execute (url);
                    }
                });

        final AlertDialog alertDialog = builder.create ();

        alertDialog.show ();
    }

    private boolean isConnected () {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo ();

        return !(networkInfo == null || !networkInfo.isConnected () ||
                (networkInfo.getType () != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType () != ConnectivityManager.TYPE_MOBILE));
    }

    private class GetDataParamsUsingGetAsync extends AsyncTask<String, Void, LinkedList<Articles>> {
        public GetDataParamsUsingGetAsync (MainActivity mainActivity) {

        }

        @Override
        protected LinkedList<Articles> doInBackground (String... params) {
            LinkedList<Articles> result = new LinkedList<> ();

            HttpURLConnection connection = null;

            try {
                URL url = new URL (params[0]);

                connection = (HttpURLConnection) url.openConnection ();

                connection.connect ();

                if (connection.getResponseCode () == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString (connection.getInputStream (), "UTF8");
                    JSONObject root = new JSONObject (json);

                    JSONArray newsArticles = root.getJSONArray ("articles");

                    for (int i = 0; i < newsArticles.length (); i++) {
                        JSONObject newsArticlesJSON = newsArticles.getJSONObject (i);
                        result.add (new Articles (newsArticlesJSON.getString ("title"),
                                newsArticlesJSON.getString ("description"),
                                newsArticlesJSON.getString ("publishedAt"),
                                newsArticlesJSON.getString ("urlToImage")));
                    }
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace ();
            } finally {
                if (connection != null) {
                    connection.disconnect ();
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute (LinkedList<Articles> result) {
            if (result != null) {
                articles.addAll (result);

                curItem = 1;

                display (articles.get (0), articles.size ());

                if (articles == null || articles.size () <= 1) {
                    setClickable (false);
                    Log.d ("demo", "There are 0 or 1 images.");
                } else {
                    setClickable (true);
                }
            } else {
                String str = "Null Result";
            }
        }
    }

    private void display (Articles article, int size) {
        Log.d ("DEMO!", article.getTitle ());
        title.setText (article.getTitle ());
        publishedAt.setText (article.getPublishedAt ());
        newsDescription.setText (article.getDescription ());

        String temp = Integer.toString (curItem) + " out of " + Integer.toString (size);

        outOf.setText (temp);
    }

    private void setClickable (boolean set) {
        previous.setClickable (set);
        forward.setClickable (set);
    }
}
