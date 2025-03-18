package com.dsy.dsu.WorkManagers;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.dsy.dsu.BootAndAsync.Service.IntentServiceBoot;
import com.dsy.dsu.BusinessLogicAll.GetConnectivityManagerAndroid;
import com.dsy.dsu.Errors.controller.RecordNewErros;
import com.dsy.dsu.Services.ServiceUpdatePoОбновлениеПО;
import com.dsy.dsu.Services.Service_For_Remote_Async_Binary;
import com.dsy.dsu.WorkManagers.BL_WorkMangers.FindRunnigServiceBeforeWorkManager;
import com.dsy.dsu.WorkManagers.BL_WorkMangers.ListenableFutures;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@SuppressLint("RestrictedApi")
public class MyWork_AsyncSingle extends Worker {
    protected String ИмяСлужбыWorkManger ="WorkManager Synchronizasiy_Data";
    protected  String ИмяСлужбыSingleWorkManger ="WorkManager Synchronizasiy_Data Disposable";

    IntentServiceBoot.LocalBinderBootSerice          getlocalBinderBootSerice;

    public MyWork_AsyncSingle(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        try{
            getLiveBindibngServiceBoot() ;
            // TODO: 02.04.2024 Bl
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

        } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
        Log.e(context.getClass().getName(), " ОШИБКА В WORK MANAGER  MyWork_AsyncSingle из FaceApp в  MyWork_AsyncSingle Exception  ошибка в классе  MyWork_AsyncSingle" + e.toString());
    }
    }




    @Override
    public void onStopped() {
        super.onStopped();
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }


    @NonNull
    @Override
    public Result doWork() {
        // TODO: 24.09.2024
        try {


            startingWorkMangerSingle();

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + " getlocalBinderBootSerice " +getlocalBinderBootSerice );

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }


        // TODO: 20.09.2022
        return Result.success();
        
    }

    private void startingWorkMangerSingle() {
        try{
        Boolean isWorkManagerRunning=  new FindRunnigServiceBeforeWorkManager(getApplicationContext()).isWorkManagerRunning(ИмяСлужбыWorkManger);

        // TODO: 22.12.2022  сама запуска синхронищации из workmanager ОБЩЕГО
        boolean ВыбранныйРежимСети =
                new GetConnectivityManagerAndroid(getApplicationContext()).сonnectivityManageruserselection();

        if(getlocalBinderBootSerice!=null){
            if(getlocalBinderBootSerice.isBinderAlive() && ВыбранныйРежимСети && isWorkManagerRunning==false){
              String  actionSingleWorker=  "IntentServiceBootUpdatePo.comAndIntentServiceBootAsync.com";
              Intent  intentSingleWorker=new Intent();
                intentSingleWorker.setAction(actionSingleWorker);
                intentSingleWorker.setData( Uri.parse(actionSingleWorker));

                getlocalBinderBootSerice.getService().startingServiceBoot(intentSingleWorker);

            }
        }
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " isWorkManagerRunning " +isWorkManagerRunning );

        } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

}


    public void getLiveBindibngServiceBoot() {
        try{
            Intent intentstartServiceOneSignal=new Intent(getApplicationContext(), IntentServiceBoot.class);
            // TODO: 24.01.2024
            getApplicationContext().bindService(intentstartServiceOneSignal, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    if (service.isBinderAlive()) {
                         getlocalBinderBootSerice = (IntentServiceBoot.LocalBinderBootSerice) service;
                        // TODO: 03.03.2025
                        // TODO: 03.03.2025


                        Log.d(getApplicationContext().getClass().getName(), "\n"
                                + " время: " + new Date() + "\n+" +
                                " Класс в процессе... " + this.getClass().getName() + "\n" +
                                " onServiceConnected  метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                "  + getlocalBinderBootService.isBinderAlive()"+
                                getlocalBinderBootSerice.isBinderAlive());

                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    Log.d(getApplicationContext().getClass().getName(), "\n"
                            + " время: " + new Date() + "\n+" +
                            " Класс в процессе... " + this.getClass().getName() + "\n" +
                            " onServiceConnected  метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
                }
            }, Context.BIND_AUTO_CREATE);

            Log.d(getApplicationContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(getApplicationContext().getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext().getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }














    // TODO: 02.04.2024  end main work namager

}





























