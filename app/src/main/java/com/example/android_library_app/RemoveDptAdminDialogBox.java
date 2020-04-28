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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RemoveDptAdminDialogBox extends AppCompatDialogFragment {

    private FirebaseFirestore db;
    private ArrayList<DptAdminModel.DptAdminInfo> dptAdminArray;
    private DptAdminAdapter dptAdminAdapter;

    public RemoveDptAdminDialogBox(DptAdminAdapter dptAdminAdapter,
                                   ArrayList<DptAdminModel.DptAdminInfo> dptAdminArray) {
        this.dptAdminAdapter = dptAdminAdapter;
        this.dptAdminArray = dptAdminArray;
    }

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
                System.out.println("-----ListAllDrtAdminsFragment" + ListAllDrtAdminsFragment.KEY_DptAdmin);
                dptAdminArray.remove(ListAllDrtAdminsFragment.KEY_DptAdmin);
                dptAdminAdapter.notifyDataSetChanged();
                updateUserRole(ListAllDrtAdminsFragment.KEY_DptAdmin.dpt_919);
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
        userRef.whereEqualTo("studentId", dptAdmin919)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                userRef.document(document.getId()).update("role", "student");
                            }
                        }
                    }
                });
    }
}
