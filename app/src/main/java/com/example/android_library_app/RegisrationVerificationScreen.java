package com.example.android_library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisrationVerificationScreen extends AppCompatActivity {

    private static String firstname;
    private static String lastname;
    private static String studentId;
    private static String emailId;
    private static String password;
    private static String vCode;

    EditText verifyET;
    Button verifyBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regisration_verification_screen);

        Intent registrationIntent = getIntent();
        firstname = registrationIntent.getStringExtra("firstname");
        lastname = registrationIntent.getStringExtra("lastname");
        studentId = registrationIntent.getStringExtra("studentId");
        emailId = registrationIntent.getStringExtra("emailId");
        password = registrationIntent.getStringExtra("password");
        vCode = registrationIntent.getStringExtra("vCode");

        // on clicking verify button go to login screen
        // we will validate the user in this method
        verifyBTN = findViewById(R.id.verifyBTN);
        verifyET = findViewById(R.id.verifyET);
        verifyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // verify the code here and
                // if entered wrong code update the text view as Invalid verification code

                // if entered correct code
                // intent to login screen

                String userVcode = verifyET.getText().toString().trim();

                if (userVcode.equals(vCode)) {
                    // save in database logic here
                    Toast.makeText(RegisrationVerificationScreen.this, "Registered Successfully", Toast.LENGTH_LONG).show();

                    // sending user details through email
                    String subject = "B.D. Owens Library, login successful";
                    String message = String.format(
                            "Hello %s %s, here is the login details:%n%n", firstname, lastname);

                    message += "\t" + String.format("EmailId: %s%n", emailId);
                    message += "\t" + String.format("Password: %s%n", password);
                    message += "\t" + String.format("StudentId: %s%n", studentId);
                    message += String.format("%nNote: This is an automated message to confirm that " +
                            "your account has created successfully.%n");
                    message += String.format("%nThank you, %nLibrary Team");


                    // sending verification code to user and sending to next intent also
                    GmailServer mailServer = new GmailServer(
                            RegisrationVerificationScreen.this,
                            emailId,
                            subject,
                            message
                    );
                    mailServer.execute();

                    Intent intent = new Intent(RegisrationVerificationScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // stay in the same activity
                    Toast.makeText(RegisrationVerificationScreen.this, "Invalid verification code", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}
