package com.sous.server.datalayer.local.module;


import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.datalayer.local.CREATE_DATABASEServerScanner;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;




@Module
@InstallIn(SingletonComponent.class)
public class getDataBaceHilt {

 Long version;
    @Provides
    @NotNull
    @Singleton
    public   SQLiteDatabase getHiltDataBase(@ApplicationContext Context hitcontext) {
        SQLiteDatabase  Create_Database_СамаБАзаSQLite=null;
        try{
            PackageInfo pInfo = hitcontext.getPackageManager().getPackageInfo(hitcontext.getPackageName(), 0);
            version = pInfo.getLongVersionCode();

            // TODO: 22.10.2024
           Create_Database_СамаБАзаSQLite= new CREATE_DATABASEServerScanner(hitcontext).getССылкаНаСозданнуюБазу();


        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +"    Create_Database_СамаБАзаSQLite "
                +   Create_Database_СамаБАзаSQLite.isOpen());
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        ContentValues valuesЗаписываемОшибки=new ContentValues();
        valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
        valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
        valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
        valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
        final Object ТекущаяВерсияПрограммы =version;
        Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
        valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
        new SubClassErrors(hitcontext).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }
        return Create_Database_СамаБАзаSQLite;
    }


}
