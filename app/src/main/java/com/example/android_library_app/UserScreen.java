package com.example.android_library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserScreen extends AppCompatActivity {

    Button user_listBTN,user_profileBTN,user_settingsBTN,user_borrowBooksBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);

        user_listBTN = findViewById(R.id.user_listBTN);
        user_listBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                Intent intent = new Intent(UserScreen.this, ListOfBooks.class);
                startActivity(intent);
            }
        });
    }
}
