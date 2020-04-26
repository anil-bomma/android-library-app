package com.example.android_library_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class BookDescriptionFragment extends Fragment {

    Button borrowBTN;
    private BorrowBookFragment borrowBookFragment = new BorrowBookFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_description, container, false);
        super.onCreate(savedInstanceState);

        TextView titleTV = view.findViewById(R.id.BTitleTV);
        String title = ListAllBooksFragment.bookTitle;
        titleTV.setText(title);

        TextView authorTV = view.findViewById(R.id.BAuthorTV);
        String author = ListAllBooksFragment.bookAuthor;
        authorTV.setText(author);

        TextView genreTV = view.findViewById(R.id.BGenreTV);
        String genre = ListAllBooksFragment.bookGenre;
        genreTV.setText(genre);

        TextView availableTV = view.findViewById(R.id.BAvailableTV);
        String available = ListAllBooksFragment.bookAvailable;
        availableTV.setText(available);

        TextView descriptionTV = view.findViewById(R.id.BDescriptionTV);
        String description = ListAllBooksFragment.bookDescription;
        descriptionTV.setText(description);

        TextView publisherTV = view.findViewById(R.id.BPublisherTV);
        String publisher = ListAllBooksFragment.bookPublisher;
        publisherTV.setText(publisher);

        TextView languageTV = view.findViewById(R.id.BLanguageTV);
        String language = ListAllBooksFragment.bookLanguage;
        languageTV.setText(language);

        // borrow book code
        borrowBTN = view.findViewById(R.id.borrowBTN);
        borrowBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.loginStatus == false) {
                    Toast.makeText(getContext(), "Please login to borrow the book", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity().getApplication(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.fragment_container, borrowBookFragment, "borrow book fragment");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        return view;
    }
}
