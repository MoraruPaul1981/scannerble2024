package com.dsy.dsu.WorkManagers.BL_WorkMangers;

import android.content.Context;
import android.util.Log;

import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.multiprocess.RemoteWorkManager;

import com.dsy.dsu.Errors.Class_Generation_Errors;
import com.dsy.dsu.WorkManagers.MyWork_Async_Public;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CreatePublicWorkManager {
    Context context;
    private    String ИмяСлужбыСинхронизации="WorkManager Synchronizasiy_Data";
    public CreatePublicWorkManager(Context context) {
        this.context =context;
    }
    // TODO: 08.10.2023 вызов из БроадКста для Синхрониазции ТОлько


    public void getcreatePublicWorkManager(@NotNull Context context,
                                           @NotNull Integer PublicId) {

        try{
            Data myDataДляОбщейСинхрониазации = new Data.Builder()
                    .putInt("КтоЗапустилWorkManagerДляСинхронизации", PublicId)
                    .build();
            Constraints constraintsСинхронизация= new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(false)
                    .setRequiresStorageNotLow(false)
                    .build();
            PeriodicWorkRequest   periodicWorkRequestСинхронизация = new PeriodicWorkRequest.Builder(MyWork_Async_Public.class,
                    PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)//MIN_PERIODIC_FLEX_MILLIS////
                    // PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
                    .addTag(ИмяСлужбыСинхронизации)
                    .setInputData(myDataДляОбщейСинхрониазации)
                    .setConstraints(constraintsСинхронизация)
                    .setBackoffCriteria(
                            BackoffPolicy.LINEAR,
                            PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                            TimeUnit.MILLISECONDS)
                    .build();


            List<WorkInfo> workInfo = WorkManager.getInstance(context).getWorkInfosByTag(ИмяСлужбыСинхронизации).get();

            if (workInfo.size()>0) {

                // TODO: 14.08.2024
                switch ( workInfo.get(0).getState())   {

                    case RUNNING,BLOCKED,ENQUEUED ,CANCELLED,SUCCEEDED,FAILED -> {
                        // TODO: 26.07.2024
                        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " workInfo.size() " +workInfo.size());
                    }

                    default -> {
                        // TODO: 14.08.2024
                        WorkManager.getInstance(context ).enqueueUniquePeriodicWork(ИмяСлужбыСинхронизации,
                                ExistingPeriodicWorkPolicy.UPDATE, periodicWorkRequestСинхронизация);
                        // TODO: 26.07.2024
                        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ "workInfo.size() " +workInfo.size());
                    }
                }

                // TODO: 14.08.2024

            }else {
// TODO: 14.08.2024
                WorkManager.getInstance(context).enqueueUniquePeriodicWork(ИмяСлужбыСинхронизации,
                        ExistingPeriodicWorkPolicy.UPDATE, periodicWorkRequestСинхронизация);
                // TODO: 26.07.2024
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            }







                Log.d(context.getClass().getName(), "\n"
                        + " время: " + new Date()+"\n+" +
                        " Класс в процессе... " +  this.getClass().getName()+"\n"+
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                        " PublicId " +PublicId+ " callbackRunnable ");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(context.getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

}
