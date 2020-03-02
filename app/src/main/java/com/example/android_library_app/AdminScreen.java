package com.example.android_library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminScreen extends AppCompatActivity {

    Button  admin_listBTN, admin_addBookBTN, admin_deptAdminListBTN,
            admin_addOrRemoveDeptBTN, admin_profileBTN, admin_settingsBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);


        admin_listBTN = findViewById(R.id.admin_listBTN);
        admin_listBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminScreen.this,ListOfBooks.class);
                startActivity(intent);
            }
        });

        admin_addBookBTN = findViewById(R.id.admin_addBookBTN);
        admin_addBookBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminScreen.this,AddBooks.class);
                startActivity(intent);

            }

    });

        admin_addOrRemoveDeptBTN = findViewById(R.id.admin_addOrRemoveDeptBTN);
        admin_addOrRemoveDeptBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminScreen.this,AddRemoveDeptAdmin.class);
                startActivity(intent);

            }

        });

        admin_deptAdminListBTN = findViewById(R.id.admin_deptAdminListBTN);
        admin_deptAdminListBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminScreen.this,ListDeptAdmin.class);
                startActivity(intent);

            }

        });

        admin_profileBTN = findViewById(R.id.admin_profileBTN);
        admin_profileBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminScreen.this,UserProfile.class);
                startActivity(intent);

            }

        });

        admin_settingsBTN = findViewById(R.id.admin_settingsBTN);
        admin_addOrRemoveDeptBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminScreen.this,Settings.class);
                startActivity(intent);

            }

        });
    }


}
