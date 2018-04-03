package com.example.dimanor3.inclass10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUp extends AppCompatActivity {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_sign_up);

		setTitle ("Sign Up");

		findViewById (R.id.buttonCancel).setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				finish ();
			}
		});
	}
}
