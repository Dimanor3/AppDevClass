/*
* Assignment #: 3
* File Name: MainActivity.java
* Full Name: Bijan Razavi, Kushal Tiwari
* */

package com.example.dimanor3.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText name, email;
    private RadioGroup radioGroup;
    private SeekBar seekBar;
    private Student student;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        //setContentView (R.layout.activity_main);
        setContentView (R.layout.activity_main);
        setTitle ("Main Activity");

        name = (EditText) findViewById (R.id.editTextName);
        email = (EditText) findViewById (R.id.editTextEmail);

        radioGroup = (RadioGroup) findViewById (R.id.radioGroup);
        seekBar = (SeekBar) findViewById (R.id.seekBar);
    }

    public void submit (View v) {
        Button button = (Button) v;

        int id = radioGroup.getCheckedRadioButtonId ();

        if (name.getText ().toString ().contains ("") || email.getText ().toString ().contains ("")) {
            Toast.makeText (getApplicationContext(), "You are missing some files", Toast.LENGTH_LONG).show();
        }

        student.name = name.getText ().toString ();
        student.email = email.getText ().toString ();

        student.mood = seekBar.getProgress ();

        if (id == R.id.radioSIS) {
            student.department = "SIS";
        } else if (id == R.id.radioCS) {
            student.department = "CS";
        } else if (id == R.id.radioBIO) {
            student.department = "BIO";
        } else {
            student.department = "Others";
        }

        Intent intent = new Intent (this, DisplayActivity.class);

        startActivity (intent);
    }
}
