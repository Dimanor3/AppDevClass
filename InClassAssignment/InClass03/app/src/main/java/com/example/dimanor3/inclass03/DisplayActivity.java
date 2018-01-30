package com.example.dimanor3.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    private TextView name, email, department, mood;
    private String strName, strEmail, strDepartment, strMood;

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

        strName = name.getText ().toString () + " " + student.getName ();
        strEmail = email.getText ().toString () + " " + student.getEmail ();
        strDepartment = department.getText ().toString () + " " + student.getDepartment ();
        strMood = mood.getText ().toString () + " " + student.getMood () + "%";

        name.setText (strName);
        email.setText (strEmail);
        department.setText (strDepartment);
        mood.setText (strMood);
    }

    public void editName (View v) {
        Button button = (Button) v;

        Intent intent = new Intent (this, EditNameActivity.class);

        startActivity (intent);
    }

    public void editEmail (View v) {
        Button button = (Button) v;

        Intent intent = new Intent (this, EditEmailActivity.class);

        startActivity (intent);
    }

    public void editDepartment (View v) {
        Button button = (Button) v;

        Intent intent = new Intent (this, EditDepartmentActivity.class);

        startActivity (intent);
    }

    public void editMood (View v) {
        Button button = (Button) v;

        Intent intent = new Intent (this, EditMoodActivity.class);

        startActivity (intent);
    }
}
