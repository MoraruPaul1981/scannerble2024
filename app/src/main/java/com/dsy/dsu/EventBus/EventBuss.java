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




    





}
