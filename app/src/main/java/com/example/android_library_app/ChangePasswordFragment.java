package com.example.android_library_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordFragment extends Fragment {
    EditText currentPasswordET, newPasswordET, reTypePassword;
    Button submitBTN;

    private FirebaseFirestore db;
    private FirebaseUser fAuthUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_password_screen, container, false);
        currentPasswordET = view.findViewById(R.id.currentPasswordET);
        newPasswordET = view.findViewById(R.id.newPasswordET);
        reTypePassword = view.findViewById(R.id.reTypePassword);
        submitBTN = view.findViewById(R.id.submitBTN);

        //db call:
        fAuthUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final CollectionReference userRef = db.collection("users");

                userRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                final Map<String, Object> user = document.getData();
                                if (user.get("emailId").toString().equals(LoginActivity.email)) {
                                    String currentPassword = currentPasswordET.getText().toString().trim();
                                    if (currentPassword.isEmpty()) {
                                        currentPasswordET.setError("Please enter Old/Current password");
                                    }
                                    if (user.get("password").toString().equals(currentPassword)) {
                                        final String newPassword = newPasswordET.getText().toString().trim();
                                        if (newPassword.isEmpty()) {
                                            newPasswordET.setError("Please enter new password");
                                        }
                                        String reType = reTypePassword.getText().toString().trim();
                                        if (reType.isEmpty()) {
                                            reTypePassword.setError("Please re-enter the new password");
                                        }
                                        if (newPassword.equals(reType)) {
                                            AuthCredential credential = EmailAuthProvider
                                                    .getCredential(LoginActivity.email, currentPassword);

                                            fAuthUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        fAuthUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Log.d("password update", "Password updated");
                                                                } else {
                                                                    Log.d("password update", "Error password not updated");
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        Log.d("password update", "Error auth failed");
                                                    }
                                                }
                                            });

                                            user.put("password", reType);
                                            userRef.document(document.getId()).set(user, SetOptions.merge());
                                            Toast.makeText(getActivity(), "Password changed successfully..!!",
                                                    Toast.LENGTH_SHORT).show();
                                            currentPasswordET.setText("");
                                            newPasswordET.setText("");
                                            reTypePassword.setText("");
                                        } else {
                                            Toast.makeText(getActivity(), "Mis-match in new password. Please try again ",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), "Old password doesn't match. Please try again..!!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }

                    }
                });// end of db call

            }
        }); // end of onClick method

        return view;
    }
}