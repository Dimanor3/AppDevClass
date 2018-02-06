/*
* Assignment#:  InClass 03
* File Name:    PasswordGenerator.java
* Full Name:    Bijan Razavi, Kushal Tiwari
* */

package com.example.dimanor3.group3_inclass04;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PasswordGenerator extends AppCompatActivity {
    TextView passwordCount, passwordLength;
    SeekBar passC, passL;
    Util passGen;
    LinkedList<String> passwords = new LinkedList<> ();

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_generator);

        setTitle ("Password Generator");

        passwordCount = (TextView) findViewById (R.id.pC);
        passwordLength = (TextView) findViewById (R.id.pL);
        passC = (SeekBar) findViewById (R.id.passwordCount);
        passL = (SeekBar) findViewById (R.id.passwordLength);

        ProgressDialog progressDialog;

        ExecutorService threadPool;

        passGen = new Util ();

        passC.setOnSeekBarChangeListener (new SeekBar.OnSeekBarChangeListener () {
            @Override
            public void onProgressChanged (SeekBar seekBar, int i, boolean b) {
                passwordCount.setText (String.format ("%s", i));
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
                passwordLength.setText (String.format ("%s", i));
            }

            @Override
            public void onStartTrackingTouch (SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch (SeekBar seekBar) {

            }
        });

        handler = new Handler (new Handler.Callback () {
            @Override
            public boolean handleMessage (Message message) {
                return false;
            }
        });

        threadPool = Executors.newFixedThreadPool (2);
    }

    class DoWork implements Runnable {
        @Override
        public void run () {
            for (int i = 0; i < passC.getProgress (); i++) {
                passwords.add (passGen.getPassword (passL.getProgress ()));
            }
        }
    }

    public void async (View v) {
        Button button = (Button) v;


    }

    public void thread (View v) {
        Button button = (Button) v;


    }
}
