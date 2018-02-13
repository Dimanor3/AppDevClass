package com.example.dimanor3.inclass05;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        if (isConnected ()) {
            new GetDataAsync ().execute ();
        } else {
            Toast.makeText (MainActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show ();
        }
    }

    private boolean isConnected () {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo ();

        if (networkInfo == null || !networkInfo.isConnected () ||
                (networkInfo.getType () != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType () != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }

        return true;
    }

    private class GetDataAsync extends AsyncTask <String, Void, String> {
        BufferedReader reader;

        StringBuilder stringBuilder = new StringBuilder ();

        HttpURLConnection connection;
        @Override
        protected String doInBackground (String... params) {
            try {
                URL url = new URL (params[0]);
                connection = (HttpURLConnection) url.openConnection ();

                reader = new BufferedReader (new InputStreamReader (connection.getInputStream ()));

                String line = "";

                while ((line = reader.readLine ()) != null) {
                    stringBuilder.append (line);
                }

                return stringBuilder.toString ();
            } catch (MalformedURLException e) {
                e.printStackTrace ();
            } catch (IOException e) {
                e.printStackTrace ();
            } finally {
                if (connection != null) {
                    connection.disconnect ();
                }

                if (reader != null) {
                    try {
                        reader.close ();
                    } catch (IOException e) {
                        e.printStackTrace ();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute (String result) {
            if (result != null) {
                Log.d ("demo", result);
            } else {
                Log.d ("demo", "null result");
            }
        }
    }
}
