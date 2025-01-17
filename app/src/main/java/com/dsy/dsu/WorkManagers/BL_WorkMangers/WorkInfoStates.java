package com.dsy.dsu.WorkManagers.BL_WorkMangers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.work.Data;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.dsy.dsu.Errors.controller.RecordNewErros;

public class WorkInfoStates {

    private Context context;


    public WorkInfoStates(@NonNull Context context) {
        this.context = context;
    }
    WorkInfo.State state;
    public  void workInfoStatesWorkManager(@NonNull String NameWorkManger, @NonNull LifecycleOwner lifecycle) {
        try {
            WorkManager.getInstance(context).getWorkInfosByTagLiveData(NameWorkManger)
                    .observe( lifecycle, workStatus -> {
                        if (workStatus.get(0).getState()== WorkInfo.State.SUCCEEDED) {
                            // TODO: 01.04.2024  пришли данные от single
                      Data dataCalBackSingle=      workStatus.get(0).getOutputData();
                        String uri=    dataCalBackSingle.getString("uri");
                                // TODO: 01.04.2024

                            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                                    " dataCalBackSingle " +dataCalBackSingle);
                        }

                    });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                    Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }




    // TODO: 07.10.2023 single


}


