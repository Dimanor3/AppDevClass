/*
* Assignment#:  InClass 03
* File Name:    MainActivity.java
* Full Name:    Bijan Razavi, Kushal Tiwari
* */

package com.example.dimanor3.group3_inclass04;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        setTitle ("Welcome!!!");

        new Handler ().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent = new Intent (this, PasswordGenerator.class);

                //startActivity (intent);
            }
        }, 3000);
    }
}
