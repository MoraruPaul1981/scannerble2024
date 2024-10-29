package com.serverscan.datasync.datasync_businesslayer.bl_dates;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;


import com.serverscan.datasync.Errors.SubClassErrors;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.core.Single;


@Module
@InstallIn(SingletonComponent.class)
public class DateForJboss {
    
    Context context;

    public @Inject DateForJboss(@ApplicationContext Context hitcontext ) {
        this.context = hitcontext;
    }


    public   String getDateLocal(  @NotNull Long version  ) {
        // TODO: 29.10.2024
       AtomicReference<String>   getDate=new AtomicReference<>();
        try{
       Single.fromCallable(new Callable<String>() {
          @Override
          public String call() throws Exception {
              getDate.set(new BinesslogicFindDatesLocal(context).getDates(version));

              Log.d(context.getClass().getName(), "\n"
                      + " время: " + new Date() + "\n+" +
                      " Класс в процессе... " + this.getClass().getName() + "\n" +
                      " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                      " getDate " +getDate);
              return getDate.get();
          }
      }).doOnError(e->{
          // TODO: 29.08.2024
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

      }).blockingSubscribe();

        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  );

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
        return getDate.get();
    }

    // TODO: 18.10.2024

    public   String getDateRemote(  @NotNull Long version  ) {

        AtomicReference<String> getDateRemote=new AtomicReference<>();
        try{
        Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                  getDateRemote.set(new BinesslogicFindDatesRemote(context).getDates(version));

                Log.d(context.getClass().getName(), "\n"
                        + " время: " + new Date() + "\n+" +
                        " Класс в процессе... " + this.getClass().getName() + "\n" +
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                        " getDate " +getDateRemote);
                return getDateRemote.get();
            }
        }).doOnError(e->{
            // TODO: 29.08.2024
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

        }).blockingSubscribe();
        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );

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

        return getDateRemote.get();
    }


    // TODO: 18.10.2024 end class
}
