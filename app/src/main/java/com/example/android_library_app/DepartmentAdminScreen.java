package com.example.android_library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DepartmentAdminScreen extends AppCompatActivity {

    Button deptAdmin_listBTN,deptAdmin_addBookBTN,deptAdmin_profileBTN,deptAdmin_settingsBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_admin_screen);

         deptAdmin_listBTN = findViewById(R.id.deptAdmin_listBTN);
        deptAdmin_listBTN.setOnClickListener(new OnClickListener(){
        @Override
        public void onClick (View v){
        Intent intent = new Intent(DepartmentAdminScreen.this, ListOfBooks.class);
        startActivity(intent);
        }
        });
    }

}