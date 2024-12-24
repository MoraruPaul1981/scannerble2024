package com.dsy.dsu.WorkManagers.BL_WorkMangers;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.dsy.dsu.BusinessLogicAll.SubClass_Connection_BroadcastReceiver_Sous_Asyns_Glassfish;
import com.dsy.dsu.Errors.Class_Generation_Errors;
import com.dsy.dsu.WorkManagers.MyWork_AsyncSingle;
import com.dsy.dsu.WorkManagers.MyWork_Async_Public;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CreateSingleWorkManager {
    Context context;
    private  String ИмяСлужбыSingleWorkManger ="WorkManager Synchronizasiy_Data Disposable";
    public CreateSingleWorkManager(Context context) {
        this.context =context;
    }
    
    public void getcreateSingleWorkManager(@NotNull Context context,
                                           @NotNull Uri uri) {

        try{
            // TODO: 08.10.2023
            Integer PublicId = new SubClass_Connection_BroadcastReceiver_Sous_Asyns_Glassfish()
                    .МетодПолучениеяПубличногоID(context);

            Data myDataSingleWorker = new Data.Builder()
                    .putInt("ПубличныйID", PublicId)
                    .putBoolean("StartSingleWorker", true)
                    .putString("uri",   uri.toString())
                    .build();

            Constraints constraintsЗапускСинхОдноразоваяСлужба = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(false)
                    .setRequiresStorageNotLow(false)
                    .build();
            OneTimeWorkRequest oneTimeWorkRequest =
                    new OneTimeWorkRequest.Builder(MyWork_AsyncSingle.class)
                            .setConstraints(constraintsЗапускСинхОдноразоваяСлужба)
                            .setInputData(myDataSingleWorker)
                            .addTag(ИмяСлужбыSingleWorkManger)
                            .build();//      .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)

            WorkManager.getInstance(context).enqueueUniqueWork(ИмяСлужбыSingleWorkManger,
                    ExistingWorkPolicy.KEEP , oneTimeWorkRequest);


            Log.d(this.getClass().getName(),"\n"
                    + " bremy: " + new Date()+"\n+"
                    + "  class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(context.getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    // TODO: 08.10.2023
}
