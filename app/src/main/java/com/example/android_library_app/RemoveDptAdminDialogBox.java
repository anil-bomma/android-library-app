package com.example.android_library_app;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class RemoveDptAdminDialogBox extends AppCompatDialogFragment {

    private FirebaseFirestore db;
    private CollectionReference userRef;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Delete");

        builder.setMessage("Do you want to remove this person as department Admin");

        builder.setPositiveButton("remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), " This persons role is changed from department " +
                        "admin to normal user." + which, Toast.LENGTH_SHORT).show();
                System.out.println("-----ListAllDrtAdminsFragment" + ListAllDrtAdminsFragment.KEY_DptAdmin919);
                updateUserRole(ListAllDrtAdminsFragment.KEY_DptAdmin919);
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }

    private void updateUserRole(final String dptAdmin919) {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        final CollectionReference userRef = db.collection("users");
        userRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Map<String, Object> userData = document.getData();
                        if (document.getData().get("studentId").toString().equals(dptAdmin919)) {
                            Map<String, Object> user = new HashMap<>();
                            user.put("role", "student");
                            userRef.document(document.getId()).set(user, SetOptions.merge());
                        }
                    }
                }
            }
        });
    }
}
