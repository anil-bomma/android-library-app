package com.example.android_library_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

class BooksModel {

    private FirebaseFirestore db;

    public static class BooksInfo {
        public String title;
        public String author;
        public String genre;
        public int available;
        public String description;
        public String publisher;
        public String language;


        public BooksInfo(String title, String author, String genre, String description,
                         String publisher, String language, int available) {
            this.title = title;
            this.author = author;
            this.genre = genre;
            this.available = available;
            this.description = description;
            this.publisher = publisher;
            this.language = language;
        }
    }

    private static BooksModel singleton = null;

    public static BooksModel getSingleton() {
        if (singleton == null) {
            singleton = new BooksModel();
        }
        return singleton;
    }

    public ArrayList<BooksInfo> booksArray;

    private BooksModel() {
        booksArray = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        loadModel();
    }

    public void loadModel() {
        System.out.println("------------load model-----------");
        db.collection("books").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> book = document.getData();
                                booksArray.add(new BooksInfo(
                                        book.get("title").toString(),
                                        book.get("author").toString(),
                                        book.get("genre").toString(),
                                        book.get("description").toString(),
                                        book.get("publisher").toString(),
                                        book.get("language").toString(),
                                        Integer.parseInt(book.get("available").toString())
                                ));
                            }
                        } else {
                            Log.w("ListAllBooks", "Error getting documents.", task.getException());
                        }
                    }
                });

//        booksArray.add(new BooksInfo("C++", "ABC", "12c edition"));
//        booksArray.add(new BooksInfo("JAVA", "DEF", "8th edition"));
//        booksArray.add(new BooksInfo("Android", "GHI", "09 edition"));
//        booksArray.add(new BooksInfo("Project management", "JKL", "12th edition"));
//        booksArray.add(new BooksInfo("Spring frame works", "MNO", "8th edition"));
//        booksArray.add(new BooksInfo("IOS", "PQR", "20th edition"));
//        booksArray.add(new BooksInfo("Big data", "STU", "12 edition"));
//        booksArray.add(new BooksInfo("Data Visualization", "VWX", "8th edition"));
//        booksArray.add(new BooksInfo("Machine Learning", "XYZ", "21st edition"));
    }
}


class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {

    public static class BooksViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout convienceViewReference;

        public BooksViewHolder(@NonNull LinearLayout linearLayout) {
            super(linearLayout);
            convienceViewReference = linearLayout;
        }
    }

    BooksModel booksModel;

    public BooksAdapter() {
        super();
        booksModel = BooksModel.getSingleton();
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_text_view, parent, false);
        BooksViewHolder bVH = new BooksViewHolder(v);
        return bVH;
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        TextView bookName = holder.convienceViewReference.findViewById(R.id.bookName);
        bookName.setText(booksModel.booksArray.get(position).title);
        TextView bookAuthor = holder.convienceViewReference.findViewById(R.id.bookAuthor);
        bookAuthor.setText(booksModel.booksArray.get(position).author);
        TextView bookEdition = holder.convienceViewReference.findViewById(R.id.bookEdition);
        bookEdition.setText(booksModel.booksArray.get(position).publisher);
    }

    @Override
    public int getItemCount() {
        return booksModel.booksArray.size();
    }
}


public class ListAllBooksFragment extends Fragment {

    // recycler view.
    private BooksAdapter booksAdapter = null;
    private RecyclerView booksRV = null;
    private GestureDetectorCompat detector = null;
    public static String bookTitle;
    private BookDescriptionFragment bookDescriptionFragment = new BookDescriptionFragment() ;

    //gesture listener
    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        private Context context;

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = booksRV.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder holder = booksRV.getChildViewHolder(view);

                if (holder instanceof BooksAdapter.BooksViewHolder) {
                    int position = holder.getAdapterPosition();

                    //Obtaining the title of the book
                    BooksModel myModel = BooksModel.getSingleton();
                    bookTitle = myModel.booksArray.get(position).title;
                    Toast.makeText(getContext(), bookTitle, Toast.LENGTH_SHORT).show();

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.fragment_container,bookDescriptionFragment,"book description");
                    transaction.commit();

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new BookDescriptionFragment()).commit();


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


        View view = inflater.inflate(R.layout.fragment_list_all_books, container, false);

        // recycler view code.
        booksAdapter = new BooksAdapter();
        booksRV = view.findViewById(R.id.myRV);
        booksRV.setAdapter(booksAdapter);
        bookDescriptionFragment  = new BookDescriptionFragment();
        RecyclerView.LayoutManager myManager = new LinearLayoutManager(view.getContext());
        booksRV.setLayoutManager(myManager);

        detector = new GestureDetectorCompat(view.getContext(), new RecyclerViewOnGestureListener());

        booksRV.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return detector.onTouchEvent(e);
            }
        });

        return view;
    }
}
