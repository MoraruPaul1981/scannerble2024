package com.sous.scanner.businesslayer.bl_forServices;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.businesslayer.Services.ServiceClientsScan;

import javax.inject.Inject;

public class Businesslogic_JOBServive {

Context context;
long version;

    public  @Inject Businesslogic_JOBServive(Context context) {
        this.context = context;
    }




    // TODO: 29.11.2022 служба сканирования
    public void startingServiceSimpleScan(String stateScartServiceScan) {
        try {

            Intent intentClientServiceSimpleScanStart = new Intent(context, ServiceClientsScan.class);
            intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_FROM_BACKGROUND);
            intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            intentClientServiceSimpleScanStart.setAction(stateScartServiceScan);
            intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            // TODO: 24.07.2024
            ContextCompat.startForegroundService(context,intentClientServiceSimpleScanStart);
            // TODO: 26.07.2024
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("НазваниеОбрабоатываемойТаблицы", "ErrorDSU1");
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
    public void stopServiceSimpleScan( ) {
        try {
            Intent intentClientServiceSimpleScanStop = new Intent(context, ServiceClientsScan.class);
            // TODO: 24.07.2024
            context.stopService( intentClientServiceSimpleScanStop);
            // TODO: 26.07.2024
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("НазваниеОбрабоатываемойТаблицы", "ErrorDSU1");
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
