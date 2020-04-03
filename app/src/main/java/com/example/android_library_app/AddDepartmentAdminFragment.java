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

public class AddDepartmentAdminFragment extends Fragment
//        implements SearchPersonFragment.SearchListener
    {

    private SearchPersonFragment searchPersonFragment;
    Button find_personBTN;
    EditText  dpt_admin_919ET;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.fragment_add_dpt_admin, container, false);

         // fragment code.
        // first time only code.
        if (savedInstanceState != null) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            searchPersonFragment = (SearchPersonFragment) manager.findFragmentByTag("searchFR");
            //return;
        }

        searchPersonFragment = new SearchPersonFragment();

        // transaction object.
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.find_person_containerFL, searchPersonFragment, "searchFR");
        transaction.hide(searchPersonFragment);
        transaction.commit();



        find_personBTN = view.findViewById(R.id.find_personBTN);
        find_personBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    dpt_admin_919ET = view.findViewById(R.id.dpt_admin_919ET);
                    String dpt_admin_919 = dpt_admin_919ET.getText().toString().trim();
                    validateDptAdmin(dpt_admin_919);


                    // temporary code.
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.show(searchPersonFragment);
                    transaction.commit();

                }catch(Exception e) {
                    Log.d("Error occured"," is "+e);
                    Toast.makeText(getActivity(), "Enter proper 919 number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void validateDptAdmin(String dpt_admin_919) {
        if(dpt_admin_919.isEmpty()) {
            dpt_admin_919ET.setError("Enter the 919 number");
        }
    }

//    @Override
    public void addPressed() {
        Toast.makeText(getActivity(), "Added the user as department admin", Toast.LENGTH_SHORT).show();
    }
}
