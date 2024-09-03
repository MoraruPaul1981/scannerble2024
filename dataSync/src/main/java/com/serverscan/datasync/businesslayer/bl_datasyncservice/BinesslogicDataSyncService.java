package com.serverscan.datasync.businesslayer.bl_datasyncservice;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.serverscan.datasync.businesslayer.Errors.SubClassErrors;
import com.serverscan.datasync.businesslayer.Services.DataSyncService;

public class BinesslogicDataSyncService  implements  InterfaceDataSyncService {

  public    Intent intentDataSyncService;
    public  Context context;
    public   Long version;
    public BinesslogicDataSyncService(@NonNull Context hiltcontext, @NonNull Long hilversion) {
        // TODO: 03.09.2024
        context=hiltcontext;
        version=hilversion;
        // TODO: 24.07.2024
    }


    @Override
    public void startingDataSyncService(@NonNull String stateScartServiceScan) {
        try{
            // TODO: 19.08.2024
            intentDataSyncService = new Intent(context, DataSyncService.class);
            intentDataSyncService.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intentDataSyncService.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intentDataSyncService.addFlags(Intent.FLAG_FROM_BACKGROUND);
            intentDataSyncService.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            intentDataSyncService.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
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
}
