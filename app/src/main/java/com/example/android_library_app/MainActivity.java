package com.example.android_library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the role from login intent and trigger to new intent based on role
        Intent intent = getIntent();
        String userRole = intent.getStringExtra("role");
        TextView homeId = findViewById(R.id.homeId);
        homeId.setText(("userRole: " + userRole));
    }


}
