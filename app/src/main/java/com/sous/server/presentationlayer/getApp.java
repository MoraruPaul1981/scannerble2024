package com.sous.server.presentationlayer;

import android.app.Application;
import android.util.Log;

import com.onesignal.OneSignal;
import com.sous.server.businesslayer.OndeSignal.InitOndeSignal;

import java.util.Date;

public class getApp  extends Application {
    private  static final String ONEKEY="204d790a-7bd5-43ce-948c-81a25803a761";

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONEKEY);

        OneSignal.promptForPushNotifications();

      /*  InitOndeSignal initOndeSignal=new InitOndeSignal(getApplicationContext(),version);
        initOndeSignal.starttinggetkeyOndeSignal("204d790a-7bd5-43ce-948c-81a25803a761");*/

        Log.i(this.getClass().getName(), "  "
                + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " время " + new Date().toLocaleString());

    }
}
