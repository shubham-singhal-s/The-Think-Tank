package com.thethinktankmit.thethinktank;

/**
 * Created by MAHE on 12/19/2016.
 */

import android.app.Application;
import android.content.Context;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(formUri = "http://www.bugsense.com/api/acra?api_key=1b983104")
public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // The following line triggers the initialization of ACRA
        ACRA.init(this);
    }
}
