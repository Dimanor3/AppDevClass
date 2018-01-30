package com.example.dimanor3.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import static com.example.dimanor3.inclass03.MainActivity.student;

public class EditMoodActivity extends AppCompatActivity {

    SeekBar mood;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        setContentView (R.layout.edit_mood);

        Student student = MainActivity.student;

        mood = (SeekBar) findViewById (R.id.seekBar2);
    }

    public void saveEdit (View v) {
        Button button = (Button) v;

        student.setMood (mood.getProgress ());

        Intent intent = new Intent (this, DisplayActivity.class);

        startActivity (intent);
    }
}
