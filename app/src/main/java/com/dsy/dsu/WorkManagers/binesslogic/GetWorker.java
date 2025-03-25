package com.dsy.dsu.WorkManagers.binesslogic;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.dsy.dsu.BootAndAsync.Service.IntentServiceBoot;
import com.dsy.dsu.BusinessLogicAll.GetConnectivityManagerAndroid;
import com.dsy.dsu.Errors.controller.RecordNewErros;

import org.jetbrains.annotations.NotNull;

public class GetWorker {


    Context context;

    public GetWorker(Context context) {
        this.context = context;
    }


     public void startingWorkMangerSingleOrPublicWorker(@NotNull  IntentServiceBoot.LocalBinderBootSerice          getlocalBinderBootSerice,
                                                        @NotNull String    ИмяСлужбыWorkManger  ) {
        try{
            Boolean isWorkManagerRunning=  new FindRunnigServiceBeforeWorkManager(context).isWorkManagerRunning(ИмяСлужбыWorkManger);

            // TODO: 22.12.2022  сама запуска синхронищации из workmanager ОБЩЕГО
            boolean ВыбранныйРежимСети =
                    new GetConnectivityManagerAndroid(context).сonnectivityManageruserselection();
            Intent intentSingleWorker=new Intent();

            if(getlocalBinderBootSerice!=null) {
                if (ВыбранныйРежимСети) {

                    if (getlocalBinderBootSerice.isBinderAlive() && isWorkManagerRunning == false) {
                        String actionSingleWorker = "IntentServiceBootAsync.com" ;

                        intentSingleWorker.setAction(actionSingleWorker);
                        intentSingleWorker.setData(Uri.parse(actionSingleWorker));

                        getlocalBinderBootSerice.getService().startingServiceBoot(intentSingleWorker, "BootService");

                    }
                } else {
// TODO: 18.03.2025
                    String exitSingleWorker = "ExitBootService";
                    intentSingleWorker.setAction(exitSingleWorker);
                    intentSingleWorker.setData(Uri.parse(exitSingleWorker));
                    getlocalBinderBootSerice.getService().startingServiceBoot(intentSingleWorker, "BootService");

                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                            + " isWorkManagerRunning " + isWorkManagerRunning);


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
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }













}