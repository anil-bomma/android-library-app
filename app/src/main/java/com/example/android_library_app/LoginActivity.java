package com.example.android_library_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth fAuth;

    EditText emailIdET, passwordET;
    TextView registerTV;
    Button loginBTN;

    public static String email = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailIdET = findViewById(R.id.emailIdET);
        passwordET = findViewById(R.id.passwordET);

        // register screen intent.
        registerTV = findViewById(R.id.registerTV);
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationScreen.
                        class);
                startActivity(intent);
            }
        });

        // login code
        fAuth = FirebaseAuth.getInstance();
        loginBTN = findViewById(R.id.loginBTN);
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailIdET.getText().toString().trim();
                if(email.isEmpty()) {
                    emailIdET.setError("Please enter your mail ID");
                }
                else {
                    String password = passwordET.getText().toString().trim();
                    if(password.isEmpty()) {
                        passwordET.setError("Please enter your password");
                    }
                    else {
                        authenticateUsernamePassword(email, password);
                    }
                }
            }
        });
    }


    // will triggered back to main activity with login is success
    // if user not authorized stay in this activity only (don't call intent logic)
    public void authenticateUsernamePassword(String email, String password) {

        // authenticate the user
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(
                            LoginActivity.this,
                            "Logged in Successfully",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userId", fAuth.getUid());
                    MainActivity.loginStatus = true;
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(
                            LoginActivity.this,
                            "Invalid credentials Please try again " + task.getException().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
