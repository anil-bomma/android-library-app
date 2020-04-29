package com.example.android_library_app;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

class BorrowedBooksModel {

    public static class BorrowedBooksInfo {
        public String borrowedBookName;
        public String borrowedStudentId;
        public String borrowedDate;
        public String borrowedBookReturnedDate;


        public BorrowedBooksInfo(String borrowedBookName, String borrowedStudentId,
                                 String borrowedDate, String borrowedBookReturnedDate) {
            this.borrowedBookName = borrowedBookName;
            this.borrowedStudentId = borrowedStudentId;
            this.borrowedDate = borrowedDate;
            this.borrowedBookReturnedDate = borrowedBookReturnedDate;
        }
    }

    private static BorrowedBooksModel singleton = null;

    public static BorrowedBooksModel getSingleton(ArrayList<BorrowedBooksInfo> booksArray) {
        singleton = new BorrowedBooksModel(booksArray);
        return singleton;
    }

    public ArrayList<BorrowedBooksInfo> borrowedbooksArray;

    private BorrowedBooksModel(ArrayList<BorrowedBooksInfo> booksArray) {
        borrowedbooksArray = booksArray;

    }
}

class BorrowedBooksAdapter extends RecyclerView.Adapter<BorrowedBooksAdapter.BorrowedBooksViewHolder> {

    private FirebaseFirestore db;

    public static class BorrowedBooksViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout convienceViewReference;

        public BorrowedBooksViewHolder(@NonNull LinearLayout linearLayout) {
            super(linearLayout);
            convienceViewReference = linearLayout;
        }
    }

    BorrowedBooksModel borrowedBooksModel;

    public BorrowedBooksAdapter(ArrayList<BorrowedBooksModel.BorrowedBooksInfo> borrowedBooks) {
        super();
        borrowedBooksModel = BorrowedBooksModel.getSingleton(borrowedBooks);
    }

    @NonNull
    @Override
    public BorrowedBooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_borrowed_book, parent, false);
        BorrowedBooksViewHolder bVH = new BorrowedBooksViewHolder(v);
        return bVH;
    }

    @Override
    public void onBindViewHolder(@NonNull final BorrowedBooksViewHolder holder, final int position) {
        TextView borrowedBookName = holder.convienceViewReference.findViewById(R.id.bookName);
        borrowedBookName.setText(borrowedBooksModel.borrowedbooksArray.get(position).borrowedBookName);
        TextView user919 = holder.convienceViewReference.findViewById(R.id.bookAuthor);
        user919.setText(borrowedBooksModel.borrowedbooksArray.get(position).borrowedStudentId);
        TextView returnDate = holder.convienceViewReference.findViewById(R.id.bookEdition);
        returnDate.setText(borrowedBooksModel.borrowedbooksArray.get(position).borrowedBookReturnedDate);

        // return book
        Button returnBook = holder.convienceViewReference.findViewById(R.id.bookReturnBTN);
        if (MainActivity.userRole.equalsIgnoreCase("student")) {
            returnBook.setVisibility(View.GONE);
        } else {
            returnBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db = FirebaseFirestore.getInstance();
                    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                            .setTimestampsInSnapshotsEnabled(true)
                            .build();
                    db.setFirestoreSettings(settings);

                    final BorrowedBooksModel.BorrowedBooksInfo borrowedBook = borrowedBooksModel.borrowedbooksArray.get(position);

                    String bookName = borrowedBook.borrowedBookName;
                    String userId = borrowedBook.borrowedStudentId;

                    db.collection("books")
                            .whereEqualTo("title", bookName)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Map<String, Object> book = document.getData();
                                            int count = Integer.parseInt(book.get("available").toString()) + 1;
                                            book.put("available", count);
                                            db.collection("books")
                                                    .document(document.getId())
                                                    .update(book);
                                        }
                                    }
                                }
                            });

                    db.collection("borrowedBooks")
                            .whereEqualTo("userID", userId)
                            .whereEqualTo("bookName", bookName)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Map<String, Object> borrowedBook = document.getData();
                                            borrowedBook.put("return", "true");
                                            db.collection("borrowedBooks")
                                                    .document(document.getId())
                                                    .update(borrowedBook);
                                        }
                                        BorrowBooksListFragment.borrowedBooksList.remove(borrowedBook);
                                        BorrowBooksListFragment.borrowedbooksAdapter.notifyDataSetChanged();

                                        Toast toast = Toast.makeText(BorrowBooksListFragment.context,
                                                "Record updated, Please collect the book...!",
                                                Toast.LENGTH_LONG);
                                        View toastView = toast.getView();
                                        toastView.setBackgroundColor(
                                                Color.parseColor("#79E87E"));
                                        toast.show();
                                    }
                                }
                            });
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return borrowedBooksModel.borrowedbooksArray.size();
    }
}

