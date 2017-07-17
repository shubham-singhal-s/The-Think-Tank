package com.thethinktankmit.thethinktank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Splashscreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2900;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }

        ImageView imageView = (ImageView) findViewById(R.id.imgLogo);
        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.anima);
        imageView.startAnimation(pulse);





        String version="";
        try{
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;}
        catch (Exception e){}

        DLoad task = new DLoad(this, version);
        task.execute();


        }


    }



class DLoad extends AsyncTask<String, Void, String> {
    String[] txt = {"", "", ""};
    SharedPreferences sharedpreferences;

    private Context mContext;
    String vers;

    public DLoad (Context context, String str){
        mContext = context;
        vers=str;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            // Create a URL for the desired page
            URL url = new URL("http://tedxmanipaluniversity.com/app/check_v");

            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            int i=0;

            while ((str = in.readLine()) != null) {
                txt[i]=str;
                i++;
            }
            in.close();
        } catch (Exception e) {

        }

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        String check = "yes";

        editor.putString("vers_txt", txt[0]);
        if(!txt[2].equals("")) {
            editor.putString("token", txt[2]);

        }
        else{
            check="no";
            editor.putString("token", "EAAYfayAh8xIBAIdlDqMQYlTgV1JSa6LzLa1JXD3eXh9oxFwvtCd1ZCFrDU8hhWqX040ByMy1syomSXIhCQqXtwzrbXWXbVfl7Kr5dwLQizSZCV6BBZBRj6fgjTaHhHxDlnx1S8MfxRyUQYa6tHcVFAADqxhPuQKD");
        }
        editor.putString("vers_app", vers);
        editor.commit();

        return check;
    }

    @Override
    protected void onPostExecute(String result) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(mContext, MainActivity.class);
                mContext.startActivity(i);

                // close this activity
                ((Activity)mContext).finish();
            }
        }, 1500);



    }

}
