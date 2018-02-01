/*
* Assignment#:	HW1
* File Name:	MainActivity.java
* Full Name:	Bijan Razavi, Kushal Tiwari
* */

package com.example.dimanor3.hw1_groups3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.nio.channels.SelectionKey;

public class MainActivity extends AppCompatActivity {

    SeekBar percentage;
    TextView showPercentage, tipAmount, totalAmount;
    EditText enterBillValue;
    String percentValue, tip, total;
    float tipValue, totalValue;
    int id;
    RadioGroup radioGroup;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        setTitle ("Tip Calculator");

		enterBillValue = (EditText) findViewById (R.id.billValue);

		enterBillValue.setError ("Enter Bill Total!");

        // Tip amount and total amount
		tipAmount = (TextView) findViewById (R.id.tipAmount);
		totalAmount = (TextView) findViewById (R.id.totalAmount);

		// Percent amounts
		radioGroup = (RadioGroup) findViewById (R.id.radioGroup);
		id = R.id.percent1;

        // Custom tip percentage
        percentage = (SeekBar) findViewById (R.id.percentage);
        showPercentage = (TextView) findViewById (R.id.showPercentage);

        percentage.setProgress (25);
		percentValue = String.format ("%s", percentage.getProgress ()) + "%";
        showPercentage.setText (percentValue);

        percentage.setOnSeekBarChangeListener (new SeekBar.OnSeekBarChangeListener () {
            @Override
            public void onProgressChanged (SeekBar seekBar, int i, boolean b) {
				percentValue = i + "%";
                showPercentage.setText (percentValue);

                if (id == R.id.customPercent) {
					total = "$" + Float.toString (Float.parseFloat (enterBillValue.getText ().toString ()) + getTipValue ());

					totalAmount.setText (total);
				}
            }

            @Override
            public void onStartTrackingTouch (SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch (SeekBar seekBar) {

            }
        });

		radioGroup.setOnCheckedChangeListener (new RadioGroup.OnCheckedChangeListener () {
			@Override
			public void onCheckedChanged (RadioGroup radioGroup, int i) {
				id = i;

				total = "$" + Float.toString (Float.parseFloat (enterBillValue.getText ().toString ()) + getTipValue ());

				totalAmount.setText (total);
			}
		});

		enterBillValue.addTextChangedListener (new TextWatcher () {
			@Override
			public void beforeTextChanged (CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged (CharSequence charSequence, int i, int i1, int i2) {
				if ("".contains (enterBillValue.getText ().toString ())) {
					tip = "$0.00";
					total = "$0.00";

					tipAmount.setText (tip);

					enterBillValue.setError ("Enter Bill Total!");
				} else {
					total = "$" + Float.toString (Float.parseFloat (enterBillValue.getText ().toString ()) + getTipValue ());

					enterBillValue.setError (null);
				}

				totalAmount.setText (total);
			}

			@Override
			public void afterTextChanged (Editable editable) {

			}
		});
    }

    public void exit (View v) {
        Button button = (Button) v;

        finish ();

        System.exit (0);
    }

    private float getTipValue () {
		if (id == R.id.percent1) {
			tipValue = .1f * Float.parseFloat (enterBillValue.getText ().toString ());
			tip = "$" + String.format ("%s", tipValue);
			tipAmount.setText (tip);
		} else if (id == R.id.percent2) {
			tipValue = .15f * Float.parseFloat (enterBillValue.getText ().toString ());
			tip = "$" + String.format ("%s", tipValue);
			tipAmount.setText (tip);
		} else if (id == R.id.percent3) {
			tipValue = .18f * Float.parseFloat (enterBillValue.getText ().toString ());
			tip = "$" + String.format ("%s", tipValue);
			tipAmount.setText (tip);
		} else {
			tipValue = ((float) percentage.getProgress () / 100f) * Float.parseFloat (enterBillValue.getText ().toString ());
			tip = "$" + String.format ("%s", tipValue);
			tipAmount.setText (tip);
		}

    	return tipValue;
	}
}
