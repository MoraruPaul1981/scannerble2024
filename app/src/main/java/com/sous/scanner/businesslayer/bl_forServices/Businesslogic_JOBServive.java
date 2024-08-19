package com.sous.scanner.businesslayer.bl_forServices;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.businesslayer.Services.ServiceClientsScanBackground;

import javax.inject.Inject;

public class Businesslogic_JOBServive {

Context context;
long version;

    public  @Inject Businesslogic_JOBServive(Context context) {
        this.context = context;
    }




    // TODO: 29.11.2022 служба сканирования
    public void startingServiceSimpleScan(String stateScartServiceScan, Message handlerScannerGattClient) {
        try {
            // TODO: 19.08.2024
            handlerScannerGattClient.getTarget().postDelayed(()->{
                AsyncTaskLoader asyncTaskLoader=new AsyncTaskLoader(context) {
                    @Nullable
                    @Override
                    public Object loadInBackground() {

                        // Intent intentClientServiceSimpleScanStart = new Intent(context, ServiceClientsScan.class);
                        Intent intentClientServiceSimpleScanStart = new Intent(context, ServiceClientsScanBackground.class);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_FROM_BACKGROUND);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        intentClientServiceSimpleScanStart.setAction(stateScartServiceScan);
                        intentClientServiceSimpleScanStart.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                        // TODO: 24.07.2024
                        context.startService( intentClientServiceSimpleScanStart);

                        return null;
                    }
                };
                asyncTaskLoader.forceLoad();
                asyncTaskLoader.loadInBackground();

                // TODO: 26.07.2024
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );

            },3000);


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
