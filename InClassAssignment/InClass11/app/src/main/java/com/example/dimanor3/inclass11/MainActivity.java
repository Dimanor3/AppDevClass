package com.example.dimanor3.inclass11;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

	EditText imageSearch;
	Button search;
	ViewPager imageView;
	ViewPagerAdapter adapter;

	private static final int NUM_PAGES = 5;

	final String SEARCH_URL = "http://pixabay.com/api/?key=8642239-8fb7d5689e5263f0a69658141&q=";
	String searchURL = SEARCH_URL;

	private final OkHttpClient client = new OkHttpClient ();

	LinkedList<ImageSearchItem> imageSearchItems = new LinkedList<> ();
	LinkedList<ScreenSlidePageFragment> screenSlidePageFragments = new LinkedList<> ();

	int pos = 0;

	private FragmentActivity fragmentActivity;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_main);

		imageSearch = findViewById (R.id.editTextImageSearch);
		search = findViewById (R.id.buttonSearch);
		imageView = findViewById (R.id.imageView);

		fragmentActivity = new FragmentActivity ();

		search.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				if (isConnected ()) {
					Toast.makeText (MainActivity.this, "Connected", Toast.LENGTH_SHORT).show ();

					searchURL += imageSearch.getText ().toString ().replace (" ", "+");

					try {
						run (searchURL);
					} catch (Exception e) {
						e.printStackTrace ();
					}

//					adapter = new ScreenSlidePagerAdapter (getSupportFragmentManager ());
//					imageView.setAdapter (adapter);

					searchURL = SEARCH_URL;
				} else {
					Toast.makeText (MainActivity.this, "Not Connected", Toast.LENGTH_SHORT).show ();
				}
			}
		});
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

	public void run (String url) throws Exception {
		Request request = new Request.Builder ()
				.url (url)
				.build ();

		client.newCall (request).enqueue (new Callback () {
			@Override
			public void onFailure (Call call, IOException e) {
				e.printStackTrace ();
			}

			@Override
			public void onResponse (Call call, Response response) throws IOException {
				String str = response.body ().string ();

				if (str.contains ("error")) {
					Gson gson = new Gson ();
					ErrorResponse errorResponse = gson.fromJson (str, ErrorResponse.class);
					final String errorMessage = errorResponse.getMessage ();

					MainActivity.this.runOnUiThread (new Runnable () {
						@Override
						public void run () {
							Toast.makeText (MainActivity.this, errorMessage, Toast.LENGTH_LONG).show ();
						}
					});
				} else {
					try {
						JSONObject jsonImage = new JSONObject (str);
						JSONArray imageArray = jsonImage.getJSONArray ("hits");

						for (int i = 0; i < imageArray.length () - 1; i++) {
							JSONObject image = (JSONObject) imageArray.get (i);

							imageSearchItems.add (new ImageSearchItem (
									image.getString ("largeImageURL"),
									image.getInt ("likes"),
									image.getInt ("views")
							));

							screenSlidePageFragments.add (new ScreenSlidePageFragment ());
							screenSlidePageFragments.get (i).setImageSearchItem (imageSearchItems.get (i));
						}
					} catch (JSONException e) {
						e.printStackTrace ();
					}
				}
			}
		});
	}

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		LinkedList<ScreenSlidePageFragment> screenSlidePageFragments;

		public ScreenSlidePagerAdapter (FragmentManager fm, LinkedList<ScreenSlidePageFragment> screenSlidePageFragments) {
			super (fm);
			this.screenSlidePageFragments = screenSlidePageFragments;
		}

		@Override
		public ScreenSlidePageFragment getItem (int position) {
			return screenSlidePageFragments.get (position);
		}

		@Override
		public int getCount () {
			return NUM_PAGES;
		}
	}
}
