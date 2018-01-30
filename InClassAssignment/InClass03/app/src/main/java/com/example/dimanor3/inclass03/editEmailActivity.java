package com.example.dimanor3.inclass03;

import android.content.Intent;
import android.opengl.ETC1;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.dimanor3.inclass03.MainActivity.student;

public class EditEmailActivity extends AppCompatActivity {

    EditText email;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        setContentView (R.layout.edit_email);

        Student student = MainActivity.student;

        email = (EditText) findViewById (R.id.email);
    }

    public void saveEdit (View v) {
        Button button = (Button) v;

        student.setEmail (email.getText ().toString ());

        Intent intent = new Intent (this, DisplayActivity.class);

        startActivity (intent);
    }
}
