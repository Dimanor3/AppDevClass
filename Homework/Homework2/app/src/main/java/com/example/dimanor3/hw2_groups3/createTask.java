/*
* Homework Assignment:  2
* File Name:            createTask.java
* Full Name:            Bijan Razavi, Kushal Tiwari
* */

package com.example.dimanor3.hw2_groups3;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import java.util.Locale;

public class createTask extends AppCompatActivity {

    task t;
    EditText date, time, title;
    RadioGroup radioGroup;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        title = (EditText) findViewById (R.id.title);
        date = (EditText) findViewById (R.id.date);
        time = (EditText) findViewById (R.id.time);
        radioGroup = (RadioGroup) findViewById (R.id.radioGroup);

        t = new task (title.getText ().toString (), "High", date.getText ().toString (), time.getText ().toString ());
    }

    public void save (View v) {
        Button b = (Button) v;

        t.setDate (date.getText ().toString ());
        t.setTime (time.getText ().toString ());
        t.setTitle (title.getText ().toString ());

        id = radioGroup.getCheckedRadioButtonId ();

        if (id == R.id.high) {
            t.setPriority ("High");
        } else if (id == R.id.medium) {
            t.setPriority ("Medium");
        } else {
            t.setPriority ("Low");
        }

        /*
        Intent returnIntent = new Intent ();
        returnIntent.putExtra("result", t);
        setResult(Activity.RESULT_OK,returnIntent);
        finish ();
        */
    }
}
