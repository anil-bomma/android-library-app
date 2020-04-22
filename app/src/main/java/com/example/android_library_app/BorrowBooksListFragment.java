package com.example.android_library_app;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class BorrowedBooksModel {

    private FirebaseFirestore db;

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

    public static BorrowedBooksModel getSingleton() {
        if (singleton == null) {
            singleton = new BorrowedBooksModel();
        }
        return singleton;
    }

    public ArrayList<BorrowedBooksInfo> borrowedbooksArray;

    private BorrowedBooksModel() {
        borrowedbooksArray = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        loadModel();
    }

    public void loadModel() {
        System.out.println("------------load model-----------");
        db.collection("borrowedBooks").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> borrowedBook = document.getData();
                                borrowedbooksArray.add(new BorrowedBooksInfo(
                                        borrowedBook.get("bookName").toString(),
                                        borrowedBook.get("userID").toString(),
                                        borrowedBook.get("borrowDate").toString(),
                                        borrowedBook.get("returnDate").toString()
                                ));
                            }
                        } else {
                            Log.w("StudentBorrowedBookList", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}

class BorrowedBooksAdapter extends RecyclerView.Adapter<BorrowedBooksAdapter.BorrowedBooksViewHolder> {

    public static class BorrowedBooksViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout convienceViewReference;

        public BorrowedBooksViewHolder(@NonNull LinearLayout linearLayout) {
            super(linearLayout);
            convienceViewReference = linearLayout;
        }
    }

    BorrowedBooksModel borrowedBooksModel;

    public BorrowedBooksAdapter() {
        super();
        borrowedBooksModel = BorrowedBooksModel.getSingleton();
    }

    @NonNull
    @Override
    public BorrowedBooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_text_view, parent, false);
        BorrowedBooksViewHolder bVH = new BorrowedBooksViewHolder(v);
        return bVH;
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowedBooksViewHolder holder, int position) {
        TextView borrowedBookName = holder.convienceViewReference.findViewById(R.id.bookName);
        borrowedBookName.setText(borrowedBooksModel.borrowedbooksArray.get(position).borrowedBookName);
        TextView user919 = holder.convienceViewReference.findViewById(R.id.bookAuthor);
        user919.setText(borrowedBooksModel.borrowedbooksArray.get(position).borrowedStudentId);
        TextView returnDate = holder.convienceViewReference.findViewById(R.id.bookEdition);
        returnDate.setText(borrowedBooksModel.borrowedbooksArray.get(position).borrowedBookReturnedDate);
    }

    @Override
    public int getItemCount() {
        return borrowedBooksModel.borrowedbooksArray.size();
    }
}

public class BorrowBooksListFragment extends Fragment {

    private BorrowedBooksAdapter borrowedbooksAdapter = null;
    private RecyclerView borrowedBookRV = null;
    private GestureDetectorCompat detector = null;

    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = borrowedBookRV.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder holder = borrowedBookRV.getChildViewHolder(view);

                if (holder instanceof BorrowedBooksAdapter.BorrowedBooksViewHolder) {
                    int position = holder.getAdapterPosition();

                    //Obtaining the title of the book
                    BorrowedBooksModel myModel = BorrowedBooksModel.getSingleton();

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
        // recycler view code.
        borrowedbooksAdapter = new BorrowedBooksAdapter();
        borrowedBookRV = view.findViewById(R.id.borrowedBookRV);
        borrowedBookRV.setAdapter(borrowedbooksAdapter);
        RecyclerView.LayoutManager myManager = new LinearLayoutManager(view.getContext());
        borrowedBookRV.setLayoutManager(myManager);
        detector = new GestureDetectorCompat(view.getContext(), new BorrowBooksListFragment.RecyclerViewOnGestureListener());
        borrowedBookRV.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return detector.onTouchEvent(e);
            }
        });
        return view;
    }
}
