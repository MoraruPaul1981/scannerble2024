package com.dsy.dsu.WorkManagers.BL_WorkMangers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.dsy.dsu.Errors.Class_Generation_Errors;

import java.util.List;

public class ListenableFutures {

    private Context context;

    WorkInfo.State state;
    public ListenableFutures(@NonNull Context context) {
        this.context = context;
    }

    @SuppressLint("SuspiciousIndentation")
    public     WorkInfo.State listenableFutureWorkManager(@NonNull String NameWorkManger) {
        try {
            if (!WorkManager.getInstance(context).getWorkInfosByTag(NameWorkManger).get().isEmpty()) {
                com.google.common.util.concurrent.ListenableFuture<List<WorkInfo>> listListenableFuture =
                        WorkManager.getInstance(context).getWorkInfosByTag(NameWorkManger);
               state = listListenableFuture.get().get(0).getState();
                    Log.d(this.getClass().getName(),"\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ "" +
                            "state2 " +state);


                return  state;

            }


            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                    Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return  state;
    }





    // TODO: 07.10.2023 single


}


