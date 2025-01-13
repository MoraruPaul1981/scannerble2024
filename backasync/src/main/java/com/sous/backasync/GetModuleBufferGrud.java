package com.sous.backasync;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

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
    SQLiteDatabase sqlite;
    Context context;


    public  @Inject GetModuleBufferGrud(@ApplicationContext Context context , @NonNull SQLiteDatabase sqlite) {
        this.context = context;
        this.sqlite = sqlite;
    }





    public   void  getModuleBufferGrud( ){
        try {
            // TODO: 13.01.2025
        // TODO: 17.04.2023
        Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                " sqlite " + sqlite);
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }



    public   void  getModuleBufferGrud(@NonNull    SQLiteDatabase getSqlLiteCoreApp ){
        try {
            // TODO: 13.01.2025
            // TODO: 17.04.2023
            Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " getSqlLiteCoreApp " + getSqlLiteCoreApp);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                    Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

}
