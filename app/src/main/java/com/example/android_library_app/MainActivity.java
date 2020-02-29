package com.example.android_library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String userRole;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the role from login intent and trigger to new intent based on role
        Intent intent = getIntent();
        userRole = intent.getStringExtra("role");
        userId = intent.getStringExtra("userId");

        if ((userRole == null || userRole.isEmpty())) {
            userRole = "";
        }

        switch (userRole) {
            case "admin":
                Log.d("mainActivity", "------admin user------");
                Intent adminScreen = new Intent(MainActivity.this, AdminScreen.class);
                adminScreen.putExtra("userId", userId);
                startActivity(adminScreen);
                finish();
                break;
            case "departmentAdmin":
                Log.d("mainActivity", "------departmentAdmin------");
                Intent departmentAdminScreen = new Intent(MainActivity.this, DepartmentAdminScreen.class);
                departmentAdminScreen.putExtra("userId", userId);
                startActivity(departmentAdminScreen);
                finish();
                break;
            case "user":
                Log.d("mainActivity", "------user------");
                Intent userScreen = new Intent(MainActivity.this, UserScreen.class);
                userScreen.putExtra("userId", userId);
                startActivity(userScreen);
                finish();
                break;
            default:
                Log.d("mainActivity", "Invalid user, please contact library help desk");
                Intent returnToLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(returnToLogin);
                Toast.makeText(MainActivity.this,
                        "Invalid user, please contact library help desk",
                        Toast.LENGTH_LONG
                ).show();
        }


        TextView homeId = findViewById(R.id.homeId);
        homeId.setText(("userRole: " + userRole));
    }
    
}
