package com.serverscan.datasync.businesslayer.bl_okhttpclient;


import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;


import com.serverscan.datasync.businesslayer.Errors.SubClassErrors;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;



@Module
@InstallIn(SingletonComponent.class)
public class GetOkhhtpBuilder   implements  OkhhtpInterface {
    Long version;

    @Singleton
    @Provides
    @Override
    public  OkHttpClient.Builder getOkhhtpBuilder(@ApplicationContext Context hiltcontext ) {
        OkHttpClient.Builder builder=null;
        try{
            PackageInfo pInfo = hiltcontext.getPackageManager().getPackageInfo(hiltcontext.getPackageName(), 0);
            version = pInfo.getLongVersionCode();


            builder=     new OkHttpClient().newBuilder();
            builder.connectionPool(new ConnectionPool(100,5, TimeUnit.SECONDS));
            Log.i(this.getClass().getName(),  " OkHttpClient"+
                    Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " время " +new Date().toLocaleString() );
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
            new SubClassErrors(hiltcontext).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return builder;
    }
}



