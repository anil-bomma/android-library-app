package com.example.android_library_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SearchBookFragment extends Fragment {
    Button searchBTN;
    EditText searchBookET;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_book,container,false);

        searchBTN = view.findViewById(R.id.searchBTN);
        searchBookET = view.findViewById(R.id.searchBookET);
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBookET.getText();
            }
        });
        return view;
    }
}
