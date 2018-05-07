package com.example.dimanor3.inclass11;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;

public class ViewPagerAdapter {
	Activity activity;
	LinkedList<ImageSearchItem> imageSearchItems;
	LayoutInflater inflater;

	public ViewPagerAdapter (Activity activity, LinkedList<ImageSearchItem> imageSearchItems) {
		this.activity = activity;
		this.imageSearchItems = imageSearchItems;
	}

	public int getCount () {
		return imageSearchItems.size ();
	}

	public boolean isViewFromObject (View view, Object object) {
		return view == object;
	}

	public Object instantiateItem (ViewGroup container, int position) {
		inflater = (LayoutInflater) activity.getApplicationContext ().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate (R.layout.viewpager_item, container, false);

		ImageView image;
		image = itemView.findViewById (R.id.imageView);
		DisplayMetrics dis = new DisplayMetrics ();
		activity.getWindowManager ().getDefaultDisplay ().getMetrics (dis);
		int height = dis.heightPixels, width = dis.widthPixels;
		image.setMinimumHeight (height);
		image.setMinimumWidth (width);

		TextView views, likes;
		views = itemView.findViewById (R.id.textViewViews);
		likes = itemView.findViewById (R.id.textViewLikes);

		views.setText (imageSearchItems.get (position).getViews ());
		likes.setText (imageSearchItems.get (position).getLikes ());

		try {
			Picasso.get ().load (imageSearchItems.get (position).getUrl ()).into (image);
		} catch (Exception e) {
			e.printStackTrace ();
		}

		container.addView (itemView);

		return itemView;
	}
}
