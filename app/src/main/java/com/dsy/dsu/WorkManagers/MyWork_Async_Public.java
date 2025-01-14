package com.dsy.dsu.WorkManagers;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.WorkInfo;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.dsy.dsu.BusinessLogicAll.GetConnectivityManagerAndroid;
import com.dsy.dsu.Errors.Class_Generation_Errors;
import com.dsy.dsu.Services.Service_For_Remote_Async_Binary;

import com.dsy.dsu.WorkManagers.BL_WorkMangers.FindRunnigServiceBeforeWorkManager;
import com.dsy.dsu.WorkManagers.BL_WorkMangers.ListenableFutures;

import java.util.Date;

public class MyWork_Async_Public extends Worker {
 /*   private String ИмяСлужбыWorkManger ="WorkManager Synchronizasiy_Data";*/
    private  String ИмяСлужбыSingleWorkManger ="WorkManager Synchronizasiy_Data Disposable";
    private ServiceConnection serviceConnectionWorkManager;
    private Service_For_Remote_Async_Binary.LocalBinderAsync localBinderAsyncWorkmanager;
    private SharedPreferences preferencesJboss;

    // TODO: 28.09.2022
    @SuppressLint("RestrictedApi")
    public MyWork_Async_Public(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        try{

            initSharedPreferences(context);


            // TODO: 22.12.2022
            МетодБиндингаОбщая();



            Log.d(this.getClass().getName(),"\n"
                    + " bremy: " + new Date()+"\n+"
                    + "  class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

        } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new   Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }

    private void initSharedPreferences(@NonNull Context context) {
        preferencesJboss = context.getSharedPreferences("sharedPreferencesХранилище", Context.MODE_MULTI_PROCESS);
    }


    @NonNull
    @Override
    public Result doWork() {
        Long     ФинальныйРезультатAsyncBackgroudPublic = 0l;
        Data data = null;
        try {
            // TODO: 01.04.2024  запускаем Listertable
            WorkInfo.State stateSingle = new ListenableFutures(getApplicationContext()).listenableFutureWorkManager(ИмяСлужбыSingleWorkManger);
            // TODO: 26.12.2021
            if ( stateSingle!= WorkInfo.State.RUNNING   ) {
                // TODO: 01.04.2024
           ФинальныйРезультатAsyncBackgroudPublic= МетодЗапускаОбщей();
            }
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " ФинальныйРезультатAsyncBackgroudPublic " +ФинальныйРезультатAsyncBackgroudPublic+
                     " stateSingle " +stateSingle);

            Data.Builder      myDataОтветОбщейСлужбы = new Data.Builder()
                    .putLong("ReturnWorklong", ФинальныйРезультатAsyncBackgroudPublic)
                  .putInt("ReturnWorkint", ФинальныйРезультатAsyncBackgroudPublic.intValue());

             data=   myDataОтветОбщейСлужбы.build();

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " ФинальныйРезультатAsyncBackgroudPublic " +ФинальныйРезультатAsyncBackgroudPublic);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
            Result.failure();
        }
     return      Result.success(data);
    }



    private void МетодБиндингаОбщая() throws InterruptedException {
        try {
            Intent intentГлавнаяСинхрониазция = new Intent(getApplicationContext(), Service_For_Remote_Async_Binary.class);
                serviceConnectionWorkManager = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        if (service.isBinderAlive()) {
                            localBinderAsyncWorkmanager = (Service_For_Remote_Async_Binary.LocalBinderAsync) service;
                            //TODO СИНХРОНИАЗЦИЯ ГЛАВНАЯ
                            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                    + "localBinderAsyncWorkmanager " + localBinderAsyncWorkmanager.isBinderAlive());
                        }
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        Log.d(getApplicationContext().getClass().getName().toString(), "\n"
                                + "onServiceConnected  ОБЩАЯ messengerActivity  ");
                    }
                };
            // TODO: 01.04.2024

                getApplicationContext().bindService(intentГлавнаяСинхрониазция, serviceConnectionWorkManager, Context.BIND_AUTO_CREATE);


            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new   Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
            Log.e(getApplicationContext().getClass().getName(), " ОШИБКА В WORK MANAGER  MyWork_AsyncSingle из FaceApp в  MyWork_AsyncSingle Exception  ошибка в классе  MyWork_AsyncSingle" + e.toString());
        }
    }

    @Override
    public void onStopped() {
        super.onStopped();
        try{
            Log.d(getApplicationContext().getClass().getName().toString(), "\n"
                    + "onStopped  onStopped");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());

        }
    }







    // TODO: 16.12.2021 МЕтод ЗАпуска  Сихрониазации Чисто В форне без актвтити
    @SuppressLint("SuspiciousIndentation")
    private Long    МетодЗапускаОбщей() {
        Long ФинальныйРезультатAsyncBackgroud=0l;
        try {
            // TODO: 22.12.2022  сама запуска синхронищации из workmanager ОБЩЕГО
            boolean ВыбранныйРежимСети =
                    new GetConnectivityManagerAndroid(getApplicationContext()).сonnectivityManageruserselection();

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " lВыбранныйРежимСети" + ВыбранныйРежимСети);


          //Boolean isMyActivityRunning=  new FindRunnigServiceBeforeWorkManager(getApplicationContext()).isGetMyActivityRunning( );
          Boolean isMyActivityRunning=  new FindRunnigServiceBeforeWorkManager(getApplicationContext()).isGetMyActivityRunning( );


            if (ВыбранныйРежимСети == true && localBinderAsyncWorkmanager!=null  && isMyActivityRunning==false) {

              ФинальныйРезультатAsyncBackgroud = localBinderAsyncWorkmanager.getService().metodStartingSync( getApplicationContext());

                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                        + " ФинальныйРезультатAsyncBackgroud "+ФинальныйРезультатAsyncBackgroud
                        + " localBinderAsyncWorkmanager.isBinderAlive( " + localBinderAsyncWorkmanager.isBinderAlive()+ "\n"+
                        "\n" + " isMyActivityRunning " +isMyActivityRunning+ "\n"+ " ВыбранныйРежимСети " +ВыбранныйРежимСети);
            }

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " ФинальныйРезультатAsyncBackgroud " + " localBinderAsyncWorkmanager.isBinderAlive( " + localBinderAsyncWorkmanager.isBinderAlive()+
                    "\n" + " isMyActivityRunning " +isMyActivityRunning);

       } catch (Exception e) {
           e.printStackTrace();
           Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                   " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
           new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                   Thread.currentThread().getStackTrace()[2].getMethodName(),
                   Thread.currentThread().getStackTrace()[2].getLineNumber());
       }
        return ФинальныйРезультатAsyncBackgroud;
    }


    // TODO: 03.10.2024 end class

}





























