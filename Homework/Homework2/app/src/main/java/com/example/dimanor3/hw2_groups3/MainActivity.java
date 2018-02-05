/*
* Homework Assignment:  2
* File Name:            MainActivity.java
* Full Name:            Bijan Razavi, Kushal Tiwari
* */

package com.example.dimanor3.hw2_groups3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        setTitle ("View Tasks");
    }

    public void makeNewTask (View v) {
        Log.d ("Test", "ADD NEW!!!");
    }

    public void gotoFirst (View v) {
        Log.d ("Test", "FIRST!!!");
    }

    public void gotoPrevious (View v) {
        Log.d ("Test", "PREVIOUS!!!");
    }

    public void edit (View v) {
        Log.d ("Test", "EDIT!!!");
    }

    public void delete (View v) {
        Log.d ("Test", "DELETED!!!");
    }

    public void gotoNext (View v) {
        Log.d ("Test", "NEXT!!!");
    }

    public void gotoLast (View v) {
        Log.d ("Test", "LAST!!!");
    }
}
