package com.dsy.dsu.BootAndAsync.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dsy.dsu.BootAndAsync.BlBootAsync.Hilts.ServiceBootBinessLogic;
import com.dsy.dsu.BootAndAsync.Service.IntentServiceBoot;
import com.dsy.dsu.Errors.controller.RecordNewErros;

import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.HiltAndroidApp;
import dagger.hilt.android.lifecycle.HiltViewModel;


@HiltViewModel
public class ViewModelBoot extends AndroidViewModel {

    private  Context context;
    private MutableLiveData<IntentServiceBoot.LocalBinderBootSerice> mutableLiveData;


    public  @Inject  ViewModelBoot(@NonNull Application application) {
        super(application);
        // TODO: 03.03.2025
        context=getApplication().getApplicationContext();
        // TODO: 03.03.2025
        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());

    }




    public LiveData<IntentServiceBoot.LocalBinderBootSerice> getMutableLiveData() {
        try{
            mutableLiveData = new MutableLiveData<IntentServiceBoot.LocalBinderBootSerice>();
            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(context.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplication().getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return mutableLiveData;
    }






    public void getLiveBindibngServiceBoot() {
        try{
            Intent intentstartServiceOneSignal=new Intent(context, IntentServiceBoot.class);
            // TODO: 24.01.2024
            context.bindService(intentstartServiceOneSignal, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    if (service.isBinderAlive()) {
                        IntentServiceBoot.LocalBinderBootSerice          getlocalBinderBootSerice = (IntentServiceBoot.LocalBinderBootSerice) service;
                        // TODO: 03.03.2025
                        // TODO: 03.03.2025
                        mutableLiveData.postValue(getlocalBinderBootSerice);

                        Log.d(context.getClass().getName(), "\n"
                                + " время: " + new Date() + "\n+" +
                                " Класс в процессе... " + this.getClass().getName() + "\n" +
                                " onServiceConnected  метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                "  + getlocalBinderBootService.isBinderAlive()"+
                                service.isBinderAlive());

                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    Log.d(context.getClass().getName(), "\n"
                            + " время: " + new Date() + "\n+" +
                            " Класс в процессе... " + this.getClass().getName() + "\n" +
                            " onServiceConnected  метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
                }
            }, Context.BIND_AUTO_CREATE);

        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(context.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(getApplication().getApplicationContext()).recordnewerror(e.toString(),
                this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

    }






}
