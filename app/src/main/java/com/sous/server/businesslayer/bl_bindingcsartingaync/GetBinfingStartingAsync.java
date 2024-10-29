package com.sous.server.businesslayer.bl_bindingcsartingaync;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.serverscan.datasync.datasync_businesslayer.bl_datasyncservice.BunissecclogicBindDataSyncService;
import com.serverscan.datasync.datasync_businesslayer.bl_network.WorkerStatusNewtorks;
import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.bl_bindingcsartingaync.interfaces.GetBinfingStartingAsyncInterface;

public class GetBinfingStartingAsync implements GetBinfingStartingAsyncInterface {

    Context context;

    Long version;


    public GetBinfingStartingAsync(Context context, Long version) {
        this.context = context;
        this.version = version;
    }

    /**
     *
     */
    @Override
    public void binfingStartingAsync() {
        try{
            WorkerStatusNewtorks workerStatusNewtorks=new WorkerStatusNewtorks(context,version);
            Boolean StatusNewtwork= workerStatusNewtorks.getStatusNewtwork();
            if (StatusNewtwork==true) {
                // TODO: 06.09.2024
                //todo зпуск синхрониазции
                new BunissecclogicBindDataSyncService(context).bindServiceDataSyncJboss(context,version);

            }else{


                context.getMainExecutor().execute(()->{
                    Toast toast = Toast.makeText(context, "Нет  сети !!! ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast.getYOffset() / 2);
                    toast.show();
                });
            }
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


    // TODO: 29.10.2024 end class

}
