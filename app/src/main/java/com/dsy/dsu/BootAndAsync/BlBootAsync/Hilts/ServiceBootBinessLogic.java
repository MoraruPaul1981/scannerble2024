package com.dsy.dsu.BootAndAsync.BlBootAsync.Hilts;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.dsy.dsu.BootAndAsync.Service.IntentServiceBoot;
import com.dsy.dsu.Errors.controller.RecordNewErros;
import com.dsy.dsu.Services.Service_for_AdminissionMaterial;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;


@QualifierEventAsyncOrUpdatePOUsers
@Module
@InstallIn(SingletonComponent.class)
@Named("startingEventAsyncOrUpdatePOUsers")
public class ServiceBootBinessLogic {

    private Context context;

    public  @Inject ServiceBootBinessLogic(@ApplicationContext  Context context ) {
        this.context = context;
    }


    public void startServiceBootAndAsync( String WorkerStatus){
        try{

                // TODO: 01.04.2024
                Intent intentstartServiceOneSignal=new Intent(context, IntentServiceBoot.class);
                intentstartServiceOneSignal.setAction(WorkerStatus);
                intentstartServiceOneSignal.setData( Uri.parse(WorkerStatus));

              intentstartServiceOneSignal.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intentstartServiceOneSignal.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intentstartServiceOneSignal.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

            ContextCompat.startForegroundService(context, intentstartServiceOneSignal);

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    public void stopServiceBootAndAsync(@NonNull Activity activity){

        try{

            Intent intentstartServiceOneSignal=new Intent(context, IntentServiceBoot.class);
            intentstartServiceOneSignal.setAction("IntentServiceBootAsync.com");
            activity.stopService(intentstartServiceOneSignal);
            // TODO: 24.01.2024
            activity.finishAffinity();


            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    public Boolean bindingServiceBootAndAsync(@NonNull Activity activity){
        Boolean bindingServiceBoot = null;
        try{
            Intent intentstartServiceOneSignal=new Intent(context, IntentServiceBoot.class);
            // TODO: 24.01.2024
            bindingServiceBoot=   activity.bindService(intentstartServiceOneSignal, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    if (service.isBinderAlive()) {
                      IntentServiceBoot.LocalBinderBootSerice getlocalBinderBootSerice = (IntentServiceBoot.LocalBinderBootSerice) service;
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
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber()+" bindingServiceBoot " +bindingServiceBoot);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return  bindingServiceBoot;
    }



    // TODO: 02.10.2024  end class
}
