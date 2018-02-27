package com.example.dimanor3.inclass07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

	TextView title, publishedAt, newsDescription;
	ImageView imageView;

	Articles articles;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_detail);

		setTitle ("Detail Activity");

		title = (TextView) findViewById (R.id.showTitle);
		publishedAt = (TextView) findViewById (R.id.publishedAt);
		newsDescription = (TextView) findViewById (R.id.newsDescription);

		imageView = (ImageView) findViewById (R.id.imageView);

		if (getIntent () != null && getIntent ().getExtras () != null) {
			//articles = (Articles) getIntent ().getExtras ().getSerializable (Category.);
		}

		title.setText (articles.getTitle ());
		publishedAt.setText (articles.getPublishedAt ());
		newsDescription.setText (articles.getDescription ());

		Picasso.with (getBaseContext ()).load (articles.getImgURL ()).into (imageView);
	}
}
