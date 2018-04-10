package com.example.ryanhaines.inclass10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class SignUpActivity extends AppCompatActivity {

    EditText firstNameText;
    EditText lastNameText;
    EditText emailText;
    EditText passwordText;
    EditText repPasswordText;
    Button cancelBtn;
    Button signUpBtn;

    public String firstName;
    public String lastName;

    private final OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        firstNameText = findViewById(R.id.firstNameText);
        lastNameText = findViewById(R.id.lastNameText);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        repPasswordText = findViewById(R.id.repPasswordText);
        cancelBtn = findViewById(R.id.cancelBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        setTitle("Sign Up");
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String firstName = firstNameText.getText().toString();
                String lastName = lastNameText.getText().toString();
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String repPassword = repPasswordText.getText().toString();

                if(password.equals(repPassword)){
                    performSignUp(email, password, firstName, lastName);
                }else{
                    Toast.makeText(SignUpActivity.this, "Passwords Don't Match", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void performSignUp(String email, String password, String fname, String lname){

        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("fname", fname)
                .add("lname", lname)
                .build();
        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/signup")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Demo", "Failure");
                Toast.makeText(SignUpActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
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
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    Gson gson = new Gson();
                    TokenResponse tokenResponse = gson.fromJson(str, TokenResponse.class);

                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_LONG).show();
                        }
                    });
                    Log.d("Demo", "onResponse: " + tokenResponse.getToken());
                    firstName = tokenResponse.getUser_fname();
                    lastName = tokenResponse.getUser_lname();
                    saveName(firstName, lastName);
                    saveToken(tokenResponse.getToken());
                    Intent intent = new Intent(SignUpActivity.this, ThreadsActivity.class);
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

    public void saveName(String fname, String lname){
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", fname + " "+ lname);
        editor.commit();
    }
}
