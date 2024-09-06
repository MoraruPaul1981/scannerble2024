package com.serverscan.datasync.businesslayer.Services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.BinderThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverscan.datasync.businesslayer.Errors.SubClassErrors;
import com.serverscan.datasync.businesslayer.bl_Jakson.BinesslogicJakson;
import com.serverscan.datasync.businesslayer.bl_jbossadress.QualifierJbossServer3;
import com.serverscan.datasync.datalayer.generatorjakson.GenerationJaksonJSON;
import com.serverscan.datasync.datalayer.local.BusinesslogicDatabase;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;


@AndroidEntryPoint
public class DataSyncService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    public LocalBinderСерверBLE localBinderСерверBLE = new LocalBinderСерверBLE();
    private  Long version;

    @Inject
    BusinesslogicDatabase businesslogicDatabase;

    @Inject
    GenerationJaksonJSON genetarorJaksonJSON;

    @Inject
    BinesslogicJakson binesslogicJakson;

    @Inject
    @QualifierJbossServer3
    public LinkedHashMap<String,String> getJbossAdressDebug;
    @Inject
    ObjectMapper getHiltJaksonObjectMapper;
    @Inject
    OkHttpClient.Builder getOkhhtpBuilder;




    public DataSyncService() {
        super("DataSyncService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try{
        PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
        version = pInfo.getLongVersionCode();
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
    public void onDestroy() {
        super.onDestroy();
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




    public void onTransact(@NonNull Context context ,@NonNull Long version,@NonNull String stateScartServiceScan) throws RemoteException {
        // TODO: 03.09.2024
        // TODO: 04.09.2024
      Completable.fromAction(()->{
                  // TODO: 03.09.2024 get DATA
                  Cursor cursorSingle= businesslogicDatabase.getingCursor("SELECT * FROM scannerserversuccess ",version);
                  // TODO: 03.09.2024
                  if (cursorSingle.getCount()>0) {

                      // TODO: 23.08.2024 Генерирум List базе курсора в Обьекты Листа ЧТобы ПОтом ПОлучить Jakson Json
                      List<?>  listForJakson=     genetarorJaksonJSON.genetarorListFor(context,version,cursorSingle);
                      // TODO: 03.09.2024 get Stream based on Cursor
                      byte[] ByteJakson=      genetarorJaksonJSON.genetarorJaksonJSON(context,version,     listForJakson  ,getHiltJaksonObjectMapper     );

                      // TODO: 03.09.2024 sending  Stream to Server
                      StringBuffer stringBufferJsonForJboss=      binesslogicJakson.
                              sendOkhhtpServiceForJboss(context,version,getOkhhtpBuilder,getJbossAdressDebug,cursorSingle ,ByteJakson);
                      // TODO: 04.09.2024
                      // TODO: 03.09.2024 get InputStream   for sending an server
                      Log.d(getApplicationContext().getClass().getName(), "\n" + " class " +
                              Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                              " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                              " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                              " stringBufferJsonForJboss " +stringBufferJsonForJboss);

                  }
                  // TODO: 03.09.2024 get InputStream   for sending an server
                  Log.d(getApplicationContext().getClass().getName(), "\n" + " class " +
                          Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                          " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                          " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                          " cursorSingle.getCount() " +cursorSingle.getCount());

      }).doOnError(e->{
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

      })
              .doOnComplete(()->{
                  // TODO: 04.09.2024
                  Log.d(getApplicationContext().getClass().getName(), "\n" + " class " +
                          Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                          " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                          " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

              }).blockingSubscribe();
        Log.d(getApplicationContext().getClass().getName(), "\n" + " class " +
                Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }
}