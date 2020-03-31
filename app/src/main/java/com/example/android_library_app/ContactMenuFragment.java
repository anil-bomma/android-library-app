package com.example.android_library_app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

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

        emailIDBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={"libraryowens@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Questions to be Answered");
                intent.putExtra(Intent.EXTRA_TEXT,"Kindly write your query here...");
                intent.putExtra(Intent.EXTRA_CC,"mailcc@gmail.com");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));

            }
        });


    return view;
    }
}
