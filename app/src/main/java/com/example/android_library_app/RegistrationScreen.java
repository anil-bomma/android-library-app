package com.example.android_library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationScreen extends AppCompatActivity {
//    implements AdapterView.OnItemSelectedListener


    Button registerBTN;
    TextView loginTV;
    EditText firstnameET, lastnameET, studentIdET, emailIdET, passwordET;
//    Spinner rolesSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);


//        // Code for spinner for role attribute
//        rolesSP = findViewById(R.id.rolesSP);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
//                (this, R.array.roles,R.layout.spinner_drop_down_layout );
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        rolesSP.setAdapter(adapter);
//        // to store the element clicked.
//        rolesSP.setOnItemSelectedListener(this);

//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            String role = parent.getItemAtPosition(position).toString();
//            Toast.makeText(parent.getContext(),"Selected as "+role,Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//
//        }

        // clicked on register button.
        // will store the data and redirect to the login page
        registerBTN = findViewById(R.id.registerBTN);
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegistrationScreen.this,"Registered Successfully",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                setResult(1);
                finish();
            }
        });

        // clicked on already registered text view.
        // will redirect to the login page.
        loginTV = findViewById(R.id.loginTV);
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(1);
                finish();
            }
        });
    }


}
