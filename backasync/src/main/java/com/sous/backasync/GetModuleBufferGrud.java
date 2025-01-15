package com.sous.backasync;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.sous.backasync.provider.GetProvider;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent.class)
@Named("getModuleBufferGrud")
public class GetModuleBufferGrud {


  public  static      SQLiteDatabase sqliteModuleGrud;
    Context context;


    public  @Inject GetModuleBufferGrud(@ApplicationContext Context context , @NonNull SQLiteDatabase sqlite) {
        try {
            this.context = context;
            this.sqliteModuleGrud = sqlite;
            // TODO: 13.01.2025
            GetProvider getProvider = new GetProvider();
            getProvider.onCreate();
            Log.d(this.getClass().getName(),"\n" + " class GetModuleBufferGrud  " + Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " sqliteModuleGrud " + sqliteModuleGrud);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                    Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }



    public   void  getModuleBufferGrud( ){
        try {
        // TODO: 17.04.2023
        Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName()
                + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                " sqliteModuleGrud " + sqliteModuleGrud);
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }



    public   void  getModuleBufferGrud(@NonNull  Context context ,@NonNull    SQLiteDatabase getSqlLiteCoreApp ){
        try {
            // TODO: 13.01.2025
            // TODO: 17.04.2023
            Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " getSqlLiteCoreApp " + getSqlLiteCoreApp  + " context " +context);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                    Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }




}
