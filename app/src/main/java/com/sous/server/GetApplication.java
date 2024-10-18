package com.sous.server;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.ContentValues;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.serverscan.datasync.datasync_businesslayer.Services.DataSyncService;
import com.serverscan.datasync.datasync_businesslayer.bl_workmangers.BunissecclogicWorkmanager;
import com.serverscan.datasync.datasync_businesslayer.bl_workmangers.RemoteMessaging;
import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.bl_OneSingal.BussenslogicOneSignal;
import com.sous.server.datalayer.binesslogic.GetCurrentDatabase;


import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;


@HiltAndroidApp
public class GetApplication  extends Application {
    private   long   version;
    protected SQLiteDatabase Create_Database_СамаБАзаSQLite;
    @Inject
    BunissecclogicWorkmanager bunissecclogicWorkmanager;



    private  DataSyncService.LocalBinderСерверBLE localBinderСерверBLE;
    public GetApplication() throws PackageManager.NameNotFoundException {
        super();
// TODO: 12.08.2024
        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +"    Flowable.fromAction(new Action() { "
                +   new Date().toLocaleString());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
        // TODO: 08.08.2024
        PackageInfo  pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            version = pInfo.getLongVersionCode();


        BussenslogicOneSignal bussenslogicOneSignal   =    new BussenslogicOneSignal(getApplicationContext(),version);
        bussenslogicOneSignal  .initOneSignal();

            /*  //TODO:Иниицилизуем БАз ДАнных */
            Create_Database_СамаБАзаSQLite=    new GetCurrentDatabase(getApplicationContext() ,version).initingCurrentDatabase();

            // TODO: 14.08.2024

            RemoteMessaging remoteMessaging=new RemoteMessaging(Create_Database_СамаБАзаSQLite,getApplicationContext(),version);
            // TODO: 14.08.2024
            // TODO: 14.08.2024 start workmanager
              remoteMessaging.initWorkmanager();


            // TODO: 03.09.2024 запускаем синхрониазцию с ссервром Server GATT
           //// bunissecclogicWorkmanager.startingAsync(getApplicationContext(),version);

            // TODO: 26.07.2024
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " Create_Database_СамаБАзаSQLite " +Create_Database_СамаБАзаSQLite);
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
    public void onTerminate() {
        super.onTerminate();
        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +"    Flowable.fromAction(new Action() { "
                +   new Date().toLocaleString());
    }

    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        super.registerComponentCallbacks(callback);
        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +"    Flowable.fromAction(new Action() { "
                +   new Date().toLocaleString());
    }
}
