package com.example.android_library_app;

import android.content.Context;
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
import android.widget.ProgressBar;
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

    public static class BooksInfo {
        public String title;
        public String author;
        public String genre;
        public String available;
        public String description;
        public String publisher;
        public String language;
        public String sectionID;
        public String rackID;


        public BooksInfo(String title, String author, String genre, String description,
                         String publisher, String language, String available, String sectionID,
                         String rackID) {
            this.title = title;
            this.author = author;
            this.genre = genre;
            this.available = available;
            this.description = description;
            this.publisher = publisher;
            this.language = language;
            this.sectionID = sectionID;
            this.rackID = rackID;
        }
    }

    private static BooksModel singleton = null;
    public ArrayList<BooksInfo> booksArray;


    public static BooksModel getSingletonWithDB(ArrayList<BooksInfo> booksArray) {
        singleton = new BooksModel(booksArray);
        return singleton;
    }

    private BooksModel(ArrayList<BooksInfo> booksArray) {
        this.booksArray = booksArray;
    }

}


class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {

    BooksModel booksModel;

    // parameterised constructor
    public BooksAdapter(ArrayList<BooksModel.BooksInfo> booksArray) {
        booksModel = BooksModel.getSingletonWithDB(booksArray);
    }

    public static class BooksViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout convienceViewReference;

        public BooksViewHolder(@NonNull LinearLayout linearLayout) {
            super(linearLayout);
            convienceViewReference = linearLayout;
        }
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

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_text_view, parent, false);
        BooksViewHolder bVH = new BooksViewHolder(v);
        return bVH;
    }

    @Override
    public int getItemCount() {
        return booksModel.booksArray.size();
    }
}


public class ListAllBooksFragment extends Fragment {

    // fire-store database
    private FirebaseFirestore db;
    private ArrayList<BooksModel.BooksInfo> booksArray;
    ProgressBar loadBooksBar;

    // recycler view.
    private BooksAdapter booksAdapter = null;
    private RecyclerView booksRV = null;
    private GestureDetectorCompat detector = null;
    public static String bookTitle, bookAuthor, bookGenre, bookDescription, bookPublisher,
            bookLanguage, bookAvailable, bookSection, bookRack;
    private BookDescriptionFragment bookDescriptionFragment = new BookDescriptionFragment();

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
                    BooksModel myModel = BooksModel.getSingletonWithDB(booksArray);
                    bookTitle = myModel.booksArray.get(position).title;
                    bookAuthor = myModel.booksArray.get(position).author;
                    bookGenre = myModel.booksArray.get(position).genre;
                    bookAvailable = myModel.booksArray.get(position).available;
                    bookDescription = myModel.booksArray.get(position).description;
                    bookPublisher = myModel.booksArray.get(position).publisher;
                    bookLanguage = myModel.booksArray.get(position).language;
                    bookSection = myModel.booksArray.get(position).sectionID;
                    bookRack = myModel.booksArray.get(position).rackID;

//                    Toast.makeText(getContext(), bookTitle, Toast.LENGTH_SHORT).show();

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.fragment_container, bookDescriptionFragment, "book description");
                    transaction.addToBackStack(null);
                    transaction.commit();

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new BookDescriptionFragment()).addToBackStack(null).commit();


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


        final View view = inflater.inflate(R.layout.fragment_list_all_books, container, false);

        loadBooksBar = view.findViewById(R.id.loadBooksBar);
        loadBooksBar.setVisibility(View.VISIBLE);


        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        booksArray = new ArrayList<>();


        db.collection("books").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> book = document.getData();
                                System.out.println(book);
                                booksArray.add(new BooksModel.BooksInfo(
                                        book.get("title").toString(),
                                        book.get("author").toString(),
                                        book.get("genre").toString(),
                                        book.get("description").toString(),
                                        book.get("publisher").toString(),
                                        book.get("language").toString(),
                                        book.get("available").toString(),
                                        book.get("sectionID").toString(),
                                        book.get("rackID").toString()
                                ));
                            }

                            // recycler view code.
                            loadBooksBar.setVisibility(View.GONE);
                            booksAdapter = new BooksAdapter(booksArray);
                            booksRV = view.findViewById(R.id.myRV);

                            bookDescriptionFragment = new BookDescriptionFragment();
                            RecyclerView.LayoutManager myManager = new LinearLayoutManager(view.getContext());
                            booksRV.setLayoutManager(myManager);
                            booksAdapter.notifyDataSetChanged();
                            booksRV.setAdapter(booksAdapter);
                            booksAdapter.notifyDataSetChanged();

                            detector = new GestureDetectorCompat(view.getContext(), new RecyclerViewOnGestureListener());

                            booksRV.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
                                @Override
                                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                                    return detector.onTouchEvent(e);
                                }
                            });

                        } else {
                            Log.w("ListAllBooks", "Error getting documents.", task.getException());
                        }
                    }
                });

        return view;
    }
}