public class BorrowBooksListFragment extends Fragment {

    private FirebaseFirestore db;
    public static FragmentActivity context;

    public static BorrowedBooksAdapter borrowedbooksAdapter = null;
    public static ArrayList<BorrowedBooksModel.BorrowedBooksInfo> borrowedBooksList;
    private RecyclerView borrowedBookRV = null;
    private GestureDetectorCompat detector = null;
    ProgressBar borrowBookPB;
    TextView errorTV;

    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = borrowedBookRV.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder holder = borrowedBookRV.getChildViewHolder(view);

                if (holder instanceof BorrowedBooksAdapter.BorrowedBooksViewHolder) {
                    int position = holder.getAdapterPosition();

                    //Obtaining the title of the book
                    BorrowedBooksModel myModel = BorrowedBooksModel.getSingleton(
                            new ArrayList<BorrowedBooksModel.BorrowedBooksInfo>());

                    // handling single tap.
                    Log.d("click", "clicked on item " + position);
                    return true;
                }
            }
            return false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_borrowed_books_list, container, false);

        context = getActivity();

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        borrowBookPB = view.findViewById(R.id.borrowBookPB);
        errorTV = view.findViewById(R.id.errorTV);

        borrowedBooksList = new ArrayList<>();
        Query collection;
        if (MainActivity.userRole.equalsIgnoreCase("student")) {
            collection = db.collection("borrowedBooks")
                    .whereEqualTo("userID", MainActivity.user919)
                    .whereEqualTo("return", "false");
        } else {
            collection = db.collection("borrowedBooks")
                    .whereEqualTo("return", "false");
        }
        collection.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> borrowedBook = document.getData();
                                borrowedBooksList.add(new BorrowedBooksModel.BorrowedBooksInfo(
                                        borrowedBook.get("bookName").toString(),
                                        borrowedBook.get("userID").toString(),
                                        borrowedBook.get("borrowDate").toString(),
                                        borrowedBook.get("returnDate").toString()
                                ));
                            }

                            if (borrowedBooksList.size() < 1) {
                                errorTV.setText("You have not borrowed any books yet...!");
                                errorTV.setVisibility(View.VISIBLE);
                            }

                            // recycler view code.
                            borrowedbooksAdapter = new BorrowedBooksAdapter(borrowedBooksList);
                            borrowedBookRV = view.findViewById(R.id.borrowedBookRV);
                            borrowedBookRV.setAdapter(borrowedbooksAdapter);
                            borrowBookPB.setVisibility(View.GONE);

                            RecyclerView.LayoutManager myManager = new LinearLayoutManager(view.getContext());
                            borrowedBookRV.setLayoutManager(myManager);
                            detector = new GestureDetectorCompat(view.getContext(), new BorrowBooksListFragment.RecyclerViewOnGestureListener());
                            borrowedBookRV.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
                                @Override
                                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                                    return detector.onTouchEvent(e);
                                }
                            });
                        } else {
                            borrowBookPB.setVisibility(View.GONE);
                            Log.w("StudentBorrowedBookList", "Error getting documents.", task.getException());
                        }
                    }
                });

        return view;
    }
}
