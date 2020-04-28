package com.example.android_library_app;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

    public static DptAdminModel getSingleton(ArrayList<DptAdminModel.DptAdminInfo> deptArray) {
        singleton = new DptAdminModel(deptArray);
        return singleton;
    }

    public static ArrayList<DptAdminInfo> dptAdminArray;

    private DptAdminModel(ArrayList<DptAdminModel.DptAdminInfo> deptArray) {
        dptAdminArray = deptArray;
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

    public DptAdminAdapter(MyOnClick myOnClick, ArrayList<DptAdminModel.DptAdminInfo> deptArray) {
        super();
        this.myOnClick = myOnClick;
        dptAdminModel = DptAdminModel.getSingleton(deptArray);
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

    private FirebaseFirestore db;

    private ArrayList<DptAdminModel.DptAdminInfo> dptAdminArray;
    ProgressBar deptAdminBar;
    TextView errorTV;


    // recycler view.
    private DptAdminAdapter dptAdminAdapter = null;
    private RecyclerView dptAdminRV = null;
    private GestureDetectorCompat detector = null;
    public static DptAdminModel.DptAdminInfo KEY_DptAdmin;

    @Override
    public void onItemClick(int position) {
        RemoveDptAdminDialogBox dialogBox = new RemoveDptAdminDialogBox(dptAdminAdapter, DptAdminModel.dptAdminArray);
        dialogBox.show((getActivity().getSupportFragmentManager()), "delete dialog");
    }

    //gesture listener
    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = dptAdminRV.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder holder = dptAdminRV.getChildViewHolder(view);
                // handling single tap.
                if (holder instanceof DptAdminAdapter.DptAdminViewHolder) {
                    int position = holder.getAdapterPosition();
                    DptAdminModel myDptAdminModel = DptAdminModel.getSingleton(dptAdminArray);
                    KEY_DptAdmin = myDptAdminModel.dptAdminArray.get(position);
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


        final View view = inflater.inflate(R.layout.fragment_list_all_dpt_admin, container, false);
        deptAdminBar = view.findViewById(R.id.deptAdminBar);
        errorTV = view.findViewById(R.id.errorTV);

        Button removeBTN;

        dptAdminArray = new ArrayList<>();
        db.collection("users")
                .whereEqualTo("role", "departmentAdmin")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> user = document.getData();
                                dptAdminArray.add(new DptAdminModel.DptAdminInfo(
                                        user.get("firstname").toString() + " " +
                                                user.get("lastname").toString(),
                                        user.get("studentId").toString(),
                                        user.get("emailId").toString()
                                ));
                            }

                            if (dptAdminArray.size() < 1) {
                                errorTV.setText("No user found...!");
                                errorTV.setVisibility(View.VISIBLE);
                                deptAdminBar.setVisibility(View.GONE);
                                return;
                            }

                            // recycler view code.
                            dptAdminAdapter = new DptAdminAdapter(ListAllDrtAdminsFragment.this, dptAdminArray);
                            dptAdminRV = view.findViewById(R.id.myDptAdminRV);
                            dptAdminRV.setAdapter(dptAdminAdapter);
                            deptAdminBar.setVisibility(View.GONE);


                            RecyclerView.LayoutManager myManager = new LinearLayoutManager(view.getContext());
                            dptAdminRV.setLayoutManager(myManager);

                            detector = new GestureDetectorCompat(view.getContext(), new RecyclerViewOnGestureListener());

                            dptAdminRV.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
                                @Override
                                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                                    return detector.onTouchEvent(e);
                                }
                            });
                        } else {
                            deptAdminBar.setVisibility(View.GONE);
                            Log.w("ListAllBooks", "Error getting documents.", task.getException());
                        }
                    }
                });


        return view;
    }

}
