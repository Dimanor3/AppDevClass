package com.example.dimanor3.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import static com.example.dimanor3.inclass03.MainActivity.student;

public class EditDepartmentActivity extends AppCompatActivity {

    RadioGroup radioGroup;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        setContentView (R.layout.edit_department);

        Student student = MainActivity.student;

        radioGroup = (RadioGroup) findViewById (R.id.radioGroup);
    }

    public void saveEdit (View v) {
        Button button = (Button) v;

        int id = radioGroup.getCheckedRadioButtonId ();

        if (id == R.id.radioSIS) {
            student.setDepartment ("SIS");
        } else if (id == R.id.radioCS) {
            student.setDepartment ("CS");
        } else if (id == R.id.radioBIO) {
            student.setDepartment ("BIO");
        } else {
            student.setDepartment ("Others");
        }

        Intent intent = new Intent (this, DisplayActivity.class);

        startActivity (intent);
    }
}
