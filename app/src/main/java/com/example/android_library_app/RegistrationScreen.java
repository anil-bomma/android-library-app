package com.example.android_library_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RegistrationScreen extends AppCompatActivity {
//    implements AdapterView.OnItemSelectedListener


    Button registerBTN;
    TextView loginTV;
    EditText firstnameET, lastnameET, studentIdET, emailIdET, passwordET;

    FirebaseAuth fAuth;


    //    Spinner rolesSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);

        firstnameET = findViewById(R.id.firstnameET);
        lastnameET = findViewById(R.id.lastnameET);
        studentIdET = findViewById(R.id.studentIdET);
        emailIdET = findViewById(R.id.emailIdET);
        passwordET = findViewById(R.id.passwordET);

        fAuth = FirebaseAuth.getInstance();


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
        // will send verification code.
        registerBTN = findViewById(R.id.registerBTN);
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstname = firstnameET.getText().toString().trim();
                String lastname = lastnameET.getText().toString().trim();
                String studentId = studentIdET.getText().toString().trim();
                String emailId = emailIdET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();

                if (firstname.isEmpty() || firstname.length() < 4) {
                    firstnameET.setError("please enter the first name minimum 3 letters");
                    return;
                }
                if (lastname.isEmpty() || lastname.length() < 2) {
                    lastnameET.setError("please enter the last name minimum 1 letters");
                    return;
                }
                if (studentId.isEmpty()) {
                    studentIdET.setError("please enter the 919 id");
                }
                if (emailId.isEmpty()) {
                    emailIdET.setError("please enter the email id");
                }
                String[] emailArr = emailId.split("@");
                if (emailArr.length == 1) {
                    emailIdET.setError("please enter valid email id");
                    return;
                }
                if (!emailId.contains("@nwmissouri.edu")) {
                    emailIdET.setError("please enter valid Northwest student email-id");
                    return;
                }
                if (password.isEmpty() || password.length() < 6) {
                    passwordET.setError("please enter the password, minimum 5 digit");
                    return;
                }

                // received all input attributes from user now send verification code
                // and save in registration table
                if (!firstname.isEmpty() && !lastname.isEmpty() && !studentId.isEmpty() &&
                        !emailId.isEmpty() && !password.isEmpty()) {

                    Random random = new Random();
                    String vCode = String.format("%04d", random.nextInt(10000));


                    Intent registrationScreenIntent = new Intent(
                            RegistrationScreen.this,
                            RegisrationVerificationScreen.class
                    );
                    registrationScreenIntent.putExtra("firstname", firstname);
                    registrationScreenIntent.putExtra("lastname", lastname);
                    registrationScreenIntent.putExtra("studentId", studentId);
                    registrationScreenIntent.putExtra("emailId", emailId);
                    registrationScreenIntent.putExtra("password", password);
                    registrationScreenIntent.putExtra("vCode", vCode);

                    sendVerificationCode(firstname, lastname, emailId, vCode, registrationScreenIntent);

                }
            }
        });

        // clicked on already registered text view.
        // will redirect to the login page.
        loginTV = findViewById(R.id.loginTV);
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationScreen.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void sendVerificationCode(
            final String firstname, final String lastname,
            final String emailId, final String vCode, final Intent registerVerifyIntent) {

        // check mail already registered or not
        fAuth.fetchSignInMethodsForEmail(emailId)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            // check login ways
                            boolean check = task.getResult().getSignInMethods().isEmpty();

                            if (check) {
                                // build subject and message for login
                                String subject = "B.D. Owens Library, Registration verification code";
                                String message = String.format(
                                        "Hello %s %s, here is the verification code for registration: %s" +
                                                "%n%nThank you, %nTeam Library",
                                        firstname, lastname, vCode);

                                // sending verification code to user and sending to next intent also
                                GmailServer mailServer = new GmailServer(
                                        RegistrationScreen.this,
                                        emailId, subject, message);
                                mailServer.execute();

                                try {
                                    TimeUnit.SECONDS.sleep(5);
                                } catch (InterruptedException e) {
                                    System.out.println("error while sending error: " + e);
                                    Toast.makeText(
                                            RegistrationScreen.this,
                                            "Error while sending message",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                                startActivity(registerVerifyIntent);
                                finish();
                            } else {
                                emailIdET.setError("Email Id already register.");
                            }
                        } else {
                            emailIdET.setError("Invalid email id, please check the email id");
                            Toast.makeText(getApplicationContext(),
                                    "Invalid details please check", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}
