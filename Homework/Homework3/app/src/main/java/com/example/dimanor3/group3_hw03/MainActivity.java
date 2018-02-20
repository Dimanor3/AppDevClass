/*
 * Assignment# 3
 * File Name: MainActivity.java
 * Full Name: Bijan Razavi, Kushal Tiwari
 */

package com.example.dimanor3.group3_hw03;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
	ImageView trivia;
	TextView loading;
	Button start;
	ProgressBar progressBar;
	LinkedList<Questions> questions;

	static String QUESTIONS_KEY = "QUESTIONS";

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_main);

		trivia = (ImageView) findViewById (R.id.triviaImageView);
		start = (Button) findViewById (R.id.startButton);
		loading = (TextView) findViewById (R.id.loading);
		progressBar = (ProgressBar) findViewById (R.id.progressBar);

		questions = new LinkedList<> ();

		if (isConnected ()) {
			new GetDataAsync ().execute ("http://dev.theappsdr.com/apis/trivia_json/trivia_text.php");
		} else {
			Toast.makeText (MainActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show ();
		}
	}

	public void startTrivia (View v) {
		Intent intent = new Intent (MainActivity.this, TriviaActivity.class);

		intent.putExtra (QUESTIONS_KEY, questions);

		startActivity (intent);
	}

	public void exit (View v) {
		finish ();
		System.exit (0);
	}

	private boolean isConnected () {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo ();

		return !(networkInfo == null || !networkInfo.isConnected () ||
				(networkInfo.getType () != ConnectivityManager.TYPE_WIFI
						&& networkInfo.getType () != ConnectivityManager.TYPE_MOBILE));
	}

	private class GetDataAsync extends AsyncTask <String, Void, LinkedList<Questions>> {
		@Override
		protected void onPreExecute () {
		}

		@Override
		protected LinkedList<Questions> doInBackground (String... strings) {
			LinkedList<Questions> questionsLinkedList = new LinkedList<> ();

			String question = "", picURL = "";
			ArrayList<String> answerChoices = new ArrayList<> ();
			int questionNumber = -1, correctAnswer = -1;

			HttpURLConnection connection = null;

			BufferedReader reader = null;

			try {
				URL url = new URL (strings[0]);
				connection = (HttpURLConnection) url.openConnection ();

				connection.connect ();

				if (connection.getResponseCode () == HttpURLConnection.HTTP_OK) {
					reader = new BufferedReader (new InputStreamReader (connection.getInputStream ()));

					String line = "";

					LinkedList<String> parts = new LinkedList<> ();

					while ((line = reader.readLine ()) != null) {
						for (String l: line.split ("\n")) {
							parts.addAll (Arrays.asList (l.split (";")));

							questionNumber = Integer.parseInt (parts.get (0));

							question = parts.get (1);

							picURL = parts.get (2);

							Log.d ("t", line);

							for (int i = 3; i < parts.size () - 2; i++) {
								answerChoices.add (parts.get (i));
							}

							correctAnswer = Integer.parseInt (parts.get (parts.size () - 1));

							questionsLinkedList.add (new Questions (question, picURL, answerChoices, questionNumber, correctAnswer));
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace ();
			} finally {
				if (connection != null) {
					connection.disconnect ();
				}

				if (reader != null) {
					try {
						reader.close ();
					} catch (IOException e) {
						e.printStackTrace ();
					}
				}
			}

			return questionsLinkedList;
		}

		@Override
		protected void onPostExecute (LinkedList<Questions> questionList) {
			if (questions != null) {
				questions.addAll (questionList);

				start.setTextColor (Color.parseColor ("black"));
				start.setClickable (true);

				progressBar.setVisibility (View.INVISIBLE);

				trivia.setVisibility (View.VISIBLE);

				String temp = "Trivia Ready";

				loading.setText (temp);
			} else {
				Log.d ("null", "Null Questions!");
			}
		}
	}
}
