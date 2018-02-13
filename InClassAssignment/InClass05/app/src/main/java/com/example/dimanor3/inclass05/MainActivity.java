/*
* Assignment#: InClass05
* File Name: MainActivity.java
* Full Name: Bijan Razavi, Kushal Tiwari
* */

package com.example.dimanor3.inclass05;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    LinkedList<String> data;

    TextView searchKeyword;
    String newText = "";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        searchKeyword = (TextView) findViewById (R.id.searchKeywordTextView);
    }

    public void go (View v) {
        if (isConnected ()) {
            new GetDataKeywordAsync ().execute (" http://dev.theappsdr.com/apis/photos/keywords.php");

        } else {
            Toast.makeText (MainActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show ();
        }
    }

    public void previous (View v) {

    }

    public void next (View v) {

    }

    public void handleData (LinkedList<String> data) {
        this.data = data;
        final CharSequence[] charSequence = data.toArray (new CharSequence[data.size ()]);

        AlertDialog.Builder builder = new AlertDialog.Builder (this);

        builder.setTitle ("Choose a Keyword")
            .setItems (charSequence, new DialogInterface.OnClickListener () {
                @Override
                public void onClick (DialogInterface dialogInterface, int which) {
                    newText = charSequence[which].toString ();

                    searchKeyword.setText (newText);

                    new GetDataKeywordAsync ().execute ();
                }
            });

        final AlertDialog alertDialog = builder.create ();

        alertDialog.show ();
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

    private class GetDataPicLinkAsync extends AsyncTask <String, Void, LinkedList<String>> {
        BufferedReader reader = null;

        LinkedList<String> linkedList = new LinkedList<String> ();

        HttpURLConnection connection = null;

        @Override
        protected LinkedList<String> doInBackground (String... params) {
            try {
                URL url = new URL (params[0]);

                connection = (HttpURLConnection) url.openConnection ();

                reader = new BufferedReader (new InputStreamReader (connection.getInputStream ()));

                String line = "";

                while ((line = reader.readLine ()) != null) {
                    for (String keyword: line.split ("")) {
                        linkedList.add (keyword);
                    }
                }

                return linkedList;
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
        protected void onPostExecute (LinkedList<String> result) {
            if (result != null) {
                handleData (result);
            } else {
                Log.d ("demo", "null result");
            }
        }
    }

    private class GetDataKeywordAsync extends AsyncTask <String, Void, LinkedList<String>> {
        BufferedReader reader = null;
        
        LinkedList<String> linkedList = new LinkedList<String> ();

        HttpURLConnection connection = null;

        @Override
        protected LinkedList<String> doInBackground (String... params) {
            try {
                URL url = new URL (params[0]);
                connection = (HttpURLConnection) url.openConnection ();

                reader = new BufferedReader (new InputStreamReader (connection.getInputStream ()));

                String line = "";

                while ((line = reader.readLine ()) != null) {
                    for (String keyword: line.split (";")) {
                        linkedList.add (keyword);
                    }
                }
                
                return linkedList;
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
        protected void onPostExecute (LinkedList<String> result) {
            if (result != null) {
                handleData (result);
            } else {
                Log.d ("demo", "null result");
            }
        }
    }
}
