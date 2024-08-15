package com.sous.scanner;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.sous.scanner.businesslayer.bl_forActivityScan.BussenslogicOneSignal;
import com.sous.scanner.datalayer.local.CREATE_DATABASEScanner;

import java.util.Date;

public class GetApplication  extends Application {

    protected SQLiteDatabase Create_Database_СамаБАзаSQLite;
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
        // TODO: 08.08.2024
        PackageInfo pInfo = null;
        try {
            pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        long  version = pInfo.getLongVersionCode();

        BussenslogicOneSignal bussenslogicOneSignal=
                new BussenslogicOneSignal(getApplicationContext(),version);
        bussenslogicOneSignal .initOneSignal();



        /*  //TODO:Иниицилизуем БАз ДАнных */
        Create_Database_СамаБАзаSQLite=new CREATE_DATABASEScanner(getApplicationContext()).getССылкаНаСозданнуюБазу();

        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +"    Flowable.fromAction(new Action() { "
                +   new Date().toLocaleString());
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
