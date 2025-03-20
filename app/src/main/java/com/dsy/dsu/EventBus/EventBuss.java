package com.dsy.dsu.EventBus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dsy.dsu.BootAndAsync.DowloadUpdatePO.DownLoadPO;
import com.dsy.dsu.BootAndAsync.EventsBus.MessageEvensBusNetworkStatuses;
import com.dsy.dsu.BootAndAsync.EventsBus.MessageEvensBusUpdatePO;
import com.dsy.dsu.Errors.controller.RecordNewErros;
import com.dsy.dsu.Hilt.JbossAdrress.getHiltPortJbossInterface;
import com.dsy.dsu.Passwords.MainActivityPasswords;

import java.util.LinkedHashMap;

import javax.net.ssl.SSLSocketFactory;

import dagger.hilt.EntryPoints;

public class EventBuss   {

Activity  activity;

    Context context;


    SSLSocketFactory getsslSocketFactory2;

      LinkedHashMap<Integer,String> getHiltPortJboss;


    public   EventBuss(@NonNull Activity activity, @NonNull Context context, @NonNull SSLSocketFactory getsslSocketFactory2) {
        this.activity = activity;
        this.context = context;
        this.getsslSocketFactory2 = getsslSocketFactory2;
        getHiltPortJboss=   EntryPoints.get(context, getHiltPortJbossInterface.class).getHiltPortJboss();
    }




    

    private void методПереходНаActivityPassword() {
        try {
            Intent Интент_ЗапускаетFaceApp=new Intent();
            Интент_ЗапускаетFaceApp.setClass(context, MainActivityPasswords.class);
            Интент_ЗапускаетFaceApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Интент_ЗапускаетFaceApp.setAction("MainActivityPasswords.class");
            activity.startActivity(Интент_ЗапускаетFaceApp);//tso
            activity. finishAfterTransition();

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }



}
