package com.serverscan.datasync.businesslayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.work.multiprocess.RemoteWorkManager;

import com.serverscan.datasync.Errors.SubClassErrors;

import java.util.concurrent.ConcurrentSkipListSet;

public class RemoteMessaging {

    private SQLiteDatabase Create_Database_СамаБАзаSQLite;
    private Context context;
    private long version;

    public RemoteMessaging(SQLiteDatabase create_Database_СамаБАзаSQLite, Context context, long version) {
        Create_Database_СамаБАзаSQLite = create_Database_СамаБАзаSQLite;
        this.context = context;
        this.version = version;
    }


    // TODO: 14.08.2024

  public   Integer startingRemoteMessaging(){
        return Create_Database_СамаБАзаSQLite.getVersion();
    }


    public  void initWorkmanager(){
        // TODO: 14.08.2024
        try{
        InitWorkManager initWorkManager=new InitWorkManager(context,version);
            initWorkManager.initWorkManager();

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

}
