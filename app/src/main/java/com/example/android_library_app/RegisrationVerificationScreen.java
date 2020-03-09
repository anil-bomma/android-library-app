package com.example.android_library_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisrationVerificationScreen extends AppCompatActivity {

    private static String firstname;
    private static String lastname;
    private static String studentId;
    private static String emailId;
    private static String password;
    private static String vCode;

    private FirebaseAuth fAuth;
    private FirebaseFirestore db;

    String userID;

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

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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

                    // register the user in fire base
                    fAuth.createUserWithEmailAndPassword(emailId, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        System.out.println("-----------User Registered Successfully------");
                                        Toast.makeText(
                                                RegisrationVerificationScreen.this,
                                                "User Registered Successfully.",
                                                Toast.LENGTH_LONG
                                        ).show();

                                        // sending mail to user
                                        sendMailToUser();

                                        // save in db
                                        userID = fAuth.getCurrentUser().getUid();
                                        DocumentReference documentReference = db.collection("users").document(userID);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("firstname", firstname);
                                        user.put("lastname", lastname);
                                        user.put("studentId", studentId);
                                        user.put("emailId", emailId);
                                        user.put("password", password);
                                        user.put("role", "student");

                                        // added user-info in database with user id
                                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("db", "onSuccess: user Profile is created for "+ userID);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("db", "onFailure: " + e.toString());
                                            }
                                        });


                                        // sending to login intent
                                        Intent intent = new Intent(
                                                RegisrationVerificationScreen.this,
                                                LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        System.out.println("-----------User Registered Failed------" + task.getException().getMessage());
                                        Toast.makeText(
                                                RegisrationVerificationScreen.this,
                                                "Error ! " + task.getException().getMessage(),
                                                Toast.LENGTH_LONG
                                        ).show();
                                    }
                                }
                            });
                } else {
                    // stay in the same activity
                    Toast.makeText(
                            RegisrationVerificationScreen.this,
                            "Invalid verification code",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void sendMailToUser() {
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
    }
}
