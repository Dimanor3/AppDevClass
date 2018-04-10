package com.example.dimanor3.inclass11;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScreenSlidePageFragment extends Fragment {

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate (
				R.layout.viewpager_item, container, false);

		return rootView;
	}
}