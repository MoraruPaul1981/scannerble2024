package com.dsy.dsu.BootAndAsync.ViewModelBoot.Model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.dsy.dsu.BootAndAsync.Service.IntentServiceBoot;
import com.dsy.dsu.Errors.controller.RecordNewErros;
import com.dsy.dsu.WorkManagers.BL_WorkMangers.CreateSingleWorkManager;

import java.util.Date;

public class ModelBootService {

    Context context;

    public ModelBootService(Context context) {
        this.context = context;
    }



    public void startingSingleWorkManger() {
        try {
            // TODO: 03.03.2025 Запускем Синхрнонизацию через Singlw Work manager
            new CreateSingleWorkManager(context).getcreateSingleWorkManager( "BootService");

            Log.d(context.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }



}
