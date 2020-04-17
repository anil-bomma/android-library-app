package com.example.android_library_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText forgetPasswordEmailIdET, verificationCodeET;
    Button verifySubmitBTN, forgetPasswordSubmitBTN;
    String mailId, vCode, generateVCode;
    String password, username;
    TextView backToLoginTV;

    FirebaseAuth fAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        verificationCodeET = findViewById(R.id.verificationCodeET);
        forgetPasswordEmailIdET = findViewById(R.id.forgetPasswordEmailIdET);

        //code for back to login text view.
        backToLoginTV = findViewById(R.id.backToLoginTV);
        backToLoginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                setResult(1);
                finish();
            }
        });

        // auth for mail.
        fAuth = FirebaseAuth.getInstance();

        // db call
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


        verifySubmitBTN = findViewById(R.id.verifySubmitBTN);
        verifySubmitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mailId = forgetPasswordEmailIdET.getText().toString();
                if (mailId.isEmpty()) {
                    forgetPasswordEmailIdET.setError("Please enter valid mailId");
                    return;
                }

                fAuth.fetchSignInMethodsForEmail(mailId).
                        addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                boolean check = task.getResult().getSignInMethods().isEmpty();
                                if (!check) {
                                    Random random = new Random();
                                    generateVCode = String.format("%04d", random.nextInt(10000));
                                    sendPassword(mailId,
                                            "B.D. Owens Library, Forgot Password Code", String.format(
                                                    "Hello User, here is the verification code of your account: %s" +
                                                            "%n%nThank you, %nTeam Library",
                                                    generateVCode));

                                    Toast.makeText(ForgetPasswordActivity.this,
                                            "A verification code has sent to you mail",
                                            Toast.LENGTH_SHORT).show();

                                    verifySubmitBTN.setVisibility(View.INVISIBLE);
                                    verificationCodeET.setVisibility(View.VISIBLE);
                                    forgetPasswordSubmitBTN.setVisibility(View.VISIBLE);
                                } else {
                                    forgetPasswordEmailIdET.setError("Email Id doesn't exist.");
                                }
                            }
                        });


            }
        });


        forgetPasswordSubmitBTN = findViewById(R.id.forgetPasswordSubmitBTN);
        forgetPasswordSubmitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mailId = forgetPasswordEmailIdET.getText().toString();
                vCode = verificationCodeET.getText().toString();
                if (mailId.isEmpty()) {
                    forgetPasswordEmailIdET.setError("Please enter valid mailId");
                    return;
                } else if (vCode.isEmpty()) {
                    verificationCodeET.setHint("Please enter the verification code");
                    return;
                } else if (!mailId.isEmpty() && !vCode.isEmpty() && vCode.equals(generateVCode)) {
                    //getting the password.
                    final CollectionReference userRef = db.collection("users");
                    final QueryDocumentSnapshot[] userData = new QueryDocumentSnapshot[1];
                    userRef.whereEqualTo("emailId", mailId)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                boolean userNotFound = task.getResult().isEmpty();
                                if (userNotFound) {
                                    Toast toast = Toast.makeText(ForgetPasswordActivity.this,
                                            String.format("User not found with email - %s", mailId),
                                            Toast.LENGTH_LONG);
                                    View toastView = toast.getView();
                                    toastView.setBackgroundColor(Color.parseColor("#DA5656"));
                                    toast.show();
                                    return;
                                } else {
                                    verificationCodeET.setText("");
                                    forgetPasswordEmailIdET.setText("");

                                    userData[0] = task.getResult().iterator().next();
                                    password = userData[0].get("password").toString();
                                    username = userData[0].get("firstname").toString() + " " +
                                            userData[0].get("lastname").toString();


                                    sendPassword(mailId,
                                            "B.D. Owens Library, Password",
                                            String.format("Hello %s , here is the password of your account: %s" +
                                                            "%n%nThank you, %nTeam Library",
                                                    username, password));
                                    Toast.makeText(ForgetPasswordActivity.this,
                                            "Hello " + username +
                                                    ", A mail is sent to your registered mailID ",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                } else {
                    verificationCodeET.setError("invalid verification code");
                }
            }
        }); // end of onclick listener
    }

    private void sendPassword(final String myMailId, final String subject, final String message) {


        // sending verification code to user and sending to next intent also
        GmailServer mailServer = new GmailServer(
                ForgetPasswordActivity.this,
                myMailId, subject, message);
        mailServer.execute();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            System.out.println("error while sending mail: " + e);
            Toast.makeText(
                    ForgetPasswordActivity.this,
                    "Error while sending message",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}