package com.example.ryanhaines.finalpractice;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.common.util.IOUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static java.util.logging.Level.parse;

public class ListActivity extends AppCompatActivity {

    Switch favoritesOnly;
    TextView welcomeText;
    Button logoutBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mDatabase = FirebaseDatabase.getInstance().getReference("Test");


        setTitle("App List");

        mAuth = FirebaseAuth.getInstance();
        favoritesOnly = findViewById(R.id.switch1);
        welcomeText = findViewById(R.id.welcomeText);
        logoutBtn = findViewById(R.id.logoutBtn);

        new GetDataAsync ().execute ("http://itunes.apple.com/us/rss/topgrossingapplications/limit=30/json");


        //Proper way to logout of the Firebase
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent (ListActivity.this, MainActivity.class);
                startActivity (intent);
                finish ();
            }
        });
        //Display firebase Display name
        welcomeText.setText("Welcome "+ mAuth.getCurrentUser().getDisplayName());


    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }



    private class GetDataAsync extends AsyncTask<String, Void, ArrayList<Apps>>{
        @Override
        protected ArrayList<Apps> doInBackground(String... params) {
            HttpURLConnection connection = null;
            ArrayList<Apps> result = new ArrayList<>();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = org.apache.commons.io.IOUtils.toString(connection.getInputStream(), "UTF8");
                    Log.d("demo", "Connection Made");


                    JSONObject root = new JSONObject(json);
                    //Log.d("demo", root.toString());

                    String rootJson = root.getString("feed");
                    Log.d("test", rootJson);
                    JSONArray rootJSONArray = root.getJSONArray("author");
                    Log.d("demo", rootJSONArray.toString());

                    Log.d("demo", Integer.toString(rootJSONArray.length()));
                    for (int i=0;i<root.length();i++) {

                        JSONObject rootJSON = rootJSONArray.getJSONObject(i);

                        Apps app = new Apps();
                        app.title = rootJSON.getString("im:name");
                        Log.d("demo", app.title);
                        app.imgUrl = rootJSON.getString("image");
                        app.summary = rootJSON.getString("summary");
                        app.developerName = rootJSON.getString("im:artist");
                        Log.d("Test", app.summary);

                        result.add(app);
                    }

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }

    }
}
