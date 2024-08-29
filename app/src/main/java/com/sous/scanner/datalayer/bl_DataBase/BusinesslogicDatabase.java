package com.sous.scanner.datalayer.bl_DataBase;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.sous.scanner.businesslayer.Errors.SubClassErrors;

import java.util.concurrent.Callable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BusinesslogicDatabase {

    // TODO: 19.08.2024
private Context context;
private  Long version;

    public BusinesslogicDatabase(Context context, Long version) {
        this.context = context;
        this.version = version;
    }


    public  Cursor getingCursor(@NonNull String СамЗапрос) {
            // TODO: 29.08.2024
        Single<Cursor> cursorSingle=   Single.fromCallable(new Callable<Cursor>() {
                        @Override
                        public Cursor call() throws Exception {
                            // TODO: 29.08.2024 создаем новы экзепляр класс
                            Uri uri = Uri.parse("content://com.sous.scanner.prodider/" +"listMacMastersSous" + "");
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
                    }).subscribeOn(Schedulers.single());
            // TODO: 29.08.2024
            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );

        return cursorSingle.blockingGet();
    }

}
