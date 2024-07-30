package com.sous.scanner.presentationlayer;

import android.app.Application;
import android.util.Log;

import com.onesignal.OneSignal;

import java.util.Date;

public class getApp  extends Application {
    private  static final String ONEKEY="d94341b5-cc58-4531-aa39-1186de5f9948";

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
