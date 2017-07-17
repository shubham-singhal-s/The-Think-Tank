package com.thethinktankmit.thethinktank;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;



public class home extends Fragment {
    String message;
    String full_picture;
    String link;
    String base = "http://tedxmanipaluniversity.com/app/not.html";
    String appPackageName = "com.thethinktankmit.thethinktank";
    View v;
    LinearLayout xyz;
    View rootView;
    GraphRequest request;
    int i=0;
    int n = 15;
    int check=0;
    String app_v,txt_v;



    public static home newInstance() {
        home fragment = new home();
        return fragment;
    }

    public home() {
    }

    String toke;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        app_v = sharedpreferences.getString("vers_app", "");
        txt_v = sharedpreferences.getString("vers_txt", "");

  //      Toast.makeText(getActivity(), "************"+token+"********************", Toast.LENGTH_SHORT).show();


        if(!app_v.equals(txt_v)&&checkNet(getActivity())){
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(getActivity());
                }
                builder.setTitle("Update to v"+txt_v)
                        .setMessage("A new update is available, please update to get the newest features. \n Proceed?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.thethinktankmit.thethinktank")));
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
        }

        getActivity().setTitle("Feed");
    }
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home, container, false);

        super.onCreate(savedInstanceState);//SAMSUNG-SGH-I997
        if((Build.MODEL).equals("SAMSUNG-SGH-I997")||(Build.MODEL).equals("GT-N7100")||(Build.MODEL).equals("GT-N7000")||(((Build.MODEL).length()>4)&&(Build.MODEL).substring(0,5).equals("GT-N7"))){
            n=8;
        }
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        toke = sharedpreferences.getString("token", "");



        final WebView webView = (WebView) rootView.findViewById(R.id.homeweb);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        WebViewClient aw = new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                play(url);
                return true;
            }};
        webView.setWebViewClient(aw);
        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipehome);
        if(!checkNet(getActivity())){
            Toast.makeText(getActivity(),"Please check your Internet Connection!", Toast.LENGTH_LONG).show();
            rootView.findViewById(R.id.imgcl).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.meme).setVisibility(View.VISIBLE);
            webView.loadData("<html><body bgcolor=\"white\"></body></html>","text/html; charset=UTF-8",null);
        }
        else{
            Toast.makeText(getActivity(),"Loading Feed...", Toast.LENGTH_SHORT).show();
            webView.loadUrl(base);
        }


        /**ImageView iv = (ImageView) rootView.findViewById(R.id.imgcl);
         iv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        if(checkNet(getActivity())){
        rootView.findViewById(R.id.imgcl).setVisibility(View.GONE);
        webView.loadUrl(base);
        Toast.makeText(getActivity(),"Loading Feed...",Toast.LENGTH_LONG).show();
        request.executeAsync();
        }

        else{
        Toast.makeText(getActivity(),"Please check your Internet Connection!",Toast.LENGTH_LONG).show();
        }

        }
        });*/


        xyz = (LinearLayout) rootView.findViewById(R.id.page);
        AccessToken accessToken= new AccessToken(toke,getString(R.string.appid),getString(R.string.userId),null,null,null,null,null);
        request = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try{if (object!=null) {

                            while(i<n) {

                                JSONArray arr=object.getJSONObject("posts").getJSONArray("data");
                                if( arr.getJSONObject(i).has("message")
                                        &&  arr.getJSONObject(i).has("full_picture"))
                                {
                                    message = arr.getJSONObject(i).getString("message");
                                    full_picture = arr.getJSONObject(i).getString("full_picture");
                                    link =  arr.getJSONObject(i).getString("permalink_url");
                                    insertView(message,full_picture,link, inflater, container);
                                    if(message.equals("Credits: Agnihotra Bhattacharya")){
                                        check=1;
                                    }
                                }

                                else if( arr.getJSONObject(i).has("picture")){
                                    n++;
                                 /* full_picture = object.getJSONObject("posts").getJSONArray("data").getJSONObject(i).getString("full_picture");

                                   insertViewB(full_picture, inflater, container);*/
                                }


                                else if( arr.getJSONObject(i).has("message"))
                                {
                                    message =  arr.getJSONObject(i).getString("message");
                                    link =  arr.getJSONObject(i).getString("permalink_url");
                                    insertViewA(message,link,inflater, container);
                                }

                                i++;

                            }


                        }

                        }

                        catch(Exception e)
                        {}

                    }
                });
        //End Of GraphRequest


        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,posts.limit(100){picture,full_picture,message,permalink_url}");
        request.setParameters(parameters);
        request.executeAsync();

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Insert your code here
                swipeLayout.setRefreshing(false);
                if(toke.equals("EAAYfayAh8xIBAIdlDqMQYlTgV1JSa6LzLa1JXD3eXh9oxFwvtCd1ZCFrDU8hhWqX040ByMy1syomSXIhCQqXtwzrbXWXbVfl7Kr5dwLQizSZCV6BBZBRj6fgjTaHhHxDlnx1S8MfxRyUQYa6tHcVFAADqxhPuQKD")&&checkNet(getActivity()))
                {
                    startActivity(new Intent(getActivity(),Splashscreen.class));
                }
                    if(!checkNet(getActivity())){
                    Toast.makeText(getActivity(),"Please check your Internet Connection!", Toast.LENGTH_LONG).show();
                    rootView.findViewById(R.id.imgcl).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.meme).setVisibility(View.VISIBLE);
                    webView.loadData("<html><body bgcolor=\"white\"></body></html>","text/html; charset=UTF-8",null);
                }
                else{
                    rootView.findViewById(R.id.imgcl).setVisibility(View.GONE);
                        rootView.findViewById(R.id.meme).setVisibility(View.GONE);
                    Toast.makeText(getActivity(),"Loading Feed...", Toast.LENGTH_SHORT).show();
                    webView.loadUrl(base);
                    request.executeAsync();

                }
            }
        });

        TextView aa = (TextView) rootView.findViewById(R.id.loader);
        aa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(checkNet(getActivity())){
                    if(n<101&&check==0){
                        n+=7;
                    Toast.makeText(getActivity(),"Loading...", Toast.LENGTH_SHORT).show();
                    request.executeAsync();
                        rootView.findViewById(R.id.loader).setVisibility(View.GONE);}
                    else Toast.makeText(getActivity(),"That's all :(", Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(getActivity(),"Check Your Connection!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }


    //End of On create View
    public void insertView(String a, String your_url, final String link, LayoutInflater inflater, ViewGroup container){
        v = inflater.inflate(R.layout.cards, container, false);
        try{
            View tv = v.findViewById(R.id.first);
            View pl = v.findViewById(R.id.second);
            ((TextView) tv).setText(a);
            new DownloadImageTask((ImageView) pl).execute(your_url);
            pl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    open_facBook(link);

                }
            });
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    open_facBook(link);

                }
            });
            //((ImageView) pl).setImageResource(R.drawable.logottt);
            v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            xyz.addView(v);

        }
        catch(Exception e)
        {}
    }

    public void insertViewA(String a, final String link, LayoutInflater inflater, ViewGroup container){
        v = inflater.inflate(R.layout.cards_a, container, false);
        try{

            View tv = v.findViewById(R.id.first);
            ((TextView) tv).setText(a);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    open_facBook(link);

                }
            });
            v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            xyz.addView(v);
        }
        catch(Exception e)
        {}
    }

    public void open_facBook(String link1){

        final String link=link1;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Do you want to open this post on Facebook?");
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

   /* public void insertViewB(String your_url,  LayoutInflater inflater,ViewGroup container){
        v = inflater.inflate(R.layout.cards_b, container, false);
        try{
            View pl = v.findViewById(R.id.second);
            new DownloadImageTask((ImageView) pl).execute(your_url);
            pl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + link));
                        intent.setPackage("com.facebook.katana");
                        startActivity(intent);
                    } catch (Exception e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                    }

                }
            });
            //((ImageView) pl).setImageResource(R.drawable.logottt);
            v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            xyz.addView(v);
        }
    catch(Exception e)
    {}
    }*/

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {

            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            bmImage.setImageBitmap(result);
            if(i==n){
                rootView.findViewById(R.id.loader).setVisibility(View.VISIBLE);
            }
        }
    }
    @Override
    public void onPause() {
        super.onPause();

    }
    public boolean checkNet(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void play(String url){
        String test1=url.substring(0,24);
        if(url.equals("https://play.google.com/store/apps/details?id=com.thethinktankmit.thethinktank")){
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
    else if(test1.equals("https://www.facebook.com")){
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + url));
                intent.setPackage("com.facebook.katana");
                startActivity(intent);
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
    }
    else{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }

    }
}


