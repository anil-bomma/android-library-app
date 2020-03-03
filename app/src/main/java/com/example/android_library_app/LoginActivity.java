package com.example.android_library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText emailIdET, passwordET;
    TextView registerTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // register screen intent.
        registerTV = findViewById(R.id.registerTV);
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegistrationScreen.
                        class);
                startActivity(intent);
            }
        });
    }



    // will triggered back to main activity with login is success
    // if user not authorized stay in this activity only (don't call intent logic)
    public void authenticateUsernamePassword(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("role", "admin");
        intent.putExtra("userId", "1234");
        MainActivity.loginStatus = true;
        startActivity(intent);
        finish();
    }
}
