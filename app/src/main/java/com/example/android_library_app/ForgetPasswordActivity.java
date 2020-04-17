package com.example.android_library_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.concurrent.TimeUnit;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText forgetPasswordEmailIdET;
    Button forgetPasswordSubmitBTN;
    String mailId;
    String password, username;
    TextView backToLoginTV;

    FirebaseAuth fAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


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

        forgetPasswordEmailIdET = findViewById(R.id.forgetPasswordEmailIdET);
        forgetPasswordSubmitBTN = findViewById(R.id.forgetPasswordSubmitBTN);
        forgetPasswordSubmitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mailId = forgetPasswordEmailIdET.getText().toString();
                System.out.println("-------------------mailID--------------- "+ mailId + " ----------------");
                if(mailId.isEmpty()) {
                    forgetPasswordEmailIdET.setError("Please enter valid mailId");
                }
                else {
                    //getting the password.
                    final CollectionReference userRef = db.collection("users");
                    userRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> user = document.getData();
                                    if (user.get("emailId").toString().equals(mailId)) {
                                        password = user.get("password").toString();
                                        System.out.println("------------password------------" + password);
                                        username = user.get("firstname").toString() + " " +
                                                user.get("lastname").toString();
                                        System.out.println("------------username------------" + username);
                                        Toast.makeText(ForgetPasswordActivity.this,
                                                "Hello " + username +
                                                        ", A mail is sent to your registered mailID ",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    // trying
                                    else {
                                        forgetPasswordEmailIdET.setError("Please enter the mailId given during registration");
                                    }
                                }
                            }
                        }
                    }); // end of db call

                    sendPassword(mailId, password, username);
                } // end of else
            }
        }); // end of onclick listener
    }

    private void sendPassword(final String myMailId, final String password, final String username) {
        fAuth.fetchSignInMethodsForEmail(myMailId).
                addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean check = task.getResult().getSignInMethods().isEmpty();
                if (check) {
                    String subject = "B.D. Owens Library, Password";
                    String message = String.format(
                            "Hello %s , here is the password of your account: %s" +
                                    "%n%nThank you, %nTeam Library",
                            username, password);
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
                //trying
                else {
                    forgetPasswordEmailIdET.setError("Email Id doesn't exist.");
                }
            }
        });

























//        fAuth.fetchSignInMethodsForEmail(mailId).
//                addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
//
//            @Override
//            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
//                if(task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//
//                    }
//                }
//                // check login ways
//                boolean check = task.getResult().getSignInMethods().isEmpty();
//
//                if (check) {
//                    // build subject and message for forget password
//                    String subject = "B.D. Owens Library, Password ";
//                    String message = String.format(
//                            "Hello, here is the password for login: %s" +
//                                    "%n%nThank you, %nTeam Library",
//                            );
//                }
//            }
//        });
    }
}
