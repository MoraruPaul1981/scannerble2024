package com.sous.server.CONTROL;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.util.Log;

import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;


import com.sous.server.MODEL.SubClassErrors;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class BroadcastReceiverWorkManagerScannersServer extends BroadcastReceiver {
    private Long version=0l;
    public BroadcastReceiverWorkManagerScannersServer() {
        super();
        Log.i(this.getClass().getName(), " ЗАПУСК  BroadcastReceiverWorkManagerScannersServer " + new Date().toLocaleString());
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.getLongVersionCode();
            // TODO: 01.02.2023 запуск workmanager синхронизации
            МетодИнициализацийСинхронизацияДанныхWorkManager(context);
            // TODO: 01.02.2023   повтореый запуск службы Server Scanner
            МетодПовторныйЗапускДанныхWorkManager(context);
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

    private void МетодИнициализацийСинхронизацияДанныхWorkManager(Context context)
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
        PeriodicWorkRequest periodicWorkRequestСинхронизация = new PeriodicWorkRequest.Builder(MyWork_Async_Синхронизация_ScannerServer.class,
                PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)//MIN_PERIODIC_FLEX_MILLIS////  PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
                .addTag(ИмяСлужбыСинхронизации)
                .setInputData(myDataДляОбщейСинхрониазации)
                .setConstraints(constraintsСинхронизация)
                .build();



        Integer callbackRunnable = WorkManager.getInstance(context).getWorkInfosByTag(ИмяСлужбыСинхронизации).get().size();
        if (callbackRunnable == 0) {
            Log.w(context.getClass().getName(), " ПОСЛЕ ОТРАБОТКИ МЕТОДА ....Внутри BroadcastReceiverWorkManagerScannersServer  callbackRunnable.name() "
                    + callbackRunnable);
            WorkManager.getInstance(context.getApplicationContext()).enqueueUniquePeriodicWork(ИмяСлужбыСинхронизации,
                    ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequestСинхронизация);
        }
        Log.w(context.getClass().getName(), " ПОСЛЕ ОТРАБОТКИ BroadcastReceiverWorkManagerScannersServer ");
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
    private void МетодПовторныйЗапускДанныхWorkManager(Context context)
            throws ExecutionException, InterruptedException {
        try {
            String ИмяСлужбыСинхронизации = "WorkManager RetryServerScanners";
            Data myDataДляОбщейСинхрониазации = new Data.Builder()
                    .putInt("КтоЗапустилWorkManagerДляСинхронизации", 1)
                    .build();
            Constraints constraintsПовторныйЗапскСлужбыServerScanner = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(false)
                    .setRequiresStorageNotLow(false)
                    .build();
            PeriodicWorkRequest periodicWorkRequestconstraintsПовторныйЗапскСлужбыServerScanner
                    = new PeriodicWorkRequest.Builder(MyWork_Retry_ScannerServers.class,
                    14, TimeUnit.HOURS)//MIN_PERIODIC_FLEX_MILLIS////  PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
                    .addTag(ИмяСлужбыСинхронизации)
                    .setInputData(myDataДляОбщейСинхрониазации)
                    .setConstraints(constraintsПовторныйЗапскСлужбыServerScanner)
                    .build();

            Integer callbackRunnableПовторныйЗапуск = WorkManager.getInstance(context).getWorkInfosByTag(ИмяСлужбыСинхронизации).get().size();
            if (callbackRunnableПовторныйЗапуск == 0) {
                Log.w(context.getClass().getName(), " ПОСЛЕ ОТРАБОТКИ МЕТОДА ....Внутри BroadcastReceiverWorkManagerScannersServer  callbackRunnable.name() "
                        + callbackRunnableПовторныйЗапуск);
                WorkManager.getInstance(context.getApplicationContext()).enqueueUniquePeriodicWork(ИмяСлужбыСинхронизации,
                        ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequestconstraintsПовторныйЗапскСлужбыServerScanner);
            }
            Log.w(context.getClass().getName(), " ПОСЛЕ ОТРАБОТКИ BroadcastReceiverWorkManagerScannersServer ");
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