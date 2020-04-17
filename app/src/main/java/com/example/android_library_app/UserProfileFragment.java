package com.example.android_library_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class UserProfileFragment extends Fragment {

    TextView profile_userName, profile_role,profile_919, profile_mailID;

    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile,container,false);

        profile_userName = view.findViewById(R.id.profile_userName);
        profile_role = view.findViewById(R.id.profile_role);
        profile_919 = view.findViewById(R.id.profile_919);
        profile_mailID = view.findViewById(R.id.profile_mailID);

        // Database code
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        System.out.println("-----------usermail---------"+LoginActivity.email);
        final CollectionReference userRef = db.collection("users");
        userRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> user = document.getData();
                        if (user.get("emailId").toString().equals(LoginActivity.email)) {
                            profile_userName.setText(user.get("firstname").toString() + " " + user.get("lastname").toString());
                            profile_role.setText(user.get("role").toString());
                            profile_919.setText(user.get("studentId").toString());
                            profile_mailID.setText(user.get("emailId").toString());
                        }
                    }
                }
            }
        });

        return view;
    }
}
