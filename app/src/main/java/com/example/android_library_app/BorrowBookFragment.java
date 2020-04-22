package com.example.android_library_app;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BorrowBookFragment extends Fragment {

    TextView borrowBookName, borrowBookLocation, borrowBookDate, borrowBookReturnDate;
    Button borrowBookBTN;
//    static String studentID;
    private FirebaseFirestore db;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_borrow_book, container, false);

        //today's date
        String currentDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());

        final String bName = ListAllBooksFragment.bookTitle;
        borrowBookName = view.findViewById(R.id.borrowBookName);
        borrowBookName.setText(bName);


        borrowBookDate = view.findViewById(R.id.borrowBookDate);
        borrowBookDate.setText(currentDate);

        // date after 1 month(30 days)
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(currentDate));
        } catch (ParseException e) {
            System.out.println("-------------Error in date parsing----------" + e);
            e.printStackTrace();
        }
        calendar.add(Calendar.DATE, 30);
        String returnDate = new SimpleDateFormat("MM-dd-yyyy").format(calendar.getTime());

        borrowBookReturnDate = view.findViewById(R.id.borrowBookReturnDate);
        borrowBookReturnDate.setText(returnDate);


        // Database code
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


        // adding book data.
        final Map<String, Object> borrowedBook = new HashMap<>();
        borrowedBook.put("userID", MainActivity.user919);
        borrowedBook.put("bookName", bName);
        borrowedBook.put("borrowDate", currentDate);
        borrowedBook.put("returnDate", returnDate);

        System.out.println("---------borrowedBook" + borrowedBook);

        // on button click.
        borrowBookBTN = view.findViewById(R.id.borrowBookBTN);
        borrowBookBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("-------------Button Clicked -----------");

                db.collection("borrowedBooks").
                        whereEqualTo("userID", MainActivity.user919).
                        whereEqualTo("bookName", bName).
                        get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean alreadyBorrowed = false;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                alreadyBorrowed = true;
                            }
                            if (alreadyBorrowed) {
                                Toast.makeText(getActivity(), "Book already Borrowed ..!!", Toast.LENGTH_SHORT).show();
                            } else {
                                borrowBook(borrowedBook);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error...!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }// onClick listener
        });
        return view;
    }

    private void borrowBook(Map<String, Object> borrowedBook) {
        db.collection("borrowedBooks").
                add(borrowedBook).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getActivity(), "Borrowed Successfully..!!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Book Borrowed Failed..!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
