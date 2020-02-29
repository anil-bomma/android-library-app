package com.example.android_library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText usernameET, passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    // will triggered back to main activity with login is success
    // if user not authorized stay in this activity only (don't call intent logic)
    public void authenticateUsernamePassword(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("role", "admin");
        startActivity(intent);
        finish();
    }
}
