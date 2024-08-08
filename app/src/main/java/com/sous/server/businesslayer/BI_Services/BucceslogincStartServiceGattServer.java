package com.sous.server.businesslayer.BI_Services;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.Services.ServiceServerScan;

public class BucceslogincStartServiceGattServer {

private Context context;
private  Long version;

    public BucceslogincStartServiceGattServer(Context context, Long version) {
        this.context = context;
        this.version = version;
    }


    public void startingServiceGattServer() {
        try {
            // TODO: 23.07.2024 starting
            Intent ServiceGattServerScan = new Intent(context, ServiceServerScan.class);
            ServiceGattServerScan.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            ServiceGattServerScan.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            ServiceGattServerScan.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            ServiceGattServerScan.addFlags(Intent.FLAG_FROM_BACKGROUND);
            ServiceGattServerScan.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            // TODO: 08.08.2024
            ContextCompat.startForegroundService(context,ServiceGattServerScan);

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


}
