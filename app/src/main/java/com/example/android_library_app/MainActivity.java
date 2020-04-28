package com.example.android_library_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class MainActivity extends AppCompatActivity {

    public static boolean loginStatus = false;
    public static String userId, user919, userName;
    public static String userRole = "admin";

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        // get the role from login intent and trigger to new intent based on role
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        DocumentReference documentReference = db.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                userRole = documentSnapshot.getString("role");
                user919 = documentSnapshot.getString("studentId");
                userName = documentSnapshot.getString("firstname");

                Intent userScreen = new Intent(MainActivity.this, AdminScreen.class);
                userScreen.putExtra("userId", userId);
                userScreen.putExtra("userRole", userRole);
                startActivity(userScreen);
                finish();
            }
        });

        TextView homeId = findViewById(R.id.homeId);
        homeId.setText(("userRole: " + userRole));
    }

}
