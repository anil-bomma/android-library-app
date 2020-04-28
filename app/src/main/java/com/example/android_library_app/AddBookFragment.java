package com.example.android_library_app;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AddBookFragment extends Fragment {

    Button addBookBTN;
    EditText titleET, authorET, languageET, genreET, publisherET, descriptionET, availableET, rackIDET;
    Spinner sectionIDSP;
    private FirebaseFirestore db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentAddBookView = inflater.inflate(R.layout.fragment_add_book, container, false);


        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        // add book into table
        addBookBTN = fragmentAddBookView.findViewById(R.id.addBookBTN);
        addBookBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    //the below line of code is just for testing
                    titleET = fragmentAddBookView.findViewById(R.id.titleET);
                    authorET = fragmentAddBookView.findViewById(R.id.authorET);
                    languageET = fragmentAddBookView.findViewById(R.id.languageET);
                    genreET = fragmentAddBookView.findViewById(R.id.genreET);
                    publisherET = fragmentAddBookView.findViewById(R.id.publisherET);
                    descriptionET = fragmentAddBookView.findViewById(R.id.descriptionET);
                    availableET = fragmentAddBookView.findViewById(R.id.availableET);
                    rackIDET = fragmentAddBookView.findViewById(R.id.rackIDET);
                    sectionIDSP = fragmentAddBookView.findViewById(R.id.sectionIDSP);

                    String title = titleET.getText().toString().trim();
                    String author = authorET.getText().toString().trim();
                    String language = languageET.getText().toString().trim();
                    String genre = genreET.getText().toString().trim();
                    String publisher = publisherET.getText().toString().trim();
                    String description = descriptionET.getText().toString().trim();
                    String available = availableET.getText().toString().trim();
                    String sectionID = sectionIDSP.getSelectedItem().toString();
                    String rackId = rackIDET.getText().toString().trim();

                    if (description.isEmpty()) {
                        description = String.format("%s by %s", title, author);
                    }

                    // validate and save book in db
                    validateBook(title, author, language, genre, publisher, available,
                            rackId, description, sectionID);

                } catch (Exception e) {
                    System.out.println("error: " + e);
                }
            }
        });

        return fragmentAddBookView;
    }

    public void validateBook(final String title, final String author, final String language,
                             final String genre, final String publisher, final String available,
                             final String rackId, final String description, final String sectionID) {

        if (title.isEmpty()) {
            titleET.setError("please enter the title");
            return;
        }
        if (author.isEmpty()) {
            authorET.setError("please enter the author name");
            return;
        }
        if (language.isEmpty()) {
            languageET.setError("please enter the language");
            return;
        }
        if (genre.isEmpty()) {
            genreET.setError("please enter the genre");
            return;
        }
        if (publisher.isEmpty()) {
            publisherET.setError("please enter the publisher name");
            return;
        }
        if (available.isEmpty()) {
            availableET.setError("please enter the total book count");
            return;
        }
        if (rackId.isEmpty()) {
            rackIDET.setError("please enter the rack id");
            return;
        }


        try {
            // check book already present in db or not
            db.collection("books")
                    .whereEqualTo("title", title)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                boolean bookExist = false;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    bookExist = true;
                                }

                                if (bookExist) {
                                    titleET.setError("book already exist with the same name");
                                    return;
                                } else {
                                    saveBook(title, author, language, genre, publisher, available,
                                            description, sectionID, rackId);
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

    public void saveBook(String title, String author, String language, String genre,
                         String publisher, String available, String description, String sectionID, String rackID) {
        try {
            // Create a new user with a first and last name
            Map<String, Object> book = new HashMap<>();
            book.put("title", title);
            book.put("author", author);
            book.put("language", language);
            book.put("genre", genre);
            book.put("publisher", publisher);
            book.put("description", description);
            book.put("available", available);
            book.put("sectionID", sectionID);
            book.put("rackID", rackID);

            // Add a new document with a generated ID
            db.collection("books")
                    .add(book)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("addBookFragment", "DocumentSnapshot added with ID: " + documentReference.getId());
                            titleET.setText("");
                            authorET.setText("");
                            languageET.setText("");
                            genreET.setText("");
                            publisherET.setText("");
                            descriptionET.setText("");
                            availableET.setText("");
                            rackIDET.setText("");
                            Toast toast = Toast.makeText(
                                    AddBookFragment.super.getContext(),
                                    "Book added successfully",
                                    Toast.LENGTH_LONG
                            );
                            View toastView = toast.getView();
                            toastView.setBackgroundColor(Color.parseColor("#79E87E"));
                            toast.show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("addBookFragment", "Error while adding book in database", e);
                        }
                    });
        } catch (Exception e) {
            Log.w("addBookFragment", "Error adding document", e);

        }
    }

}
