package com.scanner.datasync.businesslayer.Services;

import static android.app.job.JobInfo.PRIORITY_MIN;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scanner.datasync.businesslayer.Errors.SubClassErrors;

import com.scanner.datasync.businesslayer.bl_DataSyncService.BinesslogicDataSync;
import com.scanner.datasync.businesslayer.bl_Jakson.BinesslogincJakson;
import com.scanner.datasync.businesslayer.bl_JbossAdress.QualifierJbossServer3;
import com.scanner.datasync.businesslayer.bl_Okhhtp.interfaces.QualifierOkhhtp;
import com.scanner.datasync.businesslayer.bl_Okhhtp.interfaces.QualifierOkhhtpTLS;
import com.scanner.datasync.datalayer.local.BinesslogicGetCursors;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */

@AndroidEntryPoint
public class DataSyncService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    @Inject
    ObjectMapper getHiltJaksonObjectMapper;

    private  long version;
    @Inject
    BinesslogicDataSync binesslogicDataSync;
    @Inject
    BinesslogincJakson binesslogincJakson;
    private  ContentResolver resolver ;


    @Inject
    @QualifierJbossServer3
    public LinkedHashMap<String,String> getJbossAdressDebug;



    @Inject
    BinesslogicGetCursors binesslogicGetCursors;


    @Inject
   // @QualifierOkhhtp
    @QualifierOkhhtpTLS
    OkHttpClient.Builder getOkhhtpBuilder;

    public DataSyncService() {
        super("DataSyncService");
    }

    // TODO: Customize helper method
    public DataSyncService(String name) {
        super(name);
        
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //For creating the Foreground Service
        Notification();

        resolver = getApplicationContext().getContentResolver();

        // TODO: 21.08.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");


    }

    private void Notification() {
        // TODO: 23.08.2024
        try{
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? getNotificationChannel(notificationManager) : "";
        NotificationCompat.Builder   notificationBuilderServer = new NotificationCompat.Builder(this, channelId);
        Notification notification = notificationBuilderServer.setOngoing(true)
                .setPriority(PRIORITY_MIN)
                .setCategory(NotificationCompat.CATEGORY_PROGRESS)
                .build();

        startForeground(23, notification);//

            // TODO: 25.08.2024





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
        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }

}

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            // Do something
            stopForeground(true);

            // TODO: 22.08.2024  повсе всего Работы Службы Синхронихации запускаем Фрагмент Сканера   , Самая последная Операция
            binesslogicDataSync.callBackBroadcastManagerDataSyncService(version);
            // TODO: 22.08.2024

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
        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        return super.onBind(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try{
        // TODO: 08.08.2024
        PackageInfo pInfo =newBase.getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
        version = pInfo.getLongVersionCode();
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
        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        try{
          // TODO: 22.08.2024  повсе всего Работы Службы Синхронихации запускаем Фрагмент Сканера   , Самая последная Операция
            Single.fromCallable(()->{

                // TODO: 26.08.2024  получаем данные ЛОкальыне с версией данных
                //Cursor cursorlocal =     binesslogicGetCursors. getLocalDataSyncService(version,resolver);
                // Cursor cursorlocal =     binesslogicGetCursors. getMAXBremyLocalDataSyncService(version,resolver);
                Cursor cursorlocal =     binesslogicGetCursors. getMAXVersionLocalDataSyncService(version,resolver);
                // TODO: 26.08.2024  получаем данные от Сервера
                InputStream inputStreamJaksonByteScanner=     binesslogicDataSync
                        .callOkhhtpDataSyncService(version,getJbossAdressDebug,cursorlocal,getOkhhtpBuilder);

                // TODO: 26.08.2024  преобразовываем данеы в модель JAKSON
                JsonNode jsonNodeScannerBLE =binesslogincJakson.callJaksonDataSyncService(version,   getHiltJaksonObjectMapper,inputStreamJaksonByteScanner);

                // TODO: 21.08.2024
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n+ " +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  +
                        " jsonNodeScannerBLE " +jsonNodeScannerBLE);

                return jsonNodeScannerBLE;
                // TODO: 24.10.2024
            }).doOnSuccess(jsonNodeScannerBLESuccess->{

                if (jsonNodeScannerBLESuccess!=null) {
                    // TODO: 23.08.2024  записываем JAKSON в Контент ПРовайер
                    if (jsonNodeScannerBLESuccess.size()>0) {
                        binesslogincJakson.updateOperaticallContentResolver(version,jsonNodeScannerBLESuccess);
                    }
                }

                // TODO: 21.08.2024
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n+ " +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  +
                        " jsonNodeScannerBLESuccess " +jsonNodeScannerBLESuccess);

            }).blockingGet();
            // TODO: 21.08.2024  
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n+ " +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            // TODO: 28.08.2024
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        ContentValues valuesЗаписываемОшибки = new ContentValues();
        valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
        valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
        valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
        valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
        final Object ТекущаяВерсияПрограммы = version;
        Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
        valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private String getNotificationChannel(NotificationManager notificationManager){
        String channelId = "channelid";
        String channelName = getResources().getString(com.google.android.material.R.string.fab_transformation_scrim_behavior);
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setImportance(NotificationManager.IMPORTANCE_NONE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
        return channelId;
    }



}