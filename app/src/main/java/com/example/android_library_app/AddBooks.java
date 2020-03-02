package com.example.android_library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddBooks extends AppCompatActivity {

    Button add_booksBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);

        add_booksBTN = findViewById(R.id.add_booksBTN);
        add_booksBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddBooks.this, "Book Added to the database", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
