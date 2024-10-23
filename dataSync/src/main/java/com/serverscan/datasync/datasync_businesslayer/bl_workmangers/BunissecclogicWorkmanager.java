package com.serverscan.datasync.datasync_businesslayer.bl_workmangers;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.serverscan.datasync.Errors.SubClassErrors;
import com.serverscan.datasync.Services.DataSyncService;


import com.serverscan.datasync.datasync_businesslayer.bl_datasyncservice.BinesslogicDataSyncStarting;
import com.serverscan.datasync.datasync_businesslayer.bl_datasyncservice.interfaces.InterfaceDataSyncServicestarting;
import com.serverscan.datasync.datasync_businesslayer.bl_network.WorkerStatusNewtorks;

import java.util.Random;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent.class)
public class BunissecclogicWorkmanager {

    private Context context;

    private  long version;

    private  DataSyncService.LocalBinderСерверBLE localBinderСерверBLE;
    final private Handler handler=new Handler(Looper.getMainLooper());


    public @Inject BunissecclogicWorkmanager(@ApplicationContext Context hitcontext ) {
        // TODO: 25.08.2024
        context = hitcontext;
        // TODO: 25.08.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

    }

    // TODO: 14.08.2024

public  void  startingAsync(@NonNull Context context,@NonNull Long version){
try {
    // TODO: 26.07.2024

// TODO: 03.09.2024  запуск службы синхронизвции work mamanger
    WorkerStatusNewtorks workerStatusNewtorks=new WorkerStatusNewtorks(context,version);
    Boolean StatusNewtwork= workerStatusNewtorks.getStatusNewtwork();
    if (StatusNewtwork==true) {
        // TODO: 06.09.2024
        launchAsyncWithJboss("SyncWorkManager",version);
    }

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

    public void launchAsyncWithJboss(@NonNull String stateScartServiceScan, @NonNull Long version ) {
        try {
            // TODO: 19.08.2024

            class StartingDataStarting extends BinesslogicDataSyncStarting {
                public StartingDataStarting(@NonNull Context hiltcontext, @NonNull Long hilversion) {
                    super(hiltcontext, hilversion);
                }

                @Override
                public void startingDataSyncService(@NonNull String stateScartServiceScan) {
                    super.startingDataSyncService(stateScartServiceScan);
                    // TODO: 03.09.2024
                    context.startService(intentDataSyncService);
                    // TODO: 19.08.2024
                    // TODO: 26.07.2024
                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
                
                }
            }
            // TODO: 03.09.2024
            // TODO: 26.07.2024
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );

            // TODO: 03.09.2024  класс второй
            class BindigDataStarting extends BinesslogicDataSyncStarting {
                // TODO: 12.09.2024  
                ServiceConnection serviceConnection;
                public BindigDataStarting(@NonNull Context hiltcontext, @NonNull Long hilversion) {
                    super(hiltcontext, hilversion);
                }

                @Override
                public void startingDataSyncService(@NonNull String stateScartServiceScan) {
                    // TODO: 03.09.2024
                    super.startingDataSyncService(stateScartServiceScan);
                    // TODO: 12.09.2024  
                      serviceConnection=    new ServiceConnection() {
                        @Override
                        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                            // TODO: 26.07.2024
                            if (iBinder.isBinderAlive()) {
                                // TODO: 28.07.2023  Update
                                localBinderСерверBLE = (DataSyncService.LocalBinderСерверBLE) iBinder;
                                // TODO: 03.09.2024
                                startingTransactDataService(           serviceConnection    );

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
                   // context.bindService(intentDataSyncService,serviceConnection , Context.BIND_AUTO_CREATE);
                    context.bindService(intentDataSyncService,serviceConnection , Context.BIND_IMPORTANT |Context.BIND_AUTO_CREATE);
                    // TODO: 26.07.2024
                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
                 
                }
            }
// TODO: 03.09.2024 запускам службу один из варантов
            InterfaceDataSyncServicestarting interfaceDataSyncServicestarting = new BindigDataStarting(context,version);
            // TODO: 03.09.2024   биндингом
            interfaceDataSyncServicestarting.startingDataSyncService(stateScartServiceScan);

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
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







    protected void startingTransactDataService( @NonNull        ServiceConnection serviceConnection ) {
        // TODO: 09.08.2024
        try {
            // TODO: 03.09.2024 Запускаем синхронизацию с сервером JBOSS
            localBinderСерверBLE.getService().startingWorkerDatSyncService(context,version);


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

// TODO: 03.09.2024  end CLASS 

}
