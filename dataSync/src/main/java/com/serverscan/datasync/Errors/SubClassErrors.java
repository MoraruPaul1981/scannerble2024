package com.serverscan.datasync.datasync_businesslayer.Errors;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.Optional;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SubClassErrors {
    Context context;
    public SubClassErrors(@NonNull Context context) {
        this.context =context;
    }
    public  void МетодЗаписиОшибок(@NonNull ContentValues contentValuesДляЗаписиОшибки) {
        try {
            Log.i( context.getClass().getName(), "contentValuesДляЗаписиОшибки  " + contentValuesДляЗаписиОшибки);

            // TODO: 22.08.2024 Server gatt wtire Errror
            Completable.fromRunnable(()->{


                        Uri uri = Uri.parse("content://com.sous.servergatt.prodider/errordsu1" );
                        ContentResolver resolver = context. getContentResolver();
                        Uri    insertData=   resolver.insert(uri, contentValuesДляЗаписиОшибки);
                        Integer РезультатВставки= Optional.ofNullable(insertData.toString().replaceAll("content://","")).map(Integer::new).orElse(0);
                        // TODO: 08.08.2024
                        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +"ERROR ERROR ERROR GATT SERVER  !!!!!!"
                                +"\n"+ " РезультатВставки " +РезультатВставки);




                        // TODO: 08.08.2024
                        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +"ERROR ERROR ERROR CLETNT SERVER  !!!!!!"+"\n"+ " РезультатВставки " +РезультатВставки+
                                " contentValuesДляЗаписиОшибки " +contentValuesДляЗаписиОшибки);


                    })
                    .subscribeOn(Schedulers.single())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                            // TODO: 08.08.2024
                            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +"ERROR ERROR ERROR CLETNT SERVER  !!!!!!"+
                                    " contentValuesДляЗаписиОшибки " +contentValuesДляЗаписиОшибки);
                        }

                        @Override
                        public void onComplete() {
                            // TODO: 08.08.2024
                            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +"ERROR ERROR ERROR CLETNT SERVER  !!!!!!"+
                                    " contentValuesДляЗаписиОшибки " +contentValuesДляЗаписиОшибки);
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            // TODO: 08.08.2024
                            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +"ERROR ERROR ERROR CLETNT SERVER  !!!!!!"+
                                    " contentValuesДляЗаписиОшибки " +contentValuesДляЗаписиОшибки);
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e( context.getClass().getName(), "SubClassErrors ДЛЯ GATT SERVER error " + e +
                    " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber()+ " date " +new Date().toGMTString());
        }

    }





}
