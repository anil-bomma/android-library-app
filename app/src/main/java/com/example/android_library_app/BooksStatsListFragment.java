package com.example.android_library_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

class BorrowedStatsModel {

    public static class BorrowedBooksInfo {
        public String borrowedBookName;
        public int count;

        public BorrowedBooksInfo(String borrowedBookName, int count) {
            this.borrowedBookName = borrowedBookName;
            this.count = count;
        }
    }

    private static BorrowedStatsModel singleton = null;

    public static BorrowedStatsModel getSingleton(ArrayList<BorrowedBooksInfo> booksArray) {
        singleton = new BorrowedStatsModel(booksArray);
        return singleton;
    }

    public ArrayList<BorrowedBooksInfo> borrowedbooksArray;

    private BorrowedStatsModel(ArrayList<BorrowedBooksInfo> booksArray) {
        borrowedbooksArray = booksArray;

    }
}

class BooksStatsAdapter extends RecyclerView.Adapter<BooksStatsAdapter.BorrowedBooksViewHolder> {

    public static class BorrowedBooksViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout convienceViewReference;

        public BorrowedBooksViewHolder(@NonNull LinearLayout linearLayout) {
            super(linearLayout);
            convienceViewReference = linearLayout;
        }
    }

    BorrowedStatsModel borrowedBooksModel;

    public BooksStatsAdapter(ArrayList<BorrowedStatsModel.BorrowedBooksInfo> borrowedBooks) {
        super();
        borrowedBooksModel = BorrowedStatsModel.getSingleton(borrowedBooks);
    }

    @NonNull
    @Override
    public BorrowedBooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_book_stats, parent, false);
        BorrowedBooksViewHolder bVH = new BorrowedBooksViewHolder(v);
        return bVH;
    }

    @Override
    public void onBindViewHolder(@NonNull final BorrowedBooksViewHolder holder, final int position) {
        TextView borrowedBookName = holder.convienceViewReference.findViewById(R.id.bookName);
        borrowedBookName.setText(borrowedBooksModel.borrowedbooksArray.get(position).borrowedBookName);
        TextView count = holder.convienceViewReference.findViewById(R.id.countTV);
        count.setText(borrowedBooksModel.borrowedbooksArray.get(position).count + "\nHits");
    }

    @Override
    public int getItemCount() {
        return borrowedBooksModel.borrowedbooksArray.size();
    }
}

public class BooksStatsListFragment extends Fragment {

    private FirebaseFirestore db;
    public static FragmentActivity context;

    public static BooksStatsAdapter borrowedbooksAdapter = null;
    public static ArrayList<BorrowedStatsModel.BorrowedBooksInfo> borrowedBooksList;
    private RecyclerView borrowedBookRV = null;
    ProgressBar borrowBookPB;

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

        borrowedBooksList = new ArrayList<>();

        db.collection("borrowedBooks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> bookStr = new ArrayList<>();
                            ArrayList<String> newList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> borrowedBook = document.getData();
                                bookStr.add(borrowedBook.get("bookName").toString());
                            }

                            for (String book : bookStr) {
                                if (!newList.contains(book)) {
                                    newList.add(book);
                                    int count = 0;
                                    for (String countBook : bookStr) {
                                        if (countBook.equalsIgnoreCase(book)) {
                                            ++count;
                                        }
                                    }
                                    borrowedBooksList.add(
                                            new BorrowedStatsModel.BorrowedBooksInfo(book, count));
                                }
                            }

                            // recycler view code.
                            borrowedbooksAdapter = new BooksStatsAdapter(borrowedBooksList);
                            borrowedBookRV = view.findViewById(R.id.borrowedBookRV);
                            borrowedBookRV.setAdapter(borrowedbooksAdapter);
                            borrowBookPB.setVisibility(View.GONE);

                            RecyclerView.LayoutManager myManager = new LinearLayoutManager(view.getContext());
                            borrowedBookRV.setLayoutManager(myManager);

                        } else {
                            borrowBookPB.setVisibility(View.GONE);
                            Log.w("StudentBorrowedBookList", "Error getting documents.", task.getException());
                        }
                    }
                });

        return view;
    }
}
