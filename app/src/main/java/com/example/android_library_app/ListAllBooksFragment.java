package com.example.android_library_app;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

class BooksModel {

    public static class BooksInfo {
        public String bookName;
        public String bookAutor;
        public String bookEdition;

        public BooksInfo(String bookName, String bookAutor, String bookEdition) {
            this.bookName = bookName;
            this.bookAutor = bookAutor;
            this.bookEdition = bookEdition;
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
        loadModel();
    }

    public void loadModel() {
        booksArray.add(new BooksInfo("C++", "ABC", "12c edition"));
        booksArray.add(new BooksInfo("JAVA", "DEF", "8th edition"));
        booksArray.add(new BooksInfo("Android", "GHI", "09 edition"));
        booksArray.add(new BooksInfo("Project management", "JKL", "12th edition"));
        booksArray.add(new BooksInfo("Spring frame works", "MNO", "8th edition"));
        booksArray.add(new BooksInfo("IOS", "PQR", "20th edition"));
        booksArray.add(new BooksInfo("Big data", "STU", "12 edition"));
        booksArray.add(new BooksInfo("Data Visualization", "VWX", "8th edition"));
        booksArray.add(new BooksInfo("Machine Learning", "XYZ", "21st edition"));
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
        bookName.setText(booksModel.booksArray.get(position).bookName);
        TextView bookAuthor = holder.convienceViewReference.findViewById(R.id.bookAuthor);
        bookAuthor.setText(booksModel.booksArray.get(position).bookAutor);
        TextView bookEdition = holder.convienceViewReference.findViewById(R.id.bookEdition);
        bookEdition.setText(booksModel.booksArray.get(position).bookEdition);
    }

    @Override
    public int getItemCount() {
        return booksModel.booksArray.size();
    }
}


//public class ListAllBooksFragment extends Fragment {
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_list_all_books, container, false);
//    }
//}


public class ListAllBooksFragment extends Fragment {

    // recycler view.
    private BooksAdapter booksAdapter = null;
    private RecyclerView booksRV = null;
    private GestureDetectorCompat detector = null;


    //gesture listener
    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = booksRV.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder holder = booksRV.getChildViewHolder(view);

                if (holder instanceof BooksAdapter.BooksViewHolder) {
                    int position = holder.getAdapterPosition();

                    // handling single tap.
                    Log.d("click", "clicked on item " + position);
                    BooksModel myModel = BooksModel.getSingleton();
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
