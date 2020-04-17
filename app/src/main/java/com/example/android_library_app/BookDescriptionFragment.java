package com.example.android_library_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BookDescriptionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_book_description, container, false);

        super.onCreate(savedInstanceState);

        TextView titleTV = view.findViewById(R.id.BTitleTV);
        String title = ListAllBooksFragment.bookTitle;
        titleTV.setText(title);
        System.out.println("------------------title-----------"+title);

        TextView authorTV = view.findViewById(R.id.BAuthorTV);
        String author = ListAllBooksFragment.bookAuthor;
        authorTV.setText(author);
        System.out.println("------------------author-----------"+author);

        TextView genreTV = view.findViewById(R.id.BGenreTV);
        String genre = ListAllBooksFragment.bookGenre;
        genreTV.setText(genre);
        System.out.println("------------------genre-----------"+genre);

        TextView availableTV = view.findViewById(R.id.BAvailableTV);
        String available = ListAllBooksFragment.bookAvailable;
        availableTV.setText(available);
        System.out.println("------------------available-----------"+available);

        TextView descriptionTV = view.findViewById(R.id.BDescriptionTV);
        String description = ListAllBooksFragment.bookDescription;
        descriptionTV.setText(description);
        System.out.println("------------------description-----------"+description);

        TextView publisherTV = view.findViewById(R.id.BPublisherTV);
        String publisher = ListAllBooksFragment.bookPublisher;
        publisherTV.setText(publisher);
        System.out.println("------------------publisher-----------"+publisher);

        TextView languageTV = view.findViewById(R.id.BLanguageTV);
        String language = ListAllBooksFragment.bookLanguage;
        languageTV.setText(language);
        System.out.println("------------------language-----------"+language);

        return view;
    }
}
