package com.serverscan.datasync.datalayer.local;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.serverscan.datasync.businesslayer.Errors.SubClassErrors;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


@Module
@InstallIn(SingletonComponent.class)
public class BusinesslogicDatabase {

    // TODO: 19.08.2024
    private Context context;


    public @Inject BusinesslogicDatabase(@ApplicationContext Context hitcontext ) {
        // TODO: 25.08.2024
        this.context = hitcontext;
        // TODO: 25.08.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

    }

    public  Cursor getingCursor(@NonNull String СамЗапрос,@NonNull  Long version) {
        // TODO: 29.08.2024
        Single<Cursor> cursorSingle=   Single.fromCallable(new Callable<Cursor>() {
            @Override
            public Cursor call() throws Exception {
                // TODO: 29.08.2024 создаем новы экзепляр класс
                Uri uri = Uri.parse("content://com.sous.servergatt.prodider/" +"scannerserversuccess" + "");
                ContentResolver resolver = context. getContentResolver();
                Cursor    cursor = resolver.query(uri, null, СамЗапрос, null,null,null);
                if (cursor.getCount()>0) {
                    cursor.moveToFirst();
                }
                Log.d(context.getClass().getName(), "\n" + " class " +
                        Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + "cursor " +cursor);

                return cursor;
            }
        }).doOnError(e->{
            // TODO: 29.08.2024
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }).doOnSuccess(s->{
            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
        });
        // TODO: 29.08.2024
        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );

        return cursorSingle.blockingGet();
    }

}
