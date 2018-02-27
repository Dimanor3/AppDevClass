package com.example.dimanor3.inclass07;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

public class Category extends AppCompatActivity {

	LinkedList<Articles> articles = new LinkedList<> ();

	RequestParams params = new RequestParams ();

	String category = "";

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_category);

		params.addParameter ("country", "us");
		params.addParameter ("apikey", "615ebef4e6cc4072a1b6b9c05b020956");

		if (getIntent () != null && getIntent ().getExtras () != null) {
			category = (String) getIntent ().getExtras ().getString (MainActivity.CATEGORY_KEY);
		}

		setTitle (category);

		if (isConnected ()) {
			params.addParameter ("category", category);

			String url = params.getEncodedUrl ("https://newsapi.org/v2/top-headlines");

			new GetDataParamsUsingGetAsync (Category.this).execute (url);
		} else {
			Toast.makeText (Category.this, "No Connection!", Toast.LENGTH_SHORT).show ();
		}
	}



	private boolean isConnected () {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo ();

		if (networkInfo == null || !networkInfo.isConnected () ||
				(networkInfo.getType () != ConnectivityManager.TYPE_WIFI
						&& networkInfo.getType () != ConnectivityManager.TYPE_MOBILE)) {
			return false;
		}
		return true;
	}

	private class GetDataParamsUsingGetAsync extends AsyncTask<String, Void, LinkedList<Articles>> {
		ProgressDialog progressDialog;

		public GetDataParamsUsingGetAsync (Category category) {

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

				setupListView (articles.size ());

				for (Articles a: articles) {
					Log.d ("kjsfdkld", a.toString ());
				}
			} else {
				String str = "Null Result";
			}
		}
	}

	private void setupListView (int size) {
		ImageView imageView;


		for (int i = 0; i < size; i++) {

		}
	}
}
