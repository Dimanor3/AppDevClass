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
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity {
	TextView questionNumber, timeLeft, question, loading;
	ProgressBar progressBar;
	ImageView imageView;
	ArrayList<Questions> questions;
	int correctAnswer = -1, answersCorrect = 0, totalQuestions = -1, currentQuestion = 0;

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

		currentQuestion++;

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
		Log.d ("test", questions.get (5).getQuestion ());

		if (currentQuestion < totalQuestions) {
			temp = Integer.toString (questions.get (currentQuestion).getQuestionNumber () + 1);

			questionNumber.setText (temp);
			question.setText (questions.get (currentQuestion).getQuestion ());
			correctAnswer = questions.get (currentQuestion).getCorrectAnswer ();

			link = questions.get (currentQuestion).getPicURL ();

			if (isConnected ()) {
				if (!"".contains (link) || link != null) {
					new TriviaActivity.GetDataPicLinkAsync (imageView).execute (link);
				}
			} else {
				Toast.makeText (TriviaActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show ();
			}

			currentQuestion++;
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
}
