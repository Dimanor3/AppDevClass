package com.example.ryanhaines.inclass10;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();

    EditText userNameText;
    EditText passwordText;
    Button loginBtn;
    Button signUpBtn;
    public String firstName;
    public String lastName;
    //User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameText = findViewById(R.id.nameEditText);
        passwordText = findViewById(R.id.passwordEditText);
        loginBtn = findViewById(R.id.loginBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        setTitle("ChatRoom");
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameText.getText().toString();
                String password = passwordText.getText().toString();
                performLogin(userName, password);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });


    }

    public void performLogin(String username, String password){

        RequestBody formBody = new FormBody.Builder()
                .add("email", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/login")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Demo", "Failure");
                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("Demo", "Response Made");
                String str = response.body().string();
                Log.d("Demo", "onResponse: " + str);


                if(str.contains("error")){
                    Gson gson = new Gson();
                    ErrorResponse errorResponse = gson.fromJson(str, ErrorResponse.class);
                    final String errorMessage = errorResponse.getMessage();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Gson gson = new Gson();
                    TokenResponse tokenResponse = gson.fromJson(str, TokenResponse.class);
                    Log.d("Demo", "onResponse: " + tokenResponse.getToken());

                    saveID(tokenResponse.getUser_id());

                    firstName = tokenResponse.getUser_fname();
                    lastName = tokenResponse.getUser_lname();

                    saveToken(tokenResponse.getToken());
                    saveName(firstName, lastName);
                    Intent intent = new Intent(MainActivity.this, ThreadsActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        });
    }

    public void saveToken(String token){
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.commit();
    }
    public void saveID(int id){
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("ID", id);
        Log.d("StandoutHelp", String.valueOf(id));
        editor.commit();
    }

    public void saveName(String fname, String lname){
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", fname + " "+ lname);
        editor.commit();
    }

}

