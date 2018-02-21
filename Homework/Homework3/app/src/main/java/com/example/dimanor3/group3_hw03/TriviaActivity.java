/*
 * Assignment# 3
 * File Name: TriviaActivity.java
 * Full Name: Bijan Razavi, Kushal Tiwari
 */

package com.example.dimanor3.group3_hw03;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

public class TriviaActivity extends AppCompatActivity {
	TextView questionNumber, timeLeft, question, loading;
	ProgressBar progressBar;
	ImageView imageView;
	ArrayList<Questions> questions;
	RadioGroup radioGroup;
	int correctAnswer = -1, answersCorrect = 0, totalQuestions = -1;
	private int currentQuestion = 0;

	public static String STATS_KEY = "STATS";

	String link = "", temp = "";

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_trivia);

		questionNumber = (TextView) findViewById (R.id.questionNumber);
		timeLeft = (TextView) findViewById (R.id.timeLeft);
		question = (TextView) findViewById (R.id.question);
		loading = (TextView) findViewById (R.id.loading);

		imageView = (ImageView) findViewById (R.id.image);

		progressBar = (ProgressBar) findViewById (R.id.progressBar2);

		radioGroup = (RadioGroup) findViewById (R.id.radioGroup);

		questions = new ArrayList<> ();

		if (getIntent () != null && getIntent ().getExtras () != null) {
			questions = (ArrayList<Questions>) getIntent ().getExtras ().getSerializable (MainActivity.QUESTIONS_KEY);
		}

		totalQuestions = questions.size ();

		temp = Integer.toString (questions.get (currentQuestion).getQuestionNumber () + 1);

		questionNumber.setText (temp);
		question.setText (questions.get (currentQuestion).getQuestion ());
		correctAnswer = questions.get (currentQuestion).getCorrectAnswer ();

		link = questions.get (currentQuestion).getPicURL ();

		displayChoices (questions.get (currentQuestion).getAnswerChoices ());

		radioGroup.setOnCheckedChangeListener (new RadioGroup.OnCheckedChangeListener () {
			int j = answersCorrect;

			@Override
			public void onCheckedChanged (RadioGroup radioGroup, int i) {
				currentQuestion = 0;

				RadioButton rB = (RadioButton) findViewById (radioGroup.getCheckedRadioButtonId ());

				Log.d ("okjweoip", rB.getText ().toString () + " " + Integer.toString (currentQuestion) + " " + questions.get (currentQuestion).getAnswerChoices ().get (correctAnswer));

				if (rB.getText ().toString ().contains (questions.get (currentQuestion).getAnswerChoices ().get (correctAnswer)) && answersCorrect == j) {
					Log.d ("jsdfjk", "I work!");
					answersCorrect++;
				}

				if (!rB.getText ().toString ().contains (questions.get (currentQuestion).getAnswerChoices ().get (correctAnswer)) && answersCorrect > j) {
					Log.d ("jsdfjk", "So do I!");
					answersCorrect--;
				}
			}
		});

		currentQuestion = 1;

		if (isConnected ()) {
			if (!"".contains (link) || link != null) {
				new TriviaActivity.GetDataPicLinkAsync (imageView).execute (link);
			}
		} else {
			Toast.makeText (TriviaActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show ();
		}
	}

	public void quit (View v) {
		Intent intent = new Intent (TriviaActivity.this, MainActivity.class);

		startActivity (intent);
	}

	public void nextQuestion (View v) {
		if (currentQuestion == 0) {
			currentQuestion = 1;
		}

		clearDisplayChoices ();

		if (currentQuestion < totalQuestions) {
			temp = Integer.toString (questions.get (currentQuestion).getQuestionNumber () + 1);

			questionNumber.setText (temp);
			question.setText (questions.get (currentQuestion).getQuestion ());
			correctAnswer = questions.get (currentQuestion).getCorrectAnswer ();

			link = questions.get (currentQuestion).getPicURL ();

			displayChoices (questions.get (currentQuestion).getAnswerChoices ());

			Log.d ("ipweoi", Integer.toString (currentQuestion));

			radioGroup.setOnCheckedChangeListener (new RadioGroup.OnCheckedChangeListener () {
				int j = answersCorrect;

				@Override
				public void onCheckedChanged (RadioGroup radioGroup, int i) {
					RadioButton rB = (RadioButton) findViewById (radioGroup.getCheckedRadioButtonId ());

					Log.d ("okjweoip", rB.getText ().toString () + " " + Integer.toString (currentQuestion) + " " + questions.get (currentQuestion).getAnswerChoices ().get (correctAnswer));

					if (rB.getText ().toString ().contains (questions.get (currentQuestion).getAnswerChoices ().get (correctAnswer)) && answersCorrect == j) {
						Log.d ("jsdfjk", "I work!");
						answersCorrect++;
					}

					if (!rB.getText ().toString ().contains (questions.get (currentQuestion).getAnswerChoices ().get (correctAnswer)) && answersCorrect > j) {
						Log.d ("jsdfjk", "So do I!");
						answersCorrect--;
					}
				}
			});

			if (isConnected ()) {
				if (!"".contains (link) || link != null) {
					new TriviaActivity.GetDataPicLinkAsync (imageView).execute (link);
				}
			} else {
				Toast.makeText (TriviaActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show ();
			}

			currentQuestion++;
		} else {
			stats ();
		}
	}

	private boolean isConnected () {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo ();

		return !(networkInfo == null || !networkInfo.isConnected () ||
				(networkInfo.getType () != ConnectivityManager.TYPE_WIFI
						&& networkInfo.getType () != ConnectivityManager.TYPE_MOBILE));
	}

	private class GetDataPicLinkAsync extends AsyncTask<String, Void, Void> {
		ImageView imageView;

		Bitmap bitmap = null;

		public GetDataPicLinkAsync (ImageView imageView) {
			this.imageView = imageView;
			imageView.setVisibility (View.INVISIBLE);
		}

		@Override
		protected void onPreExecute () {
			setVisibilityTo (true);
		}

		@Override
		protected Void doInBackground (String... params) {
			HttpURLConnection connection = null;

			bitmap = null;

			try {
				URL url = new URL (params[0]);
				connection = (HttpURLConnection) url.openConnection ();

				connection.connect ();

				if (connection.getResponseCode () == HttpURLConnection.HTTP_OK) {
					bitmap = BitmapFactory.decodeStream (connection.getInputStream ());
				}
			} catch (IOException e) {
				e.printStackTrace ();
			} finally {
				if (connection != null) {
					connection.disconnect ();
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute (Void aVoid) {
			setVisibilityTo (false);

			if (bitmap != null && imageView != null) {
				imageView.setImageBitmap (bitmap);

				imageView.setVisibility (View.VISIBLE);
			}
		}
	}

	private void setVisibilityTo (boolean visible) {
		if (visible) {
			progressBar.setVisibility (View.VISIBLE);
			loading.setVisibility (View.VISIBLE);
		} else {
			progressBar.setVisibility (View.INVISIBLE);
			loading.setVisibility (View.INVISIBLE);
		}
	}
	int test = 0;
	private void displayChoices (ArrayList<String> answerChoices) {
		RadioButton radioButton;

		for (String aC: answerChoices) {
			radioButton = new RadioButton (this);
			radioGroup.addView (radioButton);
			radioButton.setText (aC);
		}

		test++;
	}

	private void clearDisplayChoices () {
		radioGroup.removeAllViews ();
	}

	private void stats () {
		Intent intent = new Intent (TriviaActivity.this, Stats.class);

		intent.putExtra (STATS_KEY, answersCorrect);

		startActivity (intent);
	}
}
