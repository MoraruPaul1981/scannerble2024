package com.serverscan.datasync.datasync_businesslayer.bl_workmangers;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.multiprocess.RemoteWorkManager;

import com.serverscan.datasync.datasync_businesslayer.Errors.SubClassErrors;
import com.serverscan.datasync.datasync_businesslayer.WorkManager.MyWorkAsyncScannerServer;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class InitWorkManager {


    Context context;
    Long version;

    public InitWorkManager(Context context, Long version) {
        this.context = context;
        this.version = version;
    }


    public void initWorkManager()
            throws ExecutionException, InterruptedException {
        try {
            String ИмяСлужбыСинхронизации = "workmanagerdataasync";
            Data myDataДляОбщейСинхрониазации = new Data.Builder()
                    .putInt("КтоЗапустилWorkManagerДляСинхронизации", 1)
                    .putString("getname", ИмяСлужбыСинхронизации)
                    .build();
            Constraints constraintsСинхронизация = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(false)
                    .setRequiresStorageNotLow(false)
                    .build();
            PeriodicWorkRequest periodicWorkRequestСинхронизация = new PeriodicWorkRequest.Builder(MyWorkAsyncScannerServer.class,
                    PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)//MIN_PERIODIC_FLEX_MILLIS////
                    // PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
                    .addTag(ИмяСлужбыСинхронизации)
                    .setInputData(myDataДляОбщейСинхрониазации)
                    .setConstraints(constraintsСинхронизация)
                    .setBackoffCriteria(
                            BackoffPolicy.LINEAR,
                            PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                            TimeUnit.MILLISECONDS)
                    .build();


           List<WorkInfo> workInfo = WorkManager.getInstance(context).getWorkInfosByTag(ИмяСлужбыСинхронизации).get();

            if (workInfo.size()>0) {
                // TODO: 14.08.2024
                switch ( workInfo.get(0).getState())   {
          
                    case RUNNING,BLOCKED,ENQUEUED ,CANCELLED,SUCCEEDED,FAILED -> {
                        // TODO: 26.07.2024
                        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                    }
          
                    default -> {
                        // TODO: 14.08.2024  
                        RemoteWorkManager.getInstance(context.getApplicationContext()).enqueueUniquePeriodicWork(ИмяСлужбыСинхронизации,
                                ExistingPeriodicWorkPolicy.UPDATE, periodicWorkRequestСинхронизация);
                        // TODO: 26.07.2024
                        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                    }
                }

                // TODO: 14.08.2024
                WorkManager.getInstance(context).getWorkInfosByTag(ИмяСлужбыСинхронизации).addListener(()->{
                    // TODO: 26.07.2024
                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+"  doWork "
                            +workInfo.get(0).getState()+"'\n" +
                            " Bremy " + new Date().toLocaleString());

                }, Executors.newSingleThreadExecutor());


                // TODO: 14.08.2024


            }else {
// TODO: 14.08.2024  
                RemoteWorkManager.getInstance(context.getApplicationContext()).enqueueUniquePeriodicWork(ИмяСлужбыСинхронизации,
                        ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequestСинхронизация);
                // TODO: 26.07.2024
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");  
            }


            // TODO: 26.07.2024
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }



}
