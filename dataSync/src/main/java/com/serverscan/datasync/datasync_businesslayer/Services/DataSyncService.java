package com.serverscan.datasync.datasync_businesslayer.Services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverscan.datasync.datasync_businesslayer.Errors.SubClassErrors;
import com.serverscan.datasync.datasync_businesslayer.bl_datasyncservice.BinesslogicDataSyncServiceGetGet;
import com.serverscan.datasync.datasync_businesslayer.bl_datasyncservice.BinesslogicDataSyncServiceGetPost;
import com.serverscan.datasync.datasync_businesslayer.bl_jbossadress.QualifierJbossServer3;
import com.serverscan.datasync.datasync_businesslayer.bl_okhttpclient.QualifierOkhhtp;
import com.serverscan.datasync.datasync_datalayer.generatorjakson.GenerationJaksonJSON;
import com.serverscan.datasync.datasync_datalayer.local.BusinesslogicDatabase;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;


@AndroidEntryPoint
public class DataSyncService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    public LocalBinderСерверBLE localBinderСерверBLE = new LocalBinderСерверBLE();
    public  Long version;

    @Inject
    public  BusinesslogicDatabase businesslogicDatabase;

    @Inject
    public  GenerationJaksonJSON genetarorJaksonJSON;

    @Inject
    @QualifierJbossServer3
    public LinkedHashMap<String,String> getJbossAdressDebug;

    @QualifierOkhhtp
    @Inject
    public OkHttpClient.Builder getOkhhtpBuilder;


    @Inject
    public  ObjectMapper getHiltJaksonObjectMapper;


    @Inject
    public BinesslogicDataSyncServiceGetPost binesslogicDataSyncServiceGetPost;
    @Inject
    public BinesslogicDataSyncServiceGetGet binesslogicDataSyncServiceGetGet;

    public SharedPreferences preferencesGatt;

    private AtomicReference<DataSyncService>  dataSyncService=new AtomicReference<>();

    public DataSyncService() {
        super("DataSyncService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try{
        PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
        version = pInfo.getLongVersionCode();
            preferencesGatt =getApplicationContext(). getSharedPreferences("MyPrefs", MODE_PRIVATE);

            dataSyncService.set(this);

        Log.d(getApplicationContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() +
                "\n" +
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
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

            // TODO: 29.08.2024  Сразу Две обработки и Get и POST к серверу и от сервера
           Single.fromCallable(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    // TODO: 16.10.2024  POST  send
                    binesslogicDataSyncServiceGetPost.proseccingDataSyncPost(getApplicationContext(),version,dataSyncService.get());

                    Log.d(getApplicationContext().getClass().getName(), "\n"
                            + " class " + Thread.currentThread().getStackTrace()[2].getClassName() +
                            "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                    return null;
                }
            }).doOnError(e->{
                // TODO: 29.08.2024
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
            }).doOnSuccess(s->{
                // TODO: 29.08.2024
                       // TODO: 16.10.2024  GET  getting
                       binesslogicDataSyncServiceGetGet.proseccingDataSyncGet(getApplicationContext(),version,dataSyncService.get());

                       Log.d(getApplicationContext().getClass().getName(), "\n"
                               + " class " + Thread.currentThread().getStackTrace()[2].getClassName() +
                               "\n" +
                               " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                               " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            }).subscribeOn(Schedulers.single())
                   .subscribe();
            // TODO: 12.09.2024 запуск обработки POST gatt server jboss
            Log.d(getApplicationContext().getClass().getName(), "\n"
                    + " class " + Thread.currentThread().getStackTrace()[2].getClassName() +
                    "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");


        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // TODO: 12.09.2024
        // Return this instance of LocalService so clients can call public methods
        Log.d(getApplicationContext().getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());

    }

    public class LocalBinderСерверBLE extends Binder {
        public DataSyncService getService() {
            // Return this instance of LocalService so clients can call public methods
            Log.d(getApplicationContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
            return DataSyncService.this;
        }


        @Override
        public boolean pingBinder() {
            return super.pingBinder();
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(getApplicationContext().getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
        //   return super.onBind(intent);
        return localBinderСерверBLE;

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {}

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method




}