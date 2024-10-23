package com.serverscan.datasync.datasync_businesslayer.bl_datasyncservice;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.serverscan.datasync.Errors.SubClassErrors;
import com.serverscan.datasync.Services.DataSyncService;
import com.serverscan.datasync.datasync_businesslayer.bl_network.WorkerStatusNewtorks;

import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import rx.exceptions.Exceptions;


@Module
@InstallIn(SingletonComponent.class)
public class BunissecclogicBindDataSyncService {
    private Context context;
    private  long version;

    public   @Inject BunissecclogicBindDataSyncService(@ApplicationContext Context hitcontext ) {
        // TODO: 25.08.2024
        context = hitcontext;
        // TODO: 23.10.2024 starting
        // TODO: 25.08.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

    }

    // TODO: 14.08.2024


    public void bindServiceDataSyncJboss(@NonNull Context context ,@NonNull Long version ) {
      try{

          Intent intentDataSyncService = new Intent(context, DataSyncService.class);
          // TODO: 15.08.2024
          intentDataSyncService.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
          intentDataSyncService.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
          intentDataSyncService.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
          intentDataSyncService.addFlags(Intent.FLAG_FROM_BACKGROUND);
          intentDataSyncService.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
      /*    // TODO: 08.08.2024
          ContextCompat.startForegroundService(context,ServiceGattServerScan);*/
          ServiceConnection serviceConnectionDatStnc=    new ServiceConnection() {
              @Override
              public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                  // TODO: 26.07.2024
                  if (iBinder.isBinderAlive()) {
                      // TODO: 28.07.2023  Update
                      DataSyncService.LocalBinderСерверBLE     localBinderСерверBLE = (DataSyncService.LocalBinderСерверBLE) iBinder;
                      // TODO: 03.09.2024
                      //TODO: 03.09.2024 Запускаем синхронизацию с сервером JBOSS
                    localBinderСерверBLE.getService().startingWorkerDatSyncService(context, version);

                      Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                              " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                              " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
                  }

                  Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                          " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                          " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
              }

              @Override
              public void onServiceDisconnected(ComponentName componentName) {
                  // TODO: 26.07.2024
                  Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                          " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                          " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
              }
          };
          // TODO: 19.08.2024
          // TODO: 23.10.2024 starting
          context.bindService(intentDataSyncService,Context.BIND_AUTO_CREATE, Executors.newCachedThreadPool(),serviceConnectionDatStnc  );
          Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );


    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName()
                + " Линия  :"
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


// TODO: 03.09.2024  end CLASS

}
