package com.example.android_library_app;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

class StudentBorrowedBooksModel {

    private FirebaseFirestore db;

    public static class StudentBorrowedBooksInfo {
        public String borrowedBookName;
        public String borrowedStudentId;
        public String borrowedDate;
        public String borrowedBookReturnedDate;


        public StudentBorrowedBooksInfo(String borrowedBookName, String borrowedStudentId,
                                        String borrowedDate, String borrowedBookReturnedDate) {
            this.borrowedBookName = borrowedBookName;
            this.borrowedStudentId = borrowedStudentId;
            this.borrowedDate = borrowedDate;
            this.borrowedBookReturnedDate = borrowedBookReturnedDate;
        }
    }

    private static StudentBorrowedBooksModel singleton = null;

    public static StudentBorrowedBooksModel getSingleton() {
        if (singleton == null) {
            singleton = new StudentBorrowedBooksModel();
        }
        return singleton;
    }

    public ArrayList<StudentBorrowedBooksInfo> studentBorrowedbooksArray;

    private StudentBorrowedBooksModel() {
        studentBorrowedbooksArray = new ArrayList<>();
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
                                if(borrowedBook.get("userID").toString().equals(MainActivity.user919)) {
                                    studentBorrowedbooksArray.add(new StudentBorrowedBooksInfo(
                                            borrowedBook.get("bookName").toString(),
                                            borrowedBook.get("userID").toString(),
                                            borrowedBook.get("borrowDate").toString(),
                                            borrowedBook.get("returnDate").toString()
                                    ));
                                }
                            }
                        } else {
                            Log.w("StudentBorrowedBookList", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}


class StudentBorrowedBooksAdapter extends RecyclerView.Adapter<StudentBorrowedBooksAdapter.StudentBorrowedBooksViewHolder> {

    public static class StudentBorrowedBooksViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout convienceViewReference;

        public StudentBorrowedBooksViewHolder(@NonNull LinearLayout linearLayout) {
            super(linearLayout);
            convienceViewReference = linearLayout;
        }
    }

    StudentBorrowedBooksModel studentBorrowedBooksModel;

    public StudentBorrowedBooksAdapter() {
        super();
        studentBorrowedBooksModel = StudentBorrowedBooksModel.getSingleton();
    }

    @NonNull
    @Override
    public StudentBorrowedBooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_student_borrow_list, parent, false);
        StudentBorrowedBooksViewHolder bVH = new StudentBorrowedBooksViewHolder(v);
        return bVH;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentBorrowedBooksViewHolder holder, int position) {
        TextView borrowedBookName = holder.convienceViewReference.findViewById(R.id.borrowedBookName);
        borrowedBookName.setText(studentBorrowedBooksModel.studentBorrowedbooksArray.get(position).borrowedBookName);
        TextView borrowedBookReturnDate = holder.convienceViewReference.findViewById(R.id.borrowedBookReturnDate);
        borrowedBookReturnDate.setText(studentBorrowedBooksModel.studentBorrowedbooksArray.get(position).borrowedBookReturnedDate);
    }

    @Override
    public int getItemCount() {
        return studentBorrowedBooksModel.studentBorrowedbooksArray.size();
    }
}


public class StudentBorrowedBooksListFragment extends Fragment {

    // recycler view.
    private StudentBorrowedBooksAdapter studentBorrowedbooksAdapter = null;
    private RecyclerView studentBorrowedbooksRV = null;
    private GestureDetectorCompat detector = null;
//    public static String bookTitle, bookAuthor, bookGenre, bookDescription, bookPublisher, bookLanguage, bookAvailable;
//    private BookDescriptionFragment bookDescriptionFragment = new BookDescriptionFragment();


    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = studentBorrowedbooksRV.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder holder = studentBorrowedbooksRV.getChildViewHolder(view);

                if (holder instanceof StudentBorrowedBooksAdapter.StudentBorrowedBooksViewHolder) {
                    int position = holder.getAdapterPosition();

                    //Obtaining the title of the book
                    StudentBorrowedBooksModel myModel = StudentBorrowedBooksModel.getSingleton();


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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_borrowed_books_list, container, false);

        // recycler view code.
        studentBorrowedbooksAdapter = new StudentBorrowedBooksAdapter();
        studentBorrowedbooksRV = view.findViewById(R.id.studentBorrowedBookRV);
        studentBorrowedbooksRV.setAdapter(studentBorrowedbooksAdapter);


        RecyclerView.LayoutManager myManager = new LinearLayoutManager(view.getContext());
        studentBorrowedbooksRV.setLayoutManager(myManager);

        detector = new GestureDetectorCompat(view.getContext(), new RecyclerViewOnGestureListener());

        studentBorrowedbooksRV.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return detector.onTouchEvent(e);
            }
        });

        return view;
    }
}
