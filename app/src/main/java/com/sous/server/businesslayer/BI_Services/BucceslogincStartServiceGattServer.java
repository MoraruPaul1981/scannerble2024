package com.sous.server.businesslayer.BI_Services;

import static android.content.Context.POWER_SERVICE;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.loader.content.AsyncTaskLoader;

import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.Services.ServiceServerScan;

import org.jetbrains.annotations.NotNull;

public class BucceslogincStartServiceGattServer {

private Context context;
private  Long version;

    public BucceslogincStartServiceGattServer(Context context, Long version) {
        this.context = context;
        this.version = version;
    }


    public void startingServiceGattServer(@NotNull Message message) {
        try {
            AsyncTaskLoader asyncTaskLoader=new AsyncTaskLoader(context) {
                @Nullable
                @Override
                public Object loadInBackground() {


                    message.getTarget().postDelayed(()->{


                        // TODO: 23.07.2024 starting
                        Intent ServiceGattServerScan = new Intent(context, ServiceServerScan.class);
                        // TODO: 15.08.2024
                        ServiceGattServerScan=  startPowerManager(ServiceGattServerScan);
                        ServiceGattServerScan.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        ServiceGattServerScan.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        ServiceGattServerScan.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                        ServiceGattServerScan.addFlags(Intent.FLAG_FROM_BACKGROUND);
                        ServiceGattServerScan.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        // TODO: 08.08.2024
                        ContextCompat.startForegroundService(context,ServiceGattServerScan);

                        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");


                    },2000);

                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");


                    return null;
                }
            };
            asyncTaskLoader.forceLoad();
            asyncTaskLoader.loadInBackground();



            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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






    public Intent  startPowerManager(@NotNull  Intent intent){
        try{
// TODO: 02.08.2024
            String packageName =context. getPackageName();
            PowerManager pm = (PowerManager) context.getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                intent.setData(Uri.parse("package:" + packageName));
            }
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
            // new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }

        return  intent;

    }

    // TODO: 15.08.2024 end

}
