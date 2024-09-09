package com.serverscan.datasync.businesslayer.bl_workmangers;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.serverscan.datasync.businesslayer.Errors.SubClassErrors;
import com.serverscan.datasync.businesslayer.Services.DataSyncService;
import com.serverscan.datasync.businesslayer.bl_datasyncservice.BinesslogicDataSyncService;
import com.serverscan.datasync.businesslayer.bl_datasyncservice.InterfaceDataSyncService;
import com.serverscan.datasync.businesslayer.bl_network.WorkerStatusNewtorks;

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

            class StartingDataService extends BinesslogicDataSyncService{
                public StartingDataService(@NonNull Context hiltcontext, @NonNull Long hilversion) {
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
            class BindigDataService extends BinesslogicDataSyncService{
                public BindigDataService(@NonNull Context hiltcontext, @NonNull Long hilversion) {
                    super(hiltcontext, hilversion);
                }

                @Override
                public void startingDataSyncService(@NonNull String stateScartServiceScan) {
                    // TODO: 03.09.2024
                    super.startingDataSyncService(stateScartServiceScan);
                    // TODO: 19.08.2024
                    context.bindService(intentDataSyncService, new ServiceConnection() {
                        @Override
                        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                            // TODO: 26.07.2024
                            if (iBinder.isBinderAlive()) {
                             // TODO: 28.07.2023  Update
                                 localBinderСерверBLE = (DataSyncService.LocalBinderСерверBLE) iBinder;
                                // TODO: 03.09.2024
                                startingTransactDataService(      );

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
                    }, Context.BIND_AUTO_CREATE);
                 
                }
            }
// TODO: 03.09.2024 запускам службу один из варантов
            InterfaceDataSyncService interfaceDataSyncService= new BindigDataService(context,version);
            // TODO: 03.09.2024   биндингом
            interfaceDataSyncService.startingDataSyncService(stateScartServiceScan);

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







    protected void startingTransactDataService(  ) {
        // TODO: 09.08.2024
        try {
            // TODO: 03.09.2024 Запускаем синхронизацию с сервером JBOSS
       localBinderСерверBLE.getService().onTransact(context,version );

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
