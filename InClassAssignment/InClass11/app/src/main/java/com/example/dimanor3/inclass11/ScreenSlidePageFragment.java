package com.example.dimanor3.inclass11;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ScreenSlidePageFragment extends Fragment {
	ImageSearchItem imageSearchItem;

	ImageView imageView;
	TextView views, likes;

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate (
				R.layout.viewpager_item, container, false);

		imageView = rootView.findViewById (R.id.imageView);
		views = rootView.findViewById (R.id.textViewViews);
		likes = rootView.findViewById (R.id.textViewLikes);

		return rootView;
	}

	public ImageSearchItem getImageSearchItem () {
		return imageSearchItem;
	}

	public void setImageSearchItem (ImageSearchItem imageSearchItem) {
		this.imageSearchItem = imageSearchItem;

		views.setText (imageSearchItem.getViews ());
		likes.setText (imageSearchItem.getLikes ());
		Picasso.get ().load (imageSearchItem.getUrl ()).into (imageView);
	}
}