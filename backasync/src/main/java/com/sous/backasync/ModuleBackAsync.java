package com.sous.backasync;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.sous.backasync.cursor.CursorLoaderBackSync;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent.class)
@Named("getModuleBackAsync")
public class ModuleBackAsync {



    Context context;


    public  @Inject ModuleBackAsync(@ApplicationContext Context context ) {
        try {
            this.context = context;
            // TODO: 13.01.2025
            Log.d(this.getClass().getName(),"\n" + " class ModuleBackAsync  " + Thread.currentThread().getStackTrace()[2].getClassName()
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


    // TODO: 17.01.2025 Курсор получение данных   сусловиями WHERE

    public   Cursor backasyncQueryandWhere(@NonNull String Таблица, @NonNull  String   СамЗапрос , @NonNull   String[]УсловияЗапроса){
        // TODO: 17.01.2025
        Cursor getbackasyncQueryandWhere=null;
        try {
            CursorLoaderBackSync cursorLoaderBackSync =new CursorLoaderBackSync(context);
            //////TODO ГЛАВНЫЙ КУРСОР ДЛЯ НЕПОСРЕДТСВЕНОГО ЗАГРУЗКИ СОТРУДНИКА
            Bundle bundle = new Bundle();
            bundle.putString("Таблица",Таблица);
            bundle.putString("СамЗапрос",СамЗапрос);
            bundle.putStringArray("УсловияЗапроса",УсловияЗапроса);
            // TODO: 17.01.2025
              getbackasyncQueryandWhere=    cursorLoaderBackSync.getBackasyncCursor( bundle);

        // TODO: 17.04.2023
        Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName()
                + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " getbackasyncQueryandWhere " +getbackasyncQueryandWhere);
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
        return  getbackasyncQueryandWhere;
    }


    // TODO: 17.01.2025 Курсор получение данных

    public   Cursor backasyncQuery(@NonNull String Таблица, @NonNull  String   СамЗапрос  ){
        // TODO: 17.01.2025
        Cursor getbackasyncQuery=null;
        try {
            CursorLoaderBackSync cursorLoaderBackSync =new CursorLoaderBackSync(context);
            //////TODO ГЛАВНЫЙ КУРСОР ДЛЯ НЕПОСРЕДТСВЕНОГО ЗАГРУЗКИ СОТРУДНИКА
            Bundle bundle = new Bundle();
            bundle.putString("Таблица",Таблица);
            bundle.putString("СамЗапрос",СамЗапрос);
            // TODO: 17.01.2025
              getbackasyncQuery=    cursorLoaderBackSync.getBackasyncCursor( bundle);
            // TODO: 17.04.2023
            Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + "getbackasyncQuery " +getbackasyncQuery);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                    Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return getbackasyncQuery;
    }




}
