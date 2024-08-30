package com.sous.scanner.businesslayer.bl_forServices;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.loader.content.AsyncTaskLoader;

import com.google.android.material.textview.MaterialTextView;
import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.businesslayer.Services.AdvertisingService;
import com.sous.scanner.businesslayer.Services.ServiceClientsScanBackground;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class Businesslogic_JOBServive {

Context context;
long version;

    public  @Inject Businesslogic_JOBServive(@ApplicationContext Context hiltcontext) {
        this.context = hiltcontext;
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }




    // TODO: 29.11.2022 служба сканирования
    @SuppressLint("RestrictedApi")
    public void startingServiceSimpleScan(@NonNull String stateScartServiceScan ) {
        try {
            // TODO: 19.08.2024
                        Intent intentClientServiceSimpleScanStart = new Intent(context, ServiceClientsScanBackground.class);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_FROM_BACKGROUND);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        intentClientServiceSimpleScanStart.setAction(stateScartServiceScan);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
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

    public void startingServiceAdvertising( ) {
        try {
            // TODO: 23.07.2024 starting
            Intent ServiceAdvertisingService= new Intent(context, AdvertisingService.class);
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
    public void startingServiceSimpleScan(@NonNull String stateScartServiceScan,
                                          @NonNull String MacAdresss) {
        try {
            // TODO: 19.08.2024
                        Intent intentClientServiceSimpleScanStart = new Intent(context, ServiceClientsScanBackground.class);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_FROM_BACKGROUND);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        intentClientServiceSimpleScanStart.setAction(stateScartServiceScan);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                            // TODO: 30.08.2024
                            Bundle bundle=new Bundle();
                            bundle.putString("MacAdresss",MacAdresss);
                        intentClientServiceSimpleScanStart.putExtras(bundle);
                        // TODO: 24.07.2024
                        context.startService( intentClientServiceSimpleScanStart);
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




    // TODO: 24.07.2024  end class

}
