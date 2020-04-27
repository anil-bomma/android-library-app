package com.example.android_library_app;

import android.graphics.Color;
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
import android.widget.ProgressBar;
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
    public ArrayList<SearchBooksInfo> searchBooksArray;

    public static SearchBooksModel getSingleton(ArrayList<SearchBooksInfo> booksArray) {
        singleton = new SearchBooksModel(booksArray);
        return singleton;
    }


    private SearchBooksModel(ArrayList<SearchBooksInfo> booksArray) {
        searchBooksArray = booksArray;
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

    public SearchBooksAdapter(ArrayList<SearchBooksModel.SearchBooksInfo> booksArray) {
        searchbooksModel = SearchBooksModel.getSingleton(booksArray);
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
        bookName.setText(searchbooksModel.searchBooksArray.get(position).title);
        TextView bookAuthor = holder.convienceViewReference.findViewById(R.id.bookAuthor);
        bookAuthor.setText(searchbooksModel.searchBooksArray.get(position).author);
        TextView bookEdition = holder.convienceViewReference.findViewById(R.id.bookEdition);
        bookEdition.setText(searchbooksModel.searchBooksArray.get(position).publisher);
    }

    @Override
    public int getItemCount() {
        return searchbooksModel.searchBooksArray.size();
    }
}


public class SearchBookFragment extends Fragment {

    private FirebaseFirestore db;
    private ArrayList<SearchBooksModel.SearchBooksInfo> booksArray;


    Button searchBTN;
    EditText searchBookET;
    TextView searchResultTV;
    ProgressBar searchProgressBar;

    // recycler view.
    private SearchBooksAdapter searchbooksAdapter = null;
    private RecyclerView searchbooksRV = null;
    private GestureDetectorCompat detector = null;
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
                    SearchBooksModel myModel = SearchBooksModel.getSingleton(booksArray);
                    ListAllBooksFragment.bookTitle = myModel.searchBooksArray.get(position).title;
                    ListAllBooksFragment.bookAuthor = myModel.searchBooksArray.get(position).author;
                    ListAllBooksFragment.bookGenre = myModel.searchBooksArray.get(position).genre;
                    ListAllBooksFragment.bookAvailable = myModel.searchBooksArray.get(position).available;
                    ListAllBooksFragment.bookDescription = myModel.searchBooksArray.get(position).description;
                    ListAllBooksFragment.bookPublisher = myModel.searchBooksArray.get(position).publisher;
                    ListAllBooksFragment.bookLanguage = myModel.searchBooksArray.get(position).language;


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

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        booksArray = new ArrayList<>();

        final View view = inflater.inflate(R.layout.fragment_search_book, container, false);

        searchProgressBar = view.findViewById(R.id.searchProgressBar);
        searchBookET = view.findViewById(R.id.searchBookET);
        searchResultTV = view.findViewById(R.id.searchResult);
        searchResultTV.setVisibility(View.GONE);

        searchBTN = view.findViewById(R.id.searchBTN);
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchResultTV.setVisibility(View.GONE);
                searchProgressBar.setVisibility(View.VISIBLE);
                booksArray.clear();
                final String bookName = searchBookET.getText().toString().trim();

                if (bookName == null || bookName.isEmpty()) {
                    searchBookET.setError("Please enter the book name to search");
                    Toast toast = Toast.makeText(getContext(),
                            "Please enter book name and search..!",
                            Toast.LENGTH_LONG);
                    View toastView = toast.getView();
                    toastView.setBackgroundColor(Color.parseColor("#F07878"));
                    searchProgressBar.setVisibility(View.GONE);
                    toast.show();
                    return;
                }

                db.collection("books")
                        .whereGreaterThanOrEqualTo("title", bookName)
                        .whereLessThanOrEqualTo("title", bookName + "\uf8ff")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Map<String, Object> book = document.getData();
                                        booksArray.add(new SearchBooksModel.SearchBooksInfo(
                                                book.get("title").toString(),
                                                book.get("author").toString(),
                                                book.get("genre").toString(),
                                                book.get("description").toString(),
                                                book.get("publisher").toString(),
                                                book.get("language").toString(),
                                                book.get("available").toString()
                                        ));
                                    }

                                    searchResultTV.setText(booksArray.size() +
                                            " results found with given book name: '" + bookName + "'");
                                    searchResultTV.setVisibility(View.VISIBLE);

                                    // recycler view code.
                                    searchbooksAdapter = new SearchBooksAdapter(booksArray);
                                    searchbooksRV = view.findViewById(R.id.mySearchBookRV);
                                    searchProgressBar.setVisibility(View.GONE);
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

                                } else {
                                    Toast toast = Toast.makeText(getContext(),
                                            "Error getting documents " + task.getException().getMessage(),
                                            Toast.LENGTH_LONG);
                                    View toastView = toast.getView();
                                    toastView.setBackgroundColor(Color.parseColor("#F07878"));
                                    searchProgressBar.setVisibility(View.GONE);
                                    toast.show();
                                    Log.w("ListAllBooks", "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
        });

        return view;
    }
}
