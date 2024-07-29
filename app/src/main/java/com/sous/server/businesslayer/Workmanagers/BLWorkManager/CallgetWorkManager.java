package com.sous.server.businesslayer.Workmanagers.BLWorkManager;


import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;


import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.onesignal.OneSignal;
import com.sous.server.businesslayer.Firebases.MyFirebaseMessagingServiceServerScanners;
import com.sous.server.businesslayer.Workmanagers.MyWorkAsyncScannerServer;
import com.sous.server.businesslayer.Errors.SubClassErrors;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CallgetWorkManager   {

    Context context;
    Long version;

    public CallgetWorkManager(Context context, Long version) {
        this.context = context;
        this.version = version;
    }


    private void initWorkManager()
            throws ExecutionException, InterruptedException {
        try {
            String ИмяСлужбыСинхронизации = "WorkManager Synchronizasiy_DataScanners";
            Data myDataДляОбщейСинхрониазации = new Data.Builder()
                    .putInt("КтоЗапустилWorkManagerДляСинхронизации", 1)
                    .build();
            Constraints constraintsСинхронизация = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(false)
                    .setRequiresStorageNotLow(false)
                    .build();
            PeriodicWorkRequest periodicWorkRequestСинхронизация = new PeriodicWorkRequest.Builder(MyWorkAsyncScannerServer.class,
                    PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)//MIN_PERIODIC_FLEX_MILLIS////  PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
                    .addTag(ИмяСлужбыСинхронизации)
                    .setInputData(myDataДляОбщейСинхрониазации)
                    .setConstraints(constraintsСинхронизация)
                    .setBackoffCriteria(
                            BackoffPolicy.LINEAR,
                            PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                            TimeUnit.MILLISECONDS)
                    //    .setInputData(new Data.Builder().putString("КтоЗапустилWorkmanager","BroadCastRecieve").build())
                    .build();


            List<WorkInfo> workInfo = WorkManager.getInstance(context).getWorkInfosByTag(ИмяСлужбыСинхронизации).get();
            if (  workInfo.size()>=0){
                Log.w(context.getClass().getName(), " ПОСЛЕ ОТРАБОТКИ МЕТОДА ..." +
                        ".Внутри BroadcastReceiverWorkManagerScannersServer  callbackRunnable.name() "
                        + "  workInfo " +workInfo+  "   workInfo.hasObservers() " +  workInfo + "  workInfo.getState() " +workInfo.get(0).getState());
                WorkManager.getInstance(context.getApplicationContext()).enqueueUniquePeriodicWork(ИмяСлужбыСинхронизации,
                        ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequestСинхронизация);
            }
            Log.i(this.getClass().getName(),  "  " +Thread.currentThread().getStackTrace()[2].getMethodName()+ " время " +new Date().toLocaleString() );
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