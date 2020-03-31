package com.example.android_library_app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SearchPersonFragment extends Fragment {

    Button addPersonBTN;

//    // interface -1
//    public interface SearchListener {
//        void addPressed();
//    }
//
//    public SearchPersonFragment() {
//    }
//
//    private SearchListener myActivity;
//
//    // step:4
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        myActivity = (SearchListener) context;
//    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_search_for_dpt_admin, container, false);
        addPersonBTN = view.findViewById(R.id.addPersonBTN);
        addPersonBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                myActivity.addPressed();
            }
        });
        return view;
    }
}
