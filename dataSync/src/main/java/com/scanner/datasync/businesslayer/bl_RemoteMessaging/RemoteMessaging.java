package com.scanner.datasync.businesslayer.bl_RemoteMessaging;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.scanner.datasync.businesslayer.Errors.SubClassErrors;
import com.scanner.datasync.businesslayer.Services.DataSyncService;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent.class)
public class RemoteMessaging   implements  RemoteMessaпуInterface{
    private SQLiteDatabase Create_Database_СамаБАзаSQLite;
    private Context context;
    private long version;

    public @Inject  RemoteMessaging(@ApplicationContext Context hiltcontext ) {
        // TODO: 22.08.2024
        // TODO: 21.08.2024
        context=hiltcontext;
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }


// TODO: 14.08.2024
      @Override
    public Integer startingRemoteMessaging(@NonNull  SQLiteDatabase Create_Database_СамаБАзаSQLite,  @NonNull  Long version ) {
        try{

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

        return Create_Database_СамаБАзаSQLite.getVersion();
    }



    @Override
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }

    }





}




