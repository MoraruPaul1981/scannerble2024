package com.serverscan.datasync.datasync_businesslayer.bl_datasyncservice;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.serverscan.datasync.Errors.SubClassErrors;
import com.serverscan.datasync.Services.DataSyncService;



import com.serverscan.datasync.datasync_businesslayer.bl_network.WorkerStatusNewtorks;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent.class)
public class BunissecclogicStartigDataSyncService {

    private Context context;

    private  long version;

    private  DataSyncService.LocalBinderСерверBLE localBinderСерверBLE;
    final private Handler handler=new Handler(Looper.getMainLooper());


    public @Inject BunissecclogicStartigDataSyncService(@ApplicationContext Context hitcontext ) {
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

        localBinderСерверBLE.getService().startingWorkerDatSyncService(context, version);

    }else{


        context.getMainExecutor().execute(()->{
            Toast toast = Toast.makeText(context, "Нет  сети !!! ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast.getYOffset() / 2);
            toast.show();
        });
    }

    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
            " localBinderСерверBLE " +localBinderСерверBLE);


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
