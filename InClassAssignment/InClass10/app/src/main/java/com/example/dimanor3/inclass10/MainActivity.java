package com.example.dimanor3.inclass10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

public class MainActivity extends AppCompatActivity {
	private OkHttpClient client = new OkHttpClient ();

	EditText email, password;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_main);

		setTitle ("Chat Room");

		email = findViewById (R.id.editTextEmail);
		password = findViewById (R.id.editTextPassword);

		findViewById (R.id.buttonLogin).setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				if (email == null || email.getText ().toString ().equals ("")) {
					Toast.makeText (MainActivity.this, "What's your email?", Toast.LENGTH_SHORT).show ();
				}

				if (password == null || password.getText ().toString ().equals ("")) {
					Toast.makeText (MainActivity.this, "What's your password?", Toast.LENGTH_SHORT).show ();
				}
			}
		});

		findViewById (R.id.buttonSignUp).setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Intent intent = new Intent (MainActivity.this, SignUp.class);

				startActivity (intent);
			}
		});
	}

	public void performLogin (String username, String password) {
		RequestBody formBody = new FormBody.Builder ()
				.add ("email", username)
				.add ("Password", password)
				.build ();

		final Request request = new Request.Builder ()
				.url ("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/login")
				.post (formBody)
				.build ();

		client.newCall (request).enqueue (new Callback () {
			@Override
			public void onFailure (Call call, IOException e) {
				Log.d ("tempTAG", "onFailure: ");
			}

			public void onResponse (Call call, Response response) throws IOException {
				Log.d ("tempTAG", "onResponse: " + String.valueOf (Thread.currentThread ().getId ()));
				String str = response.body ().string ();
				Log.d ("tempTAG", "onResponse: " + str);

				Gson gson = new Gson ();

				TokenResponse tokenResponse = gson.fromJson (str, TokenResponse.class);

				Log.d ("tempTAG", "onResponse: " + tokenResponse.getToken ());

				getThreadList (tokenResponse.getToken ());
			}
		});
	}

	public void getThreadList (String token) {
		final String t = token;

		client = new OkHttpClient.Builder ()
				.authenticator (new Authenticator () {
					@Override
					public Request authenticate (Route route, Response response) throws IOException {
						if (response.request ().header ("Authorization") != null) {
							return null; // Give up, we've already attempted to authenticate.
						}

						System.out.println ("Authenticating for response: " + response);
						System.out.println ("Challenges: " + response.challenges ());
//						String credential = Credentials.basic ("jesse", "password1");
						return response.request ().newBuilder ()
								.header ("Authorization", t)
								.build ();
					}
				})
				.build ();

		Request request = new Request.Builder ()
				.url ("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread")
				.build ();

		try (Response response = client.newCall (request).execute ()) {
			if (!response.isSuccessful ())
				throw new IOException ("Unexpected code " + response);

			Headers responseHeaders = response.headers ();

			for (int i = 0; i < responseHeaders.size (); i++) {
				System.out.println (responseHeaders.name (i) + ": " + responseHeaders.value (i));
			}

			System.out.println (response.body ().string ());
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}
}
