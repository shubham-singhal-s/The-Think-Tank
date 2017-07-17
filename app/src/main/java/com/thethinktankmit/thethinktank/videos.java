package com.thethinktankmit.thethinktank;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by DAIO__SAMA on 08-12-2016.
 */
public class videos extends Fragment {
    ProgressDialog pd;
    LinearLayout xyz;
    int check=0;
    int n=5;
    int i=0;
    int j;

    static LayoutInflater inflaterA;
    static ViewGroup containerA;
    static String urlStringX;
    View v;
    View rootView;
    public static videos newInstance() {
        videos fragment = new videos();
        return fragment;
    }

    public videos() {
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Videos");
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        containerA=container;
        inflaterA=inflater;
        rootView = inflater.inflate(R.layout.videos, container, false);
        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipevid);
        xyz = (LinearLayout) rootView.findViewById(R.id.cardv);
        urlStringX="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=10&playlistId=UUm2_2oD4uXoHINRPrmJXzxw&fields=etag%2CeventId%2Citems%2Ckind%2CnextPageToken%2CpageInfo%2CprevPageToken%2CtokenPagination%2CvisitorId&key="+getString(R.string.utube_apiU_key);
        if(!checkNet1(getActivity())){
            Toast.makeText(getActivity(),"Please check your Internet Connection!", Toast.LENGTH_LONG).show();
            rootView.findViewById(R.id.vidrel).setVisibility(View.VISIBLE);
        }
        else{
            Toast.makeText(getActivity(),"Loading Feed...", Toast.LENGTH_LONG).show();
            rootView.findViewById(R.id.vidrel).setVisibility(View.GONE);
            new JsonTask().execute(urlStringX);
        }
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Insert your code here
                swipeLayout.setRefreshing(false);
                if(!checkNet1(getActivity())){
                    Toast.makeText(getActivity(),"Please check your Internet Connection!", Toast.LENGTH_LONG).show();
                    rootView.findViewById(R.id.vidrel).setVisibility(View.VISIBLE);
                }
                else{
                    rootView.findViewById(R.id.vidrel).setVisibility(View.GONE);
                    Toast.makeText(getActivity(),"Loading Feed...", Toast.LENGTH_LONG).show();
                    if(check==0)
                    new JsonTask().execute(urlStringX);
                }
            }
        });

        TextView aa = (TextView) rootView.findViewById(R.id.loadr2);
        aa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(checkNet1(getActivity())){
                    if(n<51&&n<j){
                        n+=5;
                        Toast.makeText(getActivity(),"Loading...", Toast.LENGTH_SHORT).show();
                        new JsonTask().execute(urlStringX);
                        rootView.findViewById(R.id.loadr2).setVisibility(View.GONE);}
                    else Toast.makeText(getActivity(),"That's all :(", Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(getActivity(),"Check Your Connection!", Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }

   /** @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            if(check==0){if(!checkNet1(getActivity())){
                Toast.makeText(getActivity(),"Please check your Internet Connection!",Toast.LENGTH_LONG).show();
                rootView.findViewById(R.id.vidrel).setVisibility(View.VISIBLE);
            }
            else{
                Toast.makeText(getActivity(),"Loading Feed...",Toast.LENGTH_LONG).show();
                rootView.findViewById(R.id.vidrel).setVisibility(View.GONE);
                new JsonTask().execute(urlStringX);
                check=1;
            }}

        }
    }*/
    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected String doInBackground(String... params) {

            check=1;
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    //Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                JSONObject json = new JSONObject(result);
                JSONArray arr= json.getJSONArray("items");
                j=arr.length();
                for (; i<arr.length()&&i<n;i++)
                {
                    JSONObject Now=arr.getJSONObject(i).getJSONObject("snippet");
                    String S="https://www.youtube.com/watch?v="+Now.getJSONObject("resourceId").getString("videoId");
                    insertView(Now.getString("title"),
                            Now.getJSONObject("thumbnails").getJSONObject("high").getString("url"),S,inflaterA,containerA);
                }
                //insertView(null,null,null,inflaterA,containerA);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void insertView(String a, String your_url, final String link, LayoutInflater inflater, ViewGroup container){
        v = inflater.inflate(R.layout.cards_videos, container, false);
        try{

            View tv = v.findViewById(R.id.first);
            View pl = v.findViewById(R.id.second);
            ((TextView) tv).setText(a);
            new videos.DownloadImageTask((ImageView) pl).execute(your_url);
            pl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openYT(link);
                }
            });

            //((ImageView) pl).setImageResource(R.drawable.logottt);
            v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            xyz.addView(v);
        }
        catch(Exception e)
        {}
    }

    public void openYT(String link1){

        final String link=link1;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Do you want to open this video on Youtube?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setPackage("com.google.android.youtube");
                            intent.setData(Uri.parse(link));
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
            if(i==n)
                rootView.findViewById(R.id.loadr2).setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onPause() {
        super.onPause();

    }
    public boolean checkNet1(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
