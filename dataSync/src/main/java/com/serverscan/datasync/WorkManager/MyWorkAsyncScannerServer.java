package com.serverscan.datasync.WorkManager;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


import com.serverscan.datasync.Errors.SubClassErrors;
import com.serverscan.datasync.datasync_businesslayer.bl_datasyncservice.BunissecclogicBindDataSyncService;
import com.serverscan.datasync.datasync_businesslayer.bl_network.WorkerStatusNewtorks;


import java.util.Date;
import java.util.List;

import javax.inject.Inject;


public class MyWorkAsyncScannerServer extends Worker {

    private Long version=0l;
   
    private  String ИмяСлужбыСинхронизации;




    // TODO: 28.09.2022
    public MyWorkAsyncScannerServer(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        try{
            // TODO: 14.08.2024
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.getLongVersionCode();

            Data data=     workerParams.getInputData();
            ИмяСлужбыСинхронизации=     data.getString("getname");
            // TODO: 03.09.2024

            
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


    @NonNull
    @Override
    public Result doWork() {
        try {
            // TODO: 14.08.2024
            List<WorkInfo> workInfo = WorkManager.getInstance(getApplicationContext()).getWorkInfosByTag(ИмяСлужбыСинхронизации).get();


            // TODO: 03.09.2024 запускаем синхрониазцию с ссервром Server GATT
            // TODO: 03.09.2024  запуск службы синхронизвции work mamanger
            WorkerStatusNewtorks workerStatusNewtorks=new WorkerStatusNewtorks(getApplicationContext(),version);
            Boolean StatusNewtwork= workerStatusNewtorks.getStatusNewtwork();
            if (StatusNewtwork==true) {
                // TODO: 06.09.2024
                //todo зпуск синхрониазции
                new BunissecclogicBindDataSyncService(getApplicationContext()).bindServiceDataSyncJboss(getApplicationContext(),version);

            }else{


                getApplicationContext().getMainExecutor().execute(()->{
                    Toast toast = Toast.makeText(getApplicationContext(), "Нет  сети !!! ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast.getYOffset() / 2);
                    toast.show();
                });
            }

            // TODO: 26.07.2024
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+"  doWork " +workInfo.get(0).getState()+"'\n" +
                    " Bremy " + new Date().toLocaleString()+" workInfo.size() "+workInfo.size() +
                    StatusNewtwork +" StatusNewtwork ");

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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return Result.success();
    }





    @Override
    public void onStopped() {
        super.onStopped();
        try{
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }



    // TODO: 14.08.2024 END
}












