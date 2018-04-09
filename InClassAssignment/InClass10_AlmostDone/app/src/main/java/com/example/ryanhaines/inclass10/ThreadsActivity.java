package com.example.ryanhaines.inclass10;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.sql.Array;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.preference.PreferenceManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ThreadsActivity extends AppCompatActivity {
    TextView name;
    ImageButton logoutBtn;
    ImageButton sendBtn;
    ImageButton deleteBtn;
    EditText addThreadText;
    private final OkHttpClient client = new OkHttpClient();
    ArrayList<ThreadResponse> threads = new ArrayList<>();
    final Handler handler = new Handler();
    public ProgressBar progressBar;
    public ListView listview;
    public ThreadAdapter adapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);

        name = findViewById(R.id.nameText);
        logoutBtn = findViewById(R.id.logoutBtn);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        sendBtn = findViewById(R.id.addBtn);
        addThreadText = findViewById(R.id.addThreadText);
        progressBar.setVisibility(View.VISIBLE);

        setTitle("Message Threads");
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("token", "defaultStringIfNothingFound");
        getThreadList(token);
        int userID = sharedPreferences.getInt("ID", 0);


        listview = (ListView)findViewById(R.id.listView);
        adapter = new ThreadAdapter(this, R.layout.thread_adapter, threads);
        listview.setAdapter(adapter);
        //listview.setVisibility(View.INVISIBLE);

        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ThreadResponse selectedThread = threads.get(i);
                adapter.clear();
                int messageID = selectedThread.getId();
                String selectedTitle = selectedThread.getTitle();
                int thread_id = selectedThread.getId();
                saveMessageID(messageID);
                Intent intent = new Intent(ThreadsActivity.this, MessageActivity.class);
                intent.putExtra("title", selectedTitle);
                intent.putExtra("thread_id",thread_id);
                startActivity(intent);
                finish();
            }
        });

        String nameStr = sharedPreferences.getString("name", "no name");

        name.setText(nameStr);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteToken();
                Intent intent = new Intent(ThreadsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleText = addThreadText.getText().toString();
                adapter.clear();
                addThreadList(token, titleText);
                addThreadText.setText("");
            }
        });


    }


    public void deleteToken(){
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", null);
        editor.commit();
    }

    public void getThreadList(final String token) {
        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread")
                .header("Authorization", "Bearer " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("threadActivityDemo", "Failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("threadActivityDemo", "Response Made");
                String str = response.body().string();
                Log.d("threadActivityDemo", str);
                try {
                    JSONObject jsonThread = new JSONObject(str);
                    JSONArray threadArray = jsonThread.getJSONArray("threads");

                    for (int  i = 0; i < threadArray.length() ; i++ ) {
                        JSONObject thread = (JSONObject) threadArray.get(i);
                        threads.add(new ThreadResponse(
                                thread.getString("user_fname"),
                                thread.getString("user_lname"),
                                thread.getString("title"),
                                thread.getString("created_at"),
                                thread.getInt("user_id"),
                                thread.getInt("id")
                        ));


                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        });
    }

    public void saveMessageID(int messageID){
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("messageID", messageID);
        editor.commit();
    }

    public void deleteThreadList(final String token, int thread_id) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread/delete/" + thread_id)
                .header("Authorization", "Bearer " + token)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("threadAddDemo", "Failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("threadDelete", "Response Made");
                String str = response.body().string();
                Log.d("threadDelete", str);
                getThreadList(token);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

            }
        });
    }

    public void addThreadList(final String token, String title) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        RequestBody formBody = new FormBody.Builder()
                .add("title", title)
                .build();

        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread/add")
                .header("Authorization", "Bearer " + token)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("threadAddDemo", "Failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("threadAddDemo", "Response Made");
                String str = response.body().string();
                Log.d("threadAddDemo", str);
                getThreadList(token);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }

    public Activity getActivity() {
        return ThreadsActivity.this;
    }
}
