package com.sous.scanner.businesslayer.bl_fragnment_gatt_client;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

public class BusinessloginVibrator {

    Context context;

    public BusinessloginVibrator(Context context) {
        this.context = context;
    }

   public void alarmVibrator(){

        Vibrator v2 = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v2.vibrate(VibrationEffect.createOneShot(110, VibrationEffect.DEFAULT_AMPLITUDE));


        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

    }


}
