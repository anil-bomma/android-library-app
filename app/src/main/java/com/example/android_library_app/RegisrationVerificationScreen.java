package com.example.android_library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisrationVerificationScreen extends AppCompatActivity {

    EditText verifyET;
    Button verifyBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regisration_verification_screen);

        // on clicking verify button go to login screen
        // we will validate the user in this method
        verifyBTN = findViewById(R.id.verifyBTN);
        verifyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // verify the code here and
                // if entered wrong code update the text view as Invalid verification code

                // if entered correct code
                // intent to login screen
                Toast.makeText(RegisrationVerificationScreen.this,"Registered Successfully",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisrationVerificationScreen.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
