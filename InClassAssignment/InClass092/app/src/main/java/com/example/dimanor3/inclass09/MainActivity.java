package com.example.dimanor3.inclass09;

import android.content.res.AssetManager;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
	private GoogleMap mMap;

	private String json;

	private ArrayList<LatLng> points = new ArrayList<> ();

	PolylineOptions polylineOptions;
	Polyline polyline;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_main);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager ()
				.findFragmentById (R.id.map);
		mapFragment.getMapAsync (this);

		try {
			InputStream is = getResources ().openRawResource (R.raw.trip);
			int size = is.available ();
			byte[] buffer = new byte[size];
			is.read (buffer);
			is.close ();
			json = new String (buffer, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace ();
		}

		try {
			JSONObject jsonObject = new JSONObject (json);
			JSONArray pointArray = jsonObject.getJSONArray ("points");

			for (int i = 0; i < pointArray.length (); i++) {
				JSONObject pointArrayJSON = pointArray.getJSONObject (i);

				points.add (new LatLng (pointArrayJSON.getDouble ("latitude"), pointArrayJSON.getDouble ("longitude")));
			}
		} catch (JSONException e) {
			e.printStackTrace ();
		}

		polylineOptions = new PolylineOptions ().addAll (points);
	}

	@Override
	public void onMapReady (GoogleMap googleMap) {
		mMap = googleMap;

		polyline = mMap.addPolyline (polylineOptions);

		Marker start, end;

		start = mMap.addMarker (new MarkerOptions ()
				.position (points.get (0))
				.title ("Start Point"));
		start.setTag (0);

		end = mMap.addMarker (new MarkerOptions ()
				.position (points.get (points.size () - 1))
				.title ("End Point"));
		end.setTag (0);


		mMap.setOnMapLoadedCallback (new GoogleMap.OnMapLoadedCallback () {
			@Override
			public void onMapLoaded () {
				LatLngBounds.Builder bounds = new LatLngBounds.Builder ();

				for (LatLng point: points) {
					bounds.include (point);
				}

				mMap.moveCamera (CameraUpdateFactory.newLatLngBounds (bounds.build (), 20));
			}
		});
	}
}
