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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {

    EditText firstNameText;
    EditText lastNameText;
    EditText emailText;
    EditText passwordText;
    EditText confirmPasswordText;
    Button cancelBtn;
    Button signUpBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("Sign Up");

        mAuth = FirebaseAuth.getInstance ();

        firstNameText = findViewById(R.id.firstNameText);
        lastNameText = findViewById(R.id.lastNameText);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        confirmPasswordText = findViewById(R.id.confirmPasswordText);
        cancelBtn = findViewById(R.id.cancelBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (SignUpActivity.this, MainActivity.class);
                startActivity (intent);
                finish ();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameText.getText ().toString ();
                String lastName = lastNameText.getText ().toString ();
                String email = emailText.getText ().toString ();
                String password = passwordText.getText ().toString ();
                String repPassword = confirmPasswordText.getText ().toString ();

                if (password.equals (repPassword)) {
                    performSignUp (email, password, firstName, lastName);
                } else {
                    Toast.makeText (SignUpActivity.this, "Passwords Don't Match", Toast.LENGTH_SHORT).show ();
                }
            }
        });


    }

    public void performSignUp (String email, String password, final String firstName, final String lastName) {

        //Sign User up with Firebase
        mAuth.createUserWithEmailAndPassword (email, password)
                .addOnCompleteListener (this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete (@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful ()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d ("demo", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser ();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder ()
                                    .setDisplayName (firstName + " " + lastName)
                                    .build ();
                            user.updateProfile (profileUpdates)
                                    .addOnCompleteListener (new OnCompleteListener<Void> () {
                                        @Override
                                        public void onComplete (@NonNull Task<Void> task) {
                                            if (task.isSuccessful ()) {
                                                Log.d ("demo", "User profile updated.");
                                                // startActivity(new Intent (SignUpActivity.this, ThreadsActivity.class));
                                            }
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w ("demo", "createUserWithEmail:failure", task.getException ());
                            Toast.makeText (SignUpActivity.this, "Failed." + task.getException ().getMessage (),
                                    Toast.LENGTH_SHORT).show ();
                        }
                    }
                });

        Intent intent = new Intent (SignUpActivity.this, ListActivity.class);
        startActivity (intent);
        finish ();
    }
}
