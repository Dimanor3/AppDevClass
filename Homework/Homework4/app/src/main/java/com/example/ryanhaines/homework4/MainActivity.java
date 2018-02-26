/*
* Assignment# 4
* File Name: MainActivity.java
* Full Name: Ryan Haines, Bijan Razavi, Sonia Alcantara Tuscano, Kushal Tiwari
* */

package com.example.ryanhaines.homework4;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
	ImageButton nextBtn, backBtn;
	Button goBtn;
	ImageView imageViewer;
	TextView categoryText, titleText, dateText, descriptionText, indexText;
	LinkedList<String> categories = new LinkedList<> ();
	ArrayList<Headlines> newsItems = new ArrayList<> ();
	int index = 0;


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_main);

		nextBtn = findViewById (R.id.nextBtn);
		backBtn = findViewById (R.id.backBtn);
		goBtn = findViewById (R.id.goBtn);

		imageViewer = findViewById (R.id.imageView);

		categoryText = findViewById (R.id.categorieText);
		titleText = findViewById (R.id.titleText);
		dateText = findViewById (R.id.dateText);
		descriptionText = findViewById (R.id.descriptionText);
		indexText = findViewById (R.id.indexText);

		setButtonEnabled (false);

        categories.add ("Top Stories");
        categories.add ("World");
        categories.add ("U.S.");
        categories.add ("Business");
        categories.add ("Politics");
        categories.add ("Technology");
        categories.add ("Health");
        categories.add ("Entertainment");
        categories.add ("Travel");
        categories.add ("Living");
        categories.add ("Most Recent");

		backBtn.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View view) {
			if (index == 0) {
				index = newsItems.size () - 1;
				showItem (newsItems.get (index));

			} else {
				index--;
				showItem (newsItems.get (index));
			}
			}
		});

		nextBtn.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View view) {
			if (index == (newsItems.size () - 1)) {
				index = 0;
				showItem (newsItems.get (index));

			} else {
				index++;
				showItem (newsItems.get (index));
			}
			}
		});

		if (isConnected ()) goBtn.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View view) {
			Builder builder = new Builder (MainActivity.this);
			final CharSequence[] charSequences = categories.toArray (new CharSequence[categories.size ()]);

			builder.setTitle ("Choose a categories").setItems (charSequences, new DialogInterface.OnClickListener () {
				@Override
				public void onClick (DialogInterface dialogInterface, int which) {
					String categorySelected = charSequences[which].toString ();

					categoryText.setText (categorySelected);
					String apiURL = null;
					switch (categorySelected) {
						case "Top Stories":
							apiURL = "http://rss.cnn.com/rss/cnn_topstories.rss";
							Log.d ("demo", apiURL);
						case "World":
							apiURL = "http://rss.cnn.com/rss/cnn_topstories.rss";
							Log.d ("demo", apiURL);
							break;
						case "U.S.":
							apiURL = "http://rss.cnn.com/rss/cnn_us.rss";
							Log.d ("demo", apiURL);
							break;
						case "Business":
							apiURL = "http://rss.cnn.com/rss/money_latest.rss";
							Log.d ("demo", apiURL);
						case "Politics":
							apiURL = "http://rss.cnn.com/rss/cnn_allpolitics.rss";
							Log.d ("demo", apiURL);
							break;
						case "Technology":
							apiURL = "http://rss.cnn.com/rss/cnn_tech.rss";
							Log.d ("demo", apiURL);
							break;
						case "Health":
							apiURL = "http://rss.cnn.com/rss/cnn_health.rss";
							Log.d ("demo", apiURL);
						case "Entertainment":
							apiURL = "http://rss.cnn.com/rss/cnn_showbiz.rss";
							Log.d ("demo", apiURL);
							break;
						case "Travel":
							apiURL = "http://rss.cnn.com/rss/cnn_travel.rss";
							Log.d ("demo", apiURL);
							break;
						case "Living":
							apiURL = "http://rss.cnn.com/rss/cnn_living.rss";
							Log.d ("demo", apiURL);
							break;
						case "Most Recent":
							apiURL = "http://rss.cnn.com/rss/cnn_latest.rss";
							Log.d ("demo", apiURL);
							break;
						default:
							break;
					}

					new GetDataAsync ().execute (apiURL);
				}
			});

			builder.create ();
			builder.show ();
			}
		}); else {
			Toast.makeText (getApplicationContext (), "Not Connected to Network", Toast.LENGTH_LONG).show ();
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

	private class GetDataAsync extends AsyncTask<String, Void, ArrayList<Headlines>> {
		ProgressDialog progressDialog = new ProgressDialog (MainActivity.this);

		@Override
		protected ArrayList<Headlines> doInBackground (String... params) {
			HttpURLConnection connection = null;
			ArrayList<Headlines> result = new ArrayList<> ();

			try {
				URL url = new URL (params[0]);
				connection = (HttpURLConnection) url.openConnection ();
				connection.connect ();
				if (connection.getResponseCode () == HttpURLConnection.HTTP_OK) {
					String json = IOUtils.toString (connection.getInputStream (), "UTF8");
					Log.d ("connection", "Connection Made");
					result = RSSParser.RSSPullParser.parseHeadline (connection.getInputStream ());
				} else {
					Log.d ("connection", "Connection Failed");
				}
			} catch (XmlPullParserException | IOException e) {
				e.printStackTrace ();
			} finally {
				if (connection != null) {
					connection.disconnect ();
				}
			}

			return result;
		}

		@Override
		protected void onPostExecute (ArrayList<Headlines> result) {
			if (result.size () > 0) {
				Log.d ("result", result.toString ());

				newsItems.clear ();
				newsItems.addAll (result);

				index = 0;

				//progressDialog.dismiss();

				if (newsItems.size () != 0) {
					showItem (newsItems.get (index));
					setButtonEnabled (true);
					imageViewer.setVisibility (View.VISIBLE);
				}

			} else {
				Log.d ("result", "empty result");
			}
		}
	}

	public void showItem (Headlines item) {
		titleText.setText (item.title);
		dateText.setText (item.datePublished);
		descriptionText.setText (item.description);

		String indexString = Integer.toString (index + 1);

		int newArticleSize = newsItems.size ();

		String articlesString = indexString + " out of " + Integer.toString (newArticleSize);
		indexText.setText (articlesString);
		Picasso.with (MainActivity.this).load (item.imageURL).into (imageViewer);
	}

	private void setButtonEnabled (boolean enable) {
		nextBtn.setEnabled (enable);
		backBtn.setEnabled (enable);
	}
}