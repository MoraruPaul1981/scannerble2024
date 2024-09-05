package com.serverscan.datasync.datalayer.generatorjakson;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.serverscan.datasync.businesslayer.Errors.SubClassErrors;
import com.serverscan.datasync.datalayer.model.ScannerserversuccessEntity;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Module
@InstallIn(SingletonComponent.class)
public class GenerationJaksonJSON {

 private  Context context;

    public @Inject GenerationJaksonJSON(@ApplicationContext Context hitcontext ) {
        // TODO: 25.08.2024
        context = hitcontext;
        // TODO: 25.08.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

    }

    // TODO: 04.09.2024


    public byte[]  genetarorJaksonJSON(@NonNull Context  context,
                                       @NonNull long version,
                                       @NonNull   List<?>  listForJakson,
                                       @NonNull ObjectMapper getHiltJaksonObjectMapper)
            throws ExecutionException, InterruptedException {
        // TODO: 22.08.2024  Коненпт провайдер для зааписив базу данных
        AtomicReference<byte[]> inputStreamJaksonSend = new AtomicReference();
        // TODO: 28.08.2024
        Completable.fromAction(()->{
                    // TODO: 05.09.2024
                    ObjectWriter writer = getHiltJaksonObjectMapper.writerWithDefaultPrettyPrinter();
                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream(2048);

                    SequenceWriter sequenceWriter = writer .writeValues(byteArrayOutputStream).init(true) .writeAll(listForJakson);
                    // TODO: 09.02.2024 fulch
                    sequenceWriter.flush();
                    sequenceWriter.close();
                    inputStreamJaksonSend.set(byteArrayOutputStream.toByteArray());

                    // TODO: 09.02.2024 close
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();


                    Log.d(this.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n"  +
                             " listForJakson " +listForJakson + "inputStreamJaksonSend " +inputStreamJaksonSend);

                }).doOnComplete(()->{


                    Log.d(this.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

                })
                .doOnError(e->{
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                            + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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
        // TODO: 31.07.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

        return inputStreamJaksonSend.get();

    }












// TODO: 05.09.2024
    public List<?> genetarorListFor(@NonNull Context  context, @NonNull long version, @NonNull Cursor cursorlocal)
            throws ExecutionException, InterruptedException {
        // TODO: 22.08.2024  Коненпт провайдер для зааписив базу данных
        final String[] ANDROID_ID = {new String()};
        AtomicReference<List<?>> inputStreamJaksonSend = new AtomicReference();
        // TODO: 28.08.2024
        Completable.fromAction(()->{


                    Flowable.fromArray(cursorlocal.moveToNext()).map(m->{

                        Log.d(this.getClass().getName(), "\n" + " class " +
                                Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n"+m);
                         return m;
                    });

                    // TODO: 23.08.2024
                  ScannerserversuccessEntity scannerserversuccessEntity=new ScannerserversuccessEntity();





                    Log.d(this.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

                }).doOnComplete(()->{

                    // TODO: 31.07.2024
                    if (cursorlocal!=null) {
                        cursorlocal.close();
                    }

                    Log.d(this.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

                })
                .doOnError(e->{
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                            + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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
        // TODO: 31.07.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

        return inputStreamJaksonSend.get();

    }



















}
