package com.sous.scanner;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.ContentValues;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;


import com.scanner.datasync.businesslayer.bl_RemoteMessaging.RemoteMessaging;
import com.sous.scanner.businesslayer.ContentProdiders.ContentProviderScanner;
import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.businesslayer.bl_forActivityScan.BussenslogicOneSignal;
import com.sous.scanner.datalayer.local.CREATE_DATABASEScanner;

import java.io.Serializable;
import java.util.Date;
import javax.inject.Inject;

import dagger.Provides;
import dagger.hilt.android.HiltAndroidApp;


@HiltAndroidApp
public class GetApplication  extends Application {


    @Inject
    RemoteMessaging remoteMessaging;
      @Inject
      CREATE_DATABASEScanner createDatabaseScanner;


    protected SQLiteDatabase Create_Database_СамаБАзаSQLite;
    protected  long version;

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
        try{
        // TODO: 08.08.2024

            PackageInfo    pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);

           version = pInfo.getLongVersionCode();

        BussenslogicOneSignal bussenslogicOneSignal=
                new BussenslogicOneSignal(getApplicationContext(),version);
        bussenslogicOneSignal .initOneSignal();



        /*  //TODO:Иниицилизуем БАз ДАнных */
        Create_Database_СамаБАзаSQLite=createDatabaseScanner.getССылкаНаСозданнуюБазу();

            // TODO: 14.08.2024 тестовый
            Integer startingRemoteMessaging=       remoteMessaging.startingRemoteMessaging(  Create_Database_СамаБАзаSQLite,  version );




        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +"    Flowable.fromAction(new Action() { "
                +   new Date().toLocaleString());

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
