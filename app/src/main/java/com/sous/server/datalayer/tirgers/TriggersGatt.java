package com.sous.server.datalayer.tirgers;


import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.common.util.concurrent.AtomicDouble;
import com.sous.server.businesslayer.Errors.SubClassErrors;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.core.Completable;
import kotlin.reflect.KClass;

@Module
@InstallIn(SingletonComponent.class)
public class TriggersGatt {
    private  Context context;


    public @Inject TriggersGatt(@ApplicationContext Context hitcontext ) {
        // TODO: 25.08.2024
        context = hitcontext;
        // TODO: 25.08.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

    }

 public void  triggersGatt( @NotNull  Long  version,@NotNull SQLiteDatabase ССылкаНаСозданнуюБазу){
     // TODO: 18.10.2024  create triger   запуск Бизнес логики 
     Completable.fromAction(()->{
                 // TODO: 12.09.2024

// TODO: 18.10.2024 Создание Тригера Вставки
                  new BinesslogicTrigerrsInrest(ССылкаНаСозданнуюБазу).triggergeneration(context,version);

                 // TODO: 18.10.2024 Создание Тригера Обновления
                 new BinesslogicTrigerrsUpdate(ССылкаНаСозданнуюБазу).triggergeneration(context,version);


                 // TODO: 03.09.2024 get InputStream   for sending an server
                 Log.d(context.getClass().getName(), "\n" + " class " +
                         Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                         " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                         " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

             }).doOnError(e->{
                 e.printStackTrace();
                 Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                         Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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

             })
             .doOnComplete(()->{
                 // TODO: 04.09.2024
                 Log.d(context.getClass().getName(), "\n" + " class " +
                         Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                         " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                         " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

             }).blockingSubscribe();

  }


    // TODO: 18.10.2024  class end

}
