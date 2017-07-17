package com.thethinktankmit.thethinktank;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class faq extends Fragment {


    public static faq newInstance() {
        faq fragment = new faq();
        return fragment;
    }

    public faq() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.faq, container, false);
        LinearLayout fb = (LinearLayout) rootView.findViewById(R.id.fbttt);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fb_click1();
            }
        });
        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("FAQ");
    }
    public void fb_click1() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + aboutus.FACEBOOK_URL));
            intent.setPackage("com.facebook.katana");
            startActivity(intent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/thethinktankmit")));
        }
    }
}
