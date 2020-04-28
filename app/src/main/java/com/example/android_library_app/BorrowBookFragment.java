package com.example.android_library_app;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.google.firebase.auth.FirebaseAuth;
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

    TextView borrowBookName, borrowBookLocation, borrowBookDate, borrowBookReturnDate, bookAvailableTV;
    Button borrowBookBTN;
    public static ProgressBar borrowLoaderPB;
    private FirebaseFirestore db;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_borrow_book, container, false);

        borrowLoaderPB = view.findViewById(R.id.borrowLoaderPB);

        //today's date
        final String currentDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());

        final String bName = ListAllBooksFragment.bookTitle;
        final int count = Integer.parseInt(ListAllBooksFragment.bookAvailable);
        borrowBookName = view.findViewById(R.id.borrowBookName);
        borrowBookName.setText(ListAllBooksFragment.bookTitle);

        borrowBookLocation = view.findViewById(R.id.borrowBookLocation);
        borrowBookLocation.setText("Section: " + ListAllBooksFragment.bookSection + ", Rack Id: "
                + ListAllBooksFragment.bookRack);

        bookAvailableTV = view.findViewById(R.id.bookAvailableTV);
        bookAvailableTV.setText("" + count);

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
        final String returnDate = new SimpleDateFormat("MM-dd-yyyy").format(calendar.getTime());

        borrowBookReturnDate = view.findViewById(R.id.borrowBookReturnDate);
        borrowBookReturnDate.setText(returnDate);


        // Database code
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        // on button click.
        borrowBookBTN = view.findViewById(R.id.borrowBookBTN);
        borrowBookBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrowLoaderPB.setVisibility(View.VISIBLE);
                if (count < 1) {
                    borrowBookDate.setText("Not Available");
                    borrowBookReturnDate.setText("Not Available");
                    Toast toast = Toast.makeText(getContext(),
                            "Sorry, this book is not available for now..",
                            Toast.LENGTH_LONG);
                    View toastView = toast.getView();
                    toastView.setBackgroundColor(Color.parseColor("#F07878"));
                    toast.show();
                    borrowLoaderPB.setVisibility(View.INVISIBLE);
                    return;
                }

                // adding book data in borrowed book collection.
                final Map<String, Object> borrowedBook = new HashMap<>();
                borrowedBook.put("userID", MainActivity.user919);
                borrowedBook.put("bookName", bName);
                borrowedBook.put("borrowDate", currentDate);
                borrowedBook.put("returnDate", returnDate);

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
                                Toast toast = Toast.makeText(getContext(),
                                        "You have already borrowed this book..!!.",
                                        Toast.LENGTH_LONG);
                                View toastView = toast.getView();
                                toastView.setBackgroundColor(Color.parseColor("#F07878"));
                                borrowLoaderPB.setVisibility(View.INVISIBLE);
                                toast.show();
                            } else {
                                borrowBook(borrowedBook);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Database Error...!!", Toast.LENGTH_SHORT).show();
                            borrowLoaderPB.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }// onClick listener
        });
        return view;
    }

    private void borrowBook(final Map<String, Object> borrowedBook) {

        db.collection("books")
                .whereEqualTo("title", borrowedBook.get("bookName"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> book = document.getData();
                                int newCount = Integer.parseInt(book.get("available") + "") - 1;
                                book.put("available", newCount);
                                db.collection("books")
                                        .document(document.getId())
                                        .update(book);
                            }

                            db.collection("borrowedBooks")
                                    .add(borrowedBook)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast toast = Toast.makeText(getContext(),
                                                    "Reserved successfully, Please go to help desk and collect you book..!!",
                                                    Toast.LENGTH_LONG);
                                            View toastView = toast.getView();
                                            toastView.setBackgroundColor(Color.parseColor("#79E87E"));
                                            toast.show();
                                            BorrowBookFragment.borrowLoaderPB.setVisibility(View.INVISIBLE);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Book Borrowed Failed..!!", Toast.LENGTH_SHORT).show();
                                    BorrowBookFragment.borrowLoaderPB.setVisibility(View.INVISIBLE);
                                }
                            });
                        } else {
                            Toast toast = Toast.makeText(getContext(),
                                    "Database, error getting documents",
                                    Toast.LENGTH_LONG);
                            View toastView = toast.getView();
                            toastView.setBackgroundColor(Color.parseColor("#F07878"));
                            toast.show();
                            BorrowBookFragment.borrowLoaderPB.setVisibility(View.INVISIBLE);
                        }
                    }
                });

    }
}
