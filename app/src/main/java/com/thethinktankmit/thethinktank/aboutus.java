package com.thethinktankmit.thethinktank;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class aboutus extends Fragment {

    public static aboutus newInstance() {
        aboutus fragment = new aboutus();
        return fragment;
    }

    public aboutus() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("About Us");
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.aboutus, container, false);

        //FB ka Intent
        LinearLayout fb = (LinearLayout) rootView.findViewById(R.id.fbclk);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fb_click1();
            }
        });

        //YouTube ka Intent
        LinearLayout yt = (LinearLayout) rootView.findViewById(R.id.ytclk);
        yt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yt_click1();
            }
        });

        //Twitter ka Intent
        LinearLayout tw = (LinearLayout) rootView.findViewById(R.id.twclk);
        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tw_click1();
            }
        });

        //IG ka Intent
        LinearLayout ig = (LinearLayout) rootView.findViewById(R.id.igclk);
        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ig_click1();
            }
        });

        //Sahni
        LinearLayout sahni = (LinearLayout) rootView.findViewById(R.id.sahni);
        sahni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_facBook("https://www.facebook.com/abhay.sahni08");
            }
        });

        //Verma
        LinearLayout verma = (LinearLayout) rootView.findViewById(R.id.verma);
        verma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_facBook("https://www.facebook.com/dhruv2scs");
            }
        });

        //Suri
        LinearLayout suri = (LinearLayout) rootView.findViewById(R.id.suri);
        suri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_facBook("https://www.facebook.com/kiki.lulli");
            }
        });

        //Fotu
        LinearLayout fotu = (LinearLayout) rootView.findViewById(R.id.fotu);
        fotu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_facBook("https://www.facebook.com/rohanagarwal97?fref=ts");
            }
        });

        //Rwik
        LinearLayout rwik = (LinearLayout) rootView.findViewById(R.id.rwik);
        rwik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_facBook("https://www.facebook.com/Rkamilya?fref=ts");
            }
        });

        //Shreya
        LinearLayout shreya = (LinearLayout) rootView.findViewById(R.id.shreya);
        shreya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_facBook("https://www.facebook.com/shreya.choudhury.7330?fref=ts");
            }
        });

        //Arnav
        LinearLayout arnav = (LinearLayout) rootView.findViewById(R.id.arnav);
        arnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_facBook("https://www.facebook.com/ArnavVashisth?fref=ts");
            }
        });

        //Sheryl
        LinearLayout sheryl = (LinearLayout) rootView.findViewById(R.id.sheryl);
        sheryl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_facBook("https://www.facebook.com/Sheryl.Aggarwal?fref=ts");
            }
        });

        //Iyer
        LinearLayout iyer = (LinearLayout) rootView.findViewById(R.id.iyer);
        iyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_facBook("https://www.facebook.com/abhay.iyer.3?fref=ts");
            }
        });

        //Nayak
        LinearLayout nayak = (LinearLayout) rootView.findViewById(R.id.nayak);
        nayak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_facBook("https://www.facebook.com/profile.php?id=100012287942923&fref=ts");
            }
        });

        //Raghu
        LinearLayout raghu = (LinearLayout) rootView.findViewById(R.id.raghu);
        raghu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_facBook("https://www.facebook.com/RaghuRadhakrishnan?fref=ts");
            }
        });

        //TTT FB
        LinearLayout info = (LinearLayout) rootView.findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String link=FACEBOOK_URL;
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("Do you want to visit The Think Tank's Facebook page?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + link));
                                    intent.setPackage("com.facebook.katana");
                                    startActivity(intent);
                                } catch (Exception e) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                                }
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });



        return rootView;
    }


    public void open_facBook(String link1){

        final String link=link1;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Do you want to view this profile on Facebook?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + link));
                            intent.setPackage("com.facebook.katana");
                            startActivity(intent);
                        } catch (Exception e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public static String FACEBOOK_URL = "https://www.facebook.com/TheThinkTankMIT";

    public void fb_click1() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + FACEBOOK_URL));
            intent.setPackage("com.facebook.katana");
            startActivity(intent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/thethinktankmit")));
        }
    }

    public void yt_click1() {
        String url = "https://www.youtube.com/channel/UCm2_2oD4uXoHINRPrmJXzxw";
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }

    public void tw_click1() {
        Intent intent = null;
        try {
            // get the Twitter app if possible
            intent.setPackage("com.twitter.android");
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=thethinktankmit"));
            startActivity(intent);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/thethinktankmit"));
            startActivity(intent);
        }
    }

    public void ig_click1() {
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/thethinktankmit"));
            intent.setPackage("com.instagram.android");
            startActivity(intent);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/thethinktankmit"));
            startActivity(intent);
        }
    }
}
