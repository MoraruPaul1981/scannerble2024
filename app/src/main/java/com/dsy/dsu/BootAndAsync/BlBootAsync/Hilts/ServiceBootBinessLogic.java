package com.dsy.dsu.BootAndAsync.BlBootAsync.Hilts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.dsy.dsu.BootAndAsync.Service.IntentServiceBoot;
import com.dsy.dsu.Errors.WriteErrorForAll.RecordNewErros;

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
    IntentServiceBoot.LocalBinderBootSerice getlocalBinderBootSerice;
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
            intentstartServiceOneSignal.setAction("lanchAsync");
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





    // TODO: 02.10.2024  end class
}
