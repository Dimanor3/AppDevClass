/*
* Assignment #: 2
* File Name: InClass02
* Name:     Bijan Razavi
*           Kushal Tiwari
* */

package com.example.user.inclass02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText years, pounds, feet, inches;
    TextView bmi, category;
    String underweight = "BMI < 18.5", normal = "18.5 <= BMI < 25", overweight = "25 <= BMI < 25", obese = "30 <= BMI";
    double result;
    double height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        years = (EditText) findViewById (R.id.yearsEditText);
        pounds = (EditText) findViewById (R.id.poundsEditText);
        feet = (EditText) findViewById (R.id.feetEditText);
        inches = (EditText) findViewById (R.id.inchEditText);
        category = (TextView) findViewById (R.id.categoryText);
        bmi = (TextView) findViewById (R.id.bmiText);
    }

    public void onCalculate (View view) {
        if ("".equals (years.getText ().toString ()) || "".equals (pounds.getText ().toString ()) || "".equals (feet.getText ().toString ()) || "".equals (inches.getText ().toString ())) {
            Toast.makeText (getApplicationContext (), "You forgot to enter some information!", Toast.LENGTH_LONG).show ();
        } else if (Integer.parseInt (years.getText ().toString ()) < 18) {
            Toast.makeText (getApplicationContext (), "You are too young, you must be at least 18!", Toast.LENGTH_LONG).show ();
        } else {
            height = Integer.parseInt (feet.getText ().toString ()) * 12 + Integer.parseInt (inches.getText ().toString ());
            result = 703.0 * (Integer.parseInt (pounds.getText ().toString ()) / (height * height));

            String test = Double.toString (result);

            Log.d ("demo", test);
        }
    }
}
