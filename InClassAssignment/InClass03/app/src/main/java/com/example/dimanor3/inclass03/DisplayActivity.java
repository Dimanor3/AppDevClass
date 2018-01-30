package com.example.dimanor3.inclass03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    private TextView name, email, department, mood;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        setContentView (R.layout.display_activity);

        setTitle ("Display Activity");

        Student student = MainActivity.student;

        name = (TextView) findViewById (R.id.name);
        email = (TextView) findViewById (R.id.email);
        department = (TextView) findViewById (R.id.department);
        mood = (TextView) findViewById (R.id.mood);
    }
}
