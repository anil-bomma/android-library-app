package com.example.android_library_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

        final AutoCompleteTextView searchBookET = (AutoCompleteTextView) view.findViewById(R.id.searchBookET);
        String[] books = getResources().getStringArray(R.array.book_names);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, books);
        searchBookET.setAdapter(adapter);

        searchBTN = view.findViewById(R.id.searchBTN);
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBookET.getText();
                System.out.println("testing");
            }
        });
        return view;
    }
}
