package com.example.ryanhaines.finalpractice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthException;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {

    EditText emailText;
    EditText passwordText;
    Button loginBtn;
    Button signupBtn;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        setTitle("Login");


        signupBtn = findViewById(R.id.signUpBtn);
        loginBtn = findViewById(R.id.loginBtn);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, SignUpActivity.class);
                startActivity (intent);
                finish ();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = emailText.getText().toString();
                String password = passwordText.getText().toString();
                Log.d("demo", userName);
                Log.d("demo", password);
                performLogin(userName, password);

            }
        });

    }

    public void performLogin(String username, String password){

        //Login with Firebase, but use Firebase.getInstance to work
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("demo", "Successfully signed in with email !");
                            AuthResult result = task.getResult();
                            startActivity(new Intent (MainActivity.this, ListActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Error " +task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
