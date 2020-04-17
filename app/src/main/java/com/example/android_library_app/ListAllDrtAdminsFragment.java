package com.example.android_library_app;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

class DptAdminModel {
    private FirebaseFirestore db;

    public static class DptAdminInfo {
        public String dptAdminName;
        public String dpt_919;
        public String dptAdminMail;

        public DptAdminInfo(String dptAdminName, String dpt_919, String dptAdminMail) {
            this.dptAdminName = dptAdminName;
            this.dpt_919 = dpt_919;
            this.dptAdminMail = dptAdminMail;
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
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        loadModel();
    }

    public void loadModel() {
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> user = document.getData();
                        if (user.get("role").toString().equals("departmentAdmin")) {
                            dptAdminArray.add(new DptAdminInfo(
                                    user.get("firstname").toString() + " " + user.get("lastname").toString(),
                                    user.get("studentId").toString(),
                                    user.get("emailId").toString()
                            ));
                        }
                    }
                } else {
                    Log.w("ListAllBooks", "Error getting documents.", task.getException());
                }
            }
        });

//        dptAdminArray.add(new DptAdminInfo("Mahender Reddy Surkanti", "919585353", "Politics & Business"));
//        dptAdminArray.add(new DptAdminInfo("Anil Bomma", "919000000", "Applied Computer Science "));
//        dptAdminArray.add(new DptAdminInfo("Deepthi Tejaswani Chokka ", "919000000", "Information Systems"));
//        dptAdminArray.add(new DptAdminInfo("Rethimareddy Polam", "919584780", "Arts and Science"));

    }
}


class DptAdminAdapter extends RecyclerView.Adapter<DptAdminAdapter.DptAdminViewHolder> {

    public static class DptAdminViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout convienceViewReference;
        MyOnClick myOnClick;

        private Button removeBTN;

        public DptAdminViewHolder(@NonNull LinearLayout linearLayout, MyOnClick myOnClick) {
            super(linearLayout);
            removeBTN = linearLayout.findViewById(R.id.removeDptAdminButton);
            convienceViewReference = linearLayout;
            this.myOnClick = myOnClick;
            removeBTN.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myOnClick.onItemClick(getAdapterPosition());
        }
    }

    private MyOnClick myOnClick;
    DptAdminModel dptAdminModel;

    public DptAdminAdapter(MyOnClick myOnClick) {
        super();
        this.myOnClick = myOnClick;
        dptAdminModel = DptAdminModel.getSingleton();
    }

    @NonNull
    @Override
    public DptAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_list_dpt_admin, parent, false);
        DptAdminViewHolder dAVH = new DptAdminViewHolder(v, myOnClick);
        return dAVH;
    }

    @Override
    public void onBindViewHolder(@NonNull DptAdminViewHolder holder, int position) {
        TextView adminName = holder.convienceViewReference.findViewById(R.id.dpt_admin_nameTV);
        adminName.setText(dptAdminModel.dptAdminArray.get(position).dptAdminName);
        TextView admin919 = holder.convienceViewReference.findViewById(R.id.dpt_admin_919TV);
        admin919.setText(dptAdminModel.dptAdminArray.get(position).dpt_919);
        TextView dptName = holder.convienceViewReference.findViewById(R.id.dpt_name);
        dptName.setText(dptAdminModel.dptAdminArray.get(position).dptAdminMail);
    }

    @Override
    public int getItemCount() {
        return dptAdminModel.dptAdminArray.size();
    }

    public interface MyOnClick {
        void onItemClick(int position);
    }
}


public class ListAllDrtAdminsFragment extends Fragment implements DptAdminAdapter.MyOnClick {

    // recycler view.
    private DptAdminAdapter dptAdminAdapter = null;
    private RecyclerView dptAdminRV = null;
    private GestureDetectorCompat detector = null;
    public static String KEY_DptAdmin919 = "";

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "The position is " + position, Toast.LENGTH_SHORT).show();
        RemoveDptAdminDialogBox dialogBox = new RemoveDptAdminDialogBox();
        dialogBox.show((getActivity().getSupportFragmentManager()), "delete dialog");
    }


    //gesture listener
    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = dptAdminRV.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder holder = dptAdminRV.getChildViewHolder(view);

                if (holder instanceof DptAdminAdapter.DptAdminViewHolder) {
                    int position = holder.getAdapterPosition();
                    DptAdminModel myDptAdminModel = DptAdminModel.getSingleton();
                    String itemClicked919 = myDptAdminModel.dptAdminArray.get(position).dpt_919;
                    KEY_DptAdmin919 = itemClicked919;
                    Toast.makeText(getContext(), " Item clicked is " + itemClicked919, Toast.LENGTH_SHORT).show();
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


        View view = inflater.inflate(R.layout.fragment_list_all_dpt_admin, container, false);

        Button removeBTN;
        // recycler view code.
        dptAdminAdapter = new DptAdminAdapter(this);
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
