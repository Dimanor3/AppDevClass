/*
* Assignment #: 3
* File Name: EditNameActivity.java
* Full Name: Bijan Razavi, Kushal Tiwari
* */

package com.example.dimanor3.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.dimanor3.inclass03.MainActivity.student;

public class EditNameActivity extends AppCompatActivity {

    EditText name;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        setContentView (R.layout.edit_name);

        Student student = MainActivity.student;

        name = (EditText) findViewById (R.id.name);
    }

    public void saveEdit (View v) {
        Button button = (Button) v;

        if (!"".contains (name.getText ().toString ())) {
            student.setName (name.getText ().toString ());
        }

        Intent intent = new Intent (this, DisplayActivity.class);

        startActivity (intent);
    }
}
