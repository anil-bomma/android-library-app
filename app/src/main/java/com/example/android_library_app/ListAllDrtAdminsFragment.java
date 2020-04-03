package com.example.android_library_app;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class DptAdminModel {

    public static class DptAdminInfo {
        public String dptAdminName;
        public String dpt_919;
        public String dpt_name;

        public DptAdminInfo(String dptAdminName, String dpt_919, String dpt_name) {
            this.dptAdminName = dptAdminName;
            this.dpt_919 = dpt_919;
            this.dpt_name = dpt_name;
        }
    }

    private static DptAdminModel singleton = null;

    public static DptAdminModel getSingleton() {
        if (singleton == null) {
            singleton = new DptAdminModel();
        }
        return singleton;
    }

    public ArrayList<DptAdminInfo> dptAdminArray;

    private DptAdminModel() {
        dptAdminArray = new ArrayList<>();
        loadModel();
    }

    public void loadModel() {
        dptAdminArray.add(new DptAdminInfo("Mahender Reddy Surkanti", "919585353", "Politics & Business"));
        dptAdminArray.add(new DptAdminInfo("Anil Bomma", "919000000", "Applied Computer Science "));
        dptAdminArray.add(new DptAdminInfo("Deepthi Tejaswani Chokka ", "919000000", "Information Systems"));
        dptAdminArray.add(new DptAdminInfo("Rethimareddy Polam", "919584780", "Arts and Science"));
    }
}


class DptAdminAdapter extends RecyclerView.Adapter<DptAdminAdapter.DptAdminViewHolder> {

    public static class DptAdminViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout convienceViewReference;

        public DptAdminViewHolder(@NonNull LinearLayout linearLayout) {
            super(linearLayout);
            convienceViewReference = linearLayout;
        }
    }

    DptAdminModel dptAdminModel;

    public DptAdminAdapter() {
        super();
        dptAdminModel = DptAdminModel.getSingleton();
    }

    @NonNull
    @Override
    public DptAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_list_dpt_admin, parent, false);
        DptAdminViewHolder dAVH = new DptAdminViewHolder(v);
        return dAVH;
    }

    @Override
    public void onBindViewHolder(@NonNull DptAdminViewHolder holder, int position) {
        TextView adminName = holder.convienceViewReference.findViewById(R.id.dpt_admin_nameTV);
        adminName.setText(dptAdminModel.dptAdminArray.get(position).dptAdminName);
        TextView admin919 = holder.convienceViewReference.findViewById(R.id.dpt_admin_919TV);
        admin919.setText(dptAdminModel.dptAdminArray.get(position).dpt_919);
        TextView dptName = holder.convienceViewReference.findViewById(R.id.dpt_name);
        dptName.setText(dptAdminModel.dptAdminArray.get(position).dpt_name);
    }

    @Override
    public int getItemCount() {
        return dptAdminModel.dptAdminArray.size();
    }
}


public class ListAllDrtAdminsFragment extends Fragment {

    // recycler view.
    private DptAdminAdapter dptAdminAdapter = null;
    private RecyclerView dptAdminRV = null;
    private GestureDetectorCompat detector = null;


    //gesture listener
    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = dptAdminRV.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder holder = dptAdminRV.getChildViewHolder(view);

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


        View view = inflater.inflate(R.layout.fragment_list_all_dpt_admin, container, false);

        // recycler view code.
        dptAdminAdapter = new DptAdminAdapter();
        dptAdminRV = view.findViewById(R.id.myDptAdminRV);
        dptAdminRV.setAdapter(dptAdminAdapter);

        RecyclerView.LayoutManager myManager = new LinearLayoutManager(view.getContext());
        dptAdminRV.setLayoutManager(myManager);

        detector = new GestureDetectorCompat(view.getContext(), new RecyclerViewOnGestureListener());

        dptAdminRV.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return detector.onTouchEvent(e);
            }
        });

        return view;
    }
}