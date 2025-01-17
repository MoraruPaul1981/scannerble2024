package com.sous.backasync;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.sous.backasync.cursor.BackasyncCursor;

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



    Context context;


    public  @Inject GetModuleBufferGrud(@ApplicationContext Context context ) {
        try {
            this.context = context;
            // TODO: 13.01.2025
            Log.d(this.getClass().getName(),"\n" + " class GetModuleBufferGrud  " + Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                    Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }



    public   void  backasyncQueryError(@NonNull String Таблица, @NonNull  String   СамЗапрос ,@NonNull   String[]УсловияЗапроса){
        try {
            BackasyncCursor backasyncCursor=new BackasyncCursor(context);
            //////TODO ГЛАВНЫЙ КУРСОР ДЛЯ НЕПОСРЕДТСВЕНОГО ЗАГРУЗКИ СОТРУДНИКА
            Bundle bundleError= new Bundle();
            bundleError.putString("Таблица",Таблица);
            bundleError.putString("СамЗапрос",СамЗапрос);
            bundleError.putStringArray("УсловияЗапроса",УсловияЗапроса);
            Cursor cursorError=    backasyncCursor.getBackasyncCursor( bundleError);

        // TODO: 17.04.2023
        Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName()
                + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }



    public   void  backasyncQueryError(@NonNull  Context context ,@NonNull    SQLiteDatabase getSqlLiteCoreApp ){
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
