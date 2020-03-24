package com.example.android_library_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static java.net.Proxy.Type.HTTP;

public class ContactMenuFragment extends Fragment {
    Button mobileBTN,emailIDBTN;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_contact, container, false);

        mobileBTN=view.findViewById(R.id.mobileBTN);

        mobileBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Uri number = Uri.parse("tel:+16605280951");
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    startActivity(callIntent);
        }
        });

        emailIDBTN=view.findViewById(R.id.emailIDBTN);

//        emailIDBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent emailIntent = new Intent(Intent.ACTION_SEND);
//
//                //emailIntent.setType(HTTP.toString());
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"libraryowens@gmail.com"});
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
//                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
//                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
//                startActivity(emailIntent);
//
//            }
//        });


    return view;
    }
}
