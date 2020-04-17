package com.example.android_library_app;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class SearchPersonFragment extends Fragment {

    private SearchListener myActivity;
    private FirebaseFirestore db;
    Button addPersonBTN;
    LinearLayout searchPersonLL;
    TextView person_name, person919;

    public interface SearchListener {
        void addPressed();
    }

    public SearchPersonFragment() {
    }

    // step:4
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // myActivity = (SearchListener) context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_search_for_dpt_admin, container, false);
        searchPersonLL = view.findViewById(R.id.searchPersonLL);
        person_name = view.findViewById(R.id.person_name);
        person919 = view.findViewById(R.id.person919);

        // database initialization
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        final CollectionReference userRef = db.collection("users");
        final QueryDocumentSnapshot[] userData = new QueryDocumentSnapshot[1];

        if (getArguments() != null) {
            final String studentId = getArguments().getString("userId");
            try {
                // check book already present in db or not
                userRef.whereEqualTo("studentId", studentId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    boolean userNotFound = task.getResult().isEmpty();
                                    searchPersonLL.setVisibility(View.INVISIBLE);
                                    if (userNotFound) {
                                        Toast toast = Toast.makeText(getContext(),
                                                String.format("User not found with id - %s", studentId),
                                                Toast.LENGTH_LONG);
                                        View toastView = toast.getView();
                                        toastView.setBackgroundColor(Color.parseColor("#DA5656"));
                                        toast.show();
                                        return;
                                    } else {
                                        userData[0] = task.getResult().iterator().next();
                                        person_name.setText(userData[0].get("firstname") +
                                                " " + userData[0].get("lastname"));
                                        person919.setText(userData[0].get("studentId").toString());
                                        searchPersonLL.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    Log.d("addBookFragment", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            } catch (Exception e) {
                Log.w("addBookFragment", "Error while check book exist or not: ", e);
            }
        }


        addPersonBTN = view.findViewById(R.id.addPersonBTN);
        addPersonBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<Object, String> updatedUser = new HashMap<>();
                updatedUser.put("role", "departmentAdmin");
                userRef.document(userData[0].getId()).set(updatedUser, SetOptions.merge());
                Toast toast = Toast.makeText(getContext(),
                        "User role updated to Department Admin successfully",
                        Toast.LENGTH_LONG);
                View toastView = toast.getView();
                toastView.setBackgroundColor(Color.parseColor("#4CE483"));
                toast.show();
                // myActivity.addPressed();
            }
        });

        return view;
    }
}
