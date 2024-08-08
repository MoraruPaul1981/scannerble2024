package com.sous.scanner.businesslayer.bl_forActivityScan;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

import com.onesignal.BuildConfig;
import com.onesignal.OneSignal;

public class BussenslogicActifityScan {


Context context;
long version;
    final String ONEKEY="2a1819db-60c8-4ca3-a752-1b6cd9cadfa1";
    public BussenslogicActifityScan(Context context, long version) {
        this.context = context;
        this.version = version;
    }

    public void initOneSignal() {
        OneSignal.initWithContext(context);
        OneSignal.setAppId(ONEKEY);
        OneSignal.promptForPushNotifications();
        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );

    }


}
