package com.example.android_library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminScreen extends AppCompatActivity {

    Button  admin_addBookBTN, admin_deptAdminListBTN,
            admin_addOrRemoveDeptBTN, admin_profileBTN, admin_settingsBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);


        Button admin_listBTN = findViewById(R.id.admin_listBTN);
        admin_listBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminScreen.this,ListOfBooks.class);
                startActivity(intent);
            }
        });
    }


}
