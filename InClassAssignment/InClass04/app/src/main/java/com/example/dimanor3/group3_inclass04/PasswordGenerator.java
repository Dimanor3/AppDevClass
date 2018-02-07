/*
* Assignment#:  InClass 03
* File Name:    PasswordGenerator.java
* Full Name:    Bijan Razavi, Kushal Tiwari
* */

package com.example.dimanor3.group3_inclass04;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PasswordGenerator extends AppCompatActivity {
    TextView passwordCount, passwordLength, displaySelectedPassword;
    SeekBar passC, passL;
    LinkedList<String> passwords = new LinkedList<> ();
    String selectedPassword;
    int count, length;

    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    Handler handler;

	ProgressDialog progressDialog;

	ExecutorService threadPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_generator);

        setTitle ("Password Generator");

		count = 1;
		length = 8;

        // Progress Dialog Setup
		progressDialog = new ProgressDialog (this);
		progressDialog.setMessage ("Creating New Passwords!");
		progressDialog.setMax (count);
		progressDialog.setProgressStyle (ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable (false);

		builder = new AlertDialog.Builder (this).setTitle ("Your Generated Passwords:").setCancelable (true);

        passwordCount = (TextView) findViewById (R.id.pC);
        passwordLength = (TextView) findViewById (R.id.pL);
		displaySelectedPassword = (TextView) findViewById (R.id.dispSelectedPass);
        passC = (SeekBar) findViewById (R.id.passwordCount);
        passL = (SeekBar) findViewById (R.id.passwordLength);

		threadPool = Executors.newFixedThreadPool (2);

        passC.setOnSeekBarChangeListener (new SeekBar.OnSeekBarChangeListener () {
            @Override
            public void onProgressChanged (SeekBar seekBar, int i, boolean b) {
                count = i + 1;

				progressDialog.setMax (count);

                passwordCount.setText (String.format ("%s", count));
            }

            @Override
            public void onStartTrackingTouch (SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch (SeekBar seekBar) {

            }
        });

        passL.setOnSeekBarChangeListener (new SeekBar.OnSeekBarChangeListener () {
            @Override
            public void onProgressChanged (SeekBar seekBar, int i, boolean b) {
                length = i + 8;

                passwordLength.setText (String.format ("%s", length));
            }

            @Override
            public void onStartTrackingTouch (SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch (SeekBar seekBar) {

            }
        });

        // The handler fully setup!
        handler = new Handler (new Handler.Callback () {
            @Override
            public boolean handleMessage (Message msg) {
            	switch (msg.what) {
					case DoWork.STATUS_START:
						progressDialog.setProgress (0);
						progressDialog.show ();
						break;

					case DoWork.STATUS_PROGRESS:
						passwords.add (msg.obj.toString ());

						Log.d ("test", "received pw: " + msg.obj);

						progressDialog.incrementProgressBy (1);
						break;

					case DoWork.STATUS_STOP:
						progressDialog.dismiss ();

						final String[] pass = passwords.toArray (new String[passwords.size ()]);

						builder.setItems (pass, new DialogInterface.OnClickListener () {
							@Override
							public void onClick (DialogInterface dialogInterface, int i) {
								Log.d ("test", "Pass: " + pass[i]);
								selectedPassword = "Password: " + pass[i];
								displaySelectedPassword.setText (selectedPassword);
							}
						});

						alertDialog = builder.create ();

						alertDialog.show ();

						for (int i = 0; i < count; i++) {
							Log.d ("complete", "Password " + (i + 1) + ": " + passwords.get (i));
						}

						passwords.clear ();
						break;
				}

                return false;
            }
        });
    }

    class DoWork implements Runnable {
    	static final int STATUS_START = 0x00;
		static final int STATUS_PROGRESS = 0x01;
		static final int STATUS_STOP = 0x02;

		String pw;

        @Override
        public void run () {
        	Message startMsg = new Message ();

        	startMsg.what = STATUS_START;

        	handler.sendMessage (startMsg);

			for (int i = 0; i < count; i++) {
				Message msg = new Message ();

                pw = Util.getPassword (length);

				msg.what = STATUS_PROGRESS;
				msg.obj = pw;

				handler.sendMessage (msg);
            }

			Message stopMsg = new Message ();

			stopMsg.what = STATUS_STOP;

			handler.sendMessage (stopMsg);
        }
    }

	public void thread (View v) {
		progressDialog.setProgress (0);

    	threadPool.execute (new DoWork ());
	}

    public void async (View v) {

    }
}
