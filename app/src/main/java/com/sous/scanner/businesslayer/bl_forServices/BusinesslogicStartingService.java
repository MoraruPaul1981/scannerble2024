package com.sous.scanner.businesslayer.bl_forServices;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.scanner.datasync.businesslayer.Services.DataSyncService;
import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.businesslayer.Services.AdvertiseCallbackService;
import com.sous.scanner.businesslayer.Services.ServiceClientsScanBackground;
import com.sous.scanner.businesslayer.bl_fragmentbootscanner.BinesslogicFragBootScanner;

import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class BusinesslogicStartingService {

Context context;
long version;

    public  @Inject BusinesslogicStartingService(@ApplicationContext Context hiltcontext) {
        this.context = hiltcontext;
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }






    public void startingServiceAdvertising( ) {
        try {
            // TODO: 23.07.2024 starting
            Intent ServiceAdvertisingService= new Intent(context, AdvertiseCallbackService.class);
            // TODO: 15.08.2024
            ServiceAdvertisingService.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            ServiceAdvertisingService.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            ServiceAdvertisingService.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            ServiceAdvertisingService.addFlags(Intent.FLAG_FROM_BACKGROUND);
            ServiceAdvertisingService.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            // TODO: 08.08.2024
            ContextCompat.startForegroundService(context,ServiceAdvertisingService);

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
















    // TODO: 29.11.2022 служба сканирования
    @SuppressLint("RestrictedApi")
    public void startingServiceSimpleScan(@NonNull String MacAdresss) {
        try {
            // TODO: 19.08.2024
                        Intent intentClientServiceSimpleScanStart = new Intent(context, ServiceClientsScanBackground.class);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_FROM_BACKGROUND);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        intentClientServiceSimpleScanStart.setAction("com.ping.with.gatt.server");
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                            // TODO: 30.08.2024
                            Bundle bundleMac=new Bundle();
                          bundleMac.putString("MacAdresss",MacAdresss);

                        intentClientServiceSimpleScanStart.putExtras(bundleMac);
                        // TODO: 24.07.2024
                      ContextCompat.startForegroundService(context,intentClientServiceSimpleScanStart);
                        // TODO: 19.08.2024
                        // TODO: 26.07.2024
                        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );



                // TODO: 26.07.2024
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );



            // TODO: 26.07.2024
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
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


    public void startingServicedataSync(@NonNull  Context context,  @NonNull  Long version ) {
        try{
            // TODO: 21.08.2024
            Intent intentDataSyncService = new Intent(context, DataSyncService.class);
            intentDataSyncService.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intentDataSyncService.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intentDataSyncService.addFlags(Intent.FLAG_FROM_BACKGROUND);
            intentDataSyncService.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            intentDataSyncService.setAction("stateDataSync");
            intentDataSyncService.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            // TODO: 08.08.2024
            ContextCompat.startForegroundService(context,intentDataSyncService);

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
            new com.scanner.datasync.businesslayer.Errors.SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }

    }

    public void bindingServicedataSync(@NonNull  Context context,  @NonNull  Long version ) {
        try{
            // TODO: 21.08.2024
            Intent intentDataSyncService = new Intent(context, DataSyncService.class);
            intentDataSyncService.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intentDataSyncService.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intentDataSyncService.addFlags(Intent.FLAG_FROM_BACKGROUND);
            intentDataSyncService.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            intentDataSyncService.setAction("stateDataSync");
            intentDataSyncService.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            // TODO: 08.08.2024
            ServiceConnection serviceConnectionDatStnc=    new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    // TODO: 26.07.2024
                    if (iBinder.isBinderAlive()) {
                        // TODO: 28.07.2023  Update
                        DataSyncService.LocalBinderКлиентBLE     localBinderКлиентBLE = (DataSyncService.LocalBinderКлиентBLE) iBinder;
                        // TODO: 03.09.2024

                        //TODO: 03.09.2024 Запускаем синхронизацию с сервером JBOSS
                        localBinderКлиентBLE.getService().gettingDataForBluetoohtClient();

                        // TODO: 03.09.2024 После сихрониазции запускаем Фрашмент самого Сканера
                        startingFragmentAflerAsyncData();

                    }

                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
                }



                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    // TODO: 26.07.2024
                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
                }
            };
            // TODO: 19.08.2024
            // TODO: 23.10.2024 starting
            context.bindService(intentDataSyncService,Context.BIND_AUTO_CREATE, Executors.newSingleThreadExecutor(),serviceConnectionDatStnc  );

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
            new com.scanner.datasync.businesslayer.Errors.SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }

    }

    public void startingFragmentAflerAsyncData() {
        // TODO: 01.11.2024
        try{
        context.getMainExecutor().execute(()->{
            // TODO: 01.11.2024  запкскаем фрагмент
            new BinesslogicFragBootScanner(context).    statyingCallBAckFragmentScaner();

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );

        });

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
        new com.scanner.datasync.businesslayer.Errors.SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }
    }


    // TODO: 24.07.2024  end class

}
