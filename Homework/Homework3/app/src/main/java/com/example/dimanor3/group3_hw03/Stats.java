package com.example.dimanor3.group3_hw03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.crypto.interfaces.PBEKey;

public class Stats extends AppCompatActivity {
	int score = 0;

	TextView progress;
	ProgressBar progressBar;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_stats);

		progress = (TextView) findViewById (R.id.percentage);
		progressBar = (ProgressBar) findViewById (R.id.progressBar3);

		if (getIntent () != null && getIntent ().getExtras () != null) {
			score = (int) getIntent ().getExtras ().getSerializable (TriviaActivity.STATS_KEY);
		}

		progressBar.setProgress (score);

		Double temp = (double) score / 16;

		progress.setText (Double.toString (temp));
	}

	public void quit (View v) {
		Intent intent = new Intent (Stats.this, MainActivity.class);

		startActivity (intent);
	}

	public void tryAgain (View v) {
		Intent intent = new Intent (Stats.this, TriviaActivity.class);

		startActivity (intent);
	}
}
