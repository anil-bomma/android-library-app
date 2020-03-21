package com.example.android_library_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddDepartmentAdminFragment extends Fragment {

    Button add_dpt_adminBTN;
    EditText dpt_admin_nameET, dpt_admin_919ET, assigned_dptET, dpt_admin_emailET, dpt_admin_mobileET;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_dpt_admin, container, false);

        add_dpt_adminBTN = view.findViewById(R.id.add_dpt_adminBTN);
        add_dpt_adminBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    dpt_admin_nameET = view.findViewById(R.id.dpt_admin_nameET);
                    dpt_admin_919ET = view.findViewById(R.id.dpt_admin_919ET);
                    assigned_dptET = view.findViewById(R.id.assigned_dptET);
                    dpt_admin_emailET = view.findViewById(R.id.dpt_admin_emailET);
                    dpt_admin_mobileET = view.findViewById(R.id.dpt_admin_mobileET);

                    String dpt_admin_name = dpt_admin_nameET.getText().toString().trim();
                    String dpt_admin_919 = dpt_admin_919ET.getText().toString().trim();
                    String assigned_dpt = assigned_dptET.getText().toString().trim();
                    String dpt_admin_email = dpt_admin_emailET.getText().toString().trim();
                    String dpt_admin_mobile = dpt_admin_mobileET.getText().toString().trim();


                    validateDptAdmin(dpt_admin_name,dpt_admin_919,assigned_dpt,dpt_admin_email,dpt_admin_mobile);
                }catch(Exception e) {
                    Log.d("Error occured"," is "+e);
                }
            }
        });

        return view;
    }

    private void validateDptAdmin(String dpt_admin_name, String dpt_admin_919,
                                  String assigned_dpt, String dpt_admin_email,
                                  String dpt_admin_mobile) {
        if(dpt_admin_name.isEmpty()) {
            dpt_admin_nameET.setError("Enter the name of the person");
        }
        if(dpt_admin_919.isEmpty()) {
            dpt_admin_919ET.setError("Enter the 919 number");
        }
        if(assigned_dpt.isEmpty()) {
            assigned_dptET.setError("Enter the department name to be assigned");
        }
        if(dpt_admin_email.isEmpty()) {
            dpt_admin_emailET.setError("Enter the mail ID");
        }
        if(dpt_admin_mobile.isEmpty()) {
            dpt_admin_mobileET.setError("Enter the contact number");
        }
    }

}
