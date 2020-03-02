package com.example.android_library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    public void authenticateUser(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("role", "user");
        intent.putExtra("userId", "user1234");
        startActivity(intent);
//        finish();
    }

    public void authenticateAdmin(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("role", "admin");
        intent.putExtra("userId", "admin1234");
        startActivity(intent);
//        finish();
    }

    public void authenticateDeptAdmin(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("role", "departmentAdmin");
        intent.putExtra("userId", "departmentAdmin1234");
        startActivity(intent);
//        finish();
    }
}
