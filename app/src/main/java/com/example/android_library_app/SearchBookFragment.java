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

class SearchBooksModel {

    private FirebaseFirestore db;

    public static class SearchBooksInfo {
        public String title;
        public String author;
        public String genre;
        public String available;
        public String description;
        public String publisher;
        public String language;


        public SearchBooksInfo(String title, String author, String genre, String description,
                               String publisher, String language, String available) {
            this.title = title;
            this.author = author;
            this.genre = genre;
            this.available = available;
            this.description = description;
            this.publisher = publisher;
            this.language = language;
        }
    }

    private static SearchBooksModel singleton = null;

    public static SearchBooksModel getSingleton() {
        if (singleton == null) {
            singleton = new SearchBooksModel();
        }
        return singleton;
    }

    public ArrayList<SearchBooksInfo> searchbooksArray;

    private SearchBooksModel() {
        searchbooksArray = new ArrayList<>();
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
                                searchbooksArray.add(new SearchBooksInfo(
                                        book.get("title").toString(),
                                        book.get("author").toString(),
                                        book.get("genre").toString(),
                                        book.get("description").toString(),
                                        book.get("publisher").toString(),
                                        book.get("language").toString(),
                                        book.get("available").toString()
                                ));
                            }
                        } else {
                            Log.w("ListAllBooks", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}


class SearchBooksAdapter extends RecyclerView.Adapter<SearchBooksAdapter.SearchBooksViewHolder> {

    public static class SearchBooksViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout convienceViewReference;

        public SearchBooksViewHolder(@NonNull LinearLayout linearLayout) {
            super(linearLayout);
            convienceViewReference = linearLayout;
        }
    }

    SearchBooksModel searchbooksModel;

    public SearchBooksAdapter() {
        super();
        searchbooksModel = SearchBooksModel.getSingleton();
    }

    @NonNull
    @Override
    public SearchBooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_text_view, parent, false);
        SearchBooksViewHolder bVH = new SearchBooksViewHolder(v);
        return bVH;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchBooksViewHolder holder, int position) {
        TextView bookName = holder.convienceViewReference.findViewById(R.id.bookName);
        bookName.setText(searchbooksModel.searchbooksArray.get(position).title);
        TextView bookAuthor = holder.convienceViewReference.findViewById(R.id.bookAuthor);
        bookAuthor.setText(searchbooksModel.searchbooksArray.get(position).author);
        TextView bookEdition = holder.convienceViewReference.findViewById(R.id.bookEdition);
        bookEdition.setText(searchbooksModel.searchbooksArray.get(position).publisher);
    }

    @Override
    public int getItemCount() {
        return searchbooksModel.searchbooksArray.size();
    }
}


public class SearchBookFragment extends Fragment {
    Button searchBTN;
    EditText searchBookET;

    // recycler view.
    private SearchBooksAdapter searchbooksAdapter = null;
    private RecyclerView searchbooksRV = null;
    private GestureDetectorCompat detector = null;
    public static String bookTitle, bookAuthor, bookGenre, bookDescription, bookPublisher, bookLanguage, bookAvailable;
    private BookDescriptionFragment bookDescriptionFragment = new BookDescriptionFragment();


    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = searchbooksRV.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder holder = searchbooksRV.getChildViewHolder(view);

                if (holder instanceof SearchBooksAdapter.SearchBooksViewHolder) {
                    int position = holder.getAdapterPosition();

                    //Obtaining the title of the book
                    SearchBooksModel myModel = SearchBooksModel.getSingleton();
                    ListAllBooksFragment.bookTitle = myModel.searchbooksArray.get(position).title;
                    ListAllBooksFragment.bookAuthor = myModel.searchbooksArray.get(position).author;
                    ListAllBooksFragment.bookGenre = myModel.searchbooksArray.get(position).genre;
                    ListAllBooksFragment.bookAvailable = myModel.searchbooksArray.get(position).available;
                    ListAllBooksFragment.bookDescription = myModel.searchbooksArray.get(position).description;
                    ListAllBooksFragment.bookPublisher = myModel.searchbooksArray.get(position).publisher;
                    ListAllBooksFragment.bookLanguage = myModel.searchbooksArray.get(position).language;


                    Toast.makeText(getContext(), bookTitle, Toast.LENGTH_SHORT).show();

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.fragment_container, bookDescriptionFragment, "book description");
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
        View view = inflater.inflate(R.layout.fragment_search_book, container, false);


        // recycler view code.
        searchbooksAdapter = new SearchBooksAdapter();
        searchbooksRV = view.findViewById(R.id.mySearchBookRV);
        searchbooksRV.setAdapter(searchbooksAdapter);

        bookDescriptionFragment = new BookDescriptionFragment();
        RecyclerView.LayoutManager myManager = new LinearLayoutManager(view.getContext());
        searchbooksRV.setLayoutManager(myManager);

        detector = new GestureDetectorCompat(view.getContext(), new RecyclerViewOnGestureListener());

        searchbooksRV.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return detector.onTouchEvent(e);
            }
        });


//        final AutoCompleteTextView searchBookET = (AutoCompleteTextView) view.findViewById(R.id.searchBookET);
//        String[] books = getResources().getStringArray(R.array.book_names);
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, books);
//        searchBookET.setAdapter(adapter);
//
//        searchBTN = view.findViewById(R.id.searchBTN);
//        searchBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                searchBookET.getText();
//                System.out.println("testing");
//            }
//        });
        return view;
    }
}
