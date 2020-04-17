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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class AddDepartmentAdminFragment extends Fragment
        implements SearchPersonFragment.SearchListener {

    private SearchPersonFragment searchPersonFragment;
    private FirebaseFirestore db;
    Button find_personBTN;
    EditText dpt_admin_919ET;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_dpt_admin, container, false);

        // fragment code.
        // first time only code.
        if (savedInstanceState != null) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            searchPersonFragment = (SearchPersonFragment) manager.findFragmentByTag("searchFR");
            return view;
        }

        searchPersonFragment = new SearchPersonFragment();

        // transaction object.
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.find_person_containerFL, searchPersonFragment, "searchFR");
        transaction.hide(searchPersonFragment);
        transaction.commit();


        // database initialization
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


        find_personBTN = view.findViewById(R.id.find_personBTN);
        find_personBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    dpt_admin_919ET = view.findViewById(R.id.dpt_admin_919ET);
                    final String dpt_admin_919 = dpt_admin_919ET.getText().toString().trim();
                    if (validateDptAdmin(dpt_admin_919)) {

                        Bundle args = new Bundle();
                        args.putString("userId", dpt_admin_919);
                        searchPersonFragment = new SearchPersonFragment();
                        searchPersonFragment.setArguments(args);

                        FragmentTransaction transaction = getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.find_person_containerFL, searchPersonFragment);

                        transaction.show(searchPersonFragment);
                        transaction.commit();

                    } else {
                        Toast.makeText(getActivity(), "Enter  #919", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.d("Error occurred", " is " + e);
                    Toast.makeText(getActivity(), "Enter proper 919 number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private boolean validateDptAdmin(String dpt_admin_919) {
        if (dpt_admin_919.isEmpty()) {
            dpt_admin_919ET.setError("Enter the 919 number");
            return false;
        }
        return true;
    }


    @Override
    public void addPressed() {
        Toast.makeText(getActivity(), "Added the user as department admin", Toast.LENGTH_SHORT).show();
    }
}
