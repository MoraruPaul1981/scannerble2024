package com.serverscan.datasync.datasync_businesslayer.bl_Jakson.parsejsonfromserver;


import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.serverscan.datasync.datasync_businesslayer.Errors.SubClassErrors;
import com.serverscan.datasync.datasync_businesslayer.bl_Jakson.interfaces.WtiringJaksonJSONInterface;
import com.serverscan.datasync.datasync_businesslayer.bl_Jakson.model.CompleteallmacadressusersEntityDeserial;
import com.serverscan.datasync.datasync_businesslayer.bl_dates.BinesslogicParserDates;
import com.serverscan.datasync.datasync_businesslayer.bl_Jakson.model.ScannerserversuccessEntitySerial;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
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
import io.reactivex.rxjava3.core.Single;

@Module
@InstallIn(SingletonComponent.class)
public class WtiringJaksonJSON  implements WtiringJaksonJSONInterface {

   private  Context context;
    private  long version;
    private ObjectMapper objectMapperGet;


    public @Inject WtiringJaksonJSON(@NotNull  @ApplicationContext Context hitcontext, @NotNull  long version,@NotNull ObjectMapper objectMapperGet) {
        // TODO: 25.08.2024
        this.context = hitcontext;
        this.version = version;
        this.objectMapperGet = objectMapperGet;
        // TODO: 25.08.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " objectMapperGet  " +objectMapperGet);

    }

    // TODO: 04.09.2024

  @Override
    public  CopyOnWriteArrayList<CompleteallmacadressusersEntityDeserial> converttoJacksonObject(@NotNull byte[] bytesGetOtJBoss) {
        // TODO: 22.08.2024  Коненпт провайдер для зааписив базу данных
        AtomicReference<CopyOnWriteArrayList<CompleteallmacadressusersEntityDeserial> > completeallmacadressusersEntityDeserials = new AtomicReference();
        // TODO: 28.08.2024
       Completable.fromAction(()->{
                    // TODO: 05.09.2024
                /*    final JsonParser jsonParserServerGattGet= objectMapperGet.createParser(bytesGetOtJBoss);

                    jsonNodeAtomicReferenceGattGet.set(jsonParserServerGattGet.readValueAsTree());*/


            completeallmacadressusersEntityDeserials.set(objectMapperGet.readValue(bytesGetOtJBoss,
                    new TypeReference<CopyOnWriteArrayList<CompleteallmacadressusersEntityDeserial>>() {
                          @Override
                          public Type getType() {
                              return super.getType();
                          }
                      }));

                    Log.d(this.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n"
                            + "completeallmacadressusersEntityDeserials " +completeallmacadressusersEntityDeserials);

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

        return completeallmacadressusersEntityDeserials.get();

    }












// TODO: 05.09.2024
@Override
public Integer readListJacksonObject(  @NotNull CopyOnWriteArrayList<CompleteallmacadressusersEntityDeserial> completeallmacadressusersEntityDeserials) {
        // TODO: 22.08.2024  Коненпт провайдер для зааписив базу данных
    // TODO: 06.09.2024
        // TODO: 28.08.2024
    Single<Integer> integerSingleNewMacAdress=    Single.fromCallable(()->{
                    // TODO: 22.10.2024
       String NameTableMac="completeallmacadressusers";
                    // TODO: 06.09.2024
            String  SQloperations=  " REPLACE INTO "+NameTableMac+"  (  fio,mac,date_update, current_table , uuid  )  VALUES  (?,?,? ,?,? )";
         // TODO: 28.08.2024
          Uri uri = Uri.parse("content://com.sous.servergatt.prodider/"+NameTableMac+"" );
            ContentResolver resolver = context.getContentResolver();
            Bundle bundleCompleteallmacadress=new Bundle();
                    bundleCompleteallmacadress.putSerializable("completeallmac", (Serializable) completeallmacadressusersEntityDeserials);
                    bundleCompleteallmacadress.putSerializable("sql", SQloperations);
            // TODO: 28.08.2024  Само выполение операции
            Bundle insertAndupdateData=   resolver.call(uri,SQloperations, SQloperations,bundleCompleteallmacadress);
               Integer resultInsert  = (Integer) insertAndupdateData.getSerializable("resultUpdateOrInsert");



                        Log.d(this.getClass().getName(), "\n" + " class " +
                                Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n"+ "resultInsert " +resultInsert);
                        return resultInsert;
                }).doOnSuccess(getsuccess->{
                    // TODO: 06.09.2024

                    Log.d(this.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n"+
                            " getsuccess " +getsuccess);

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

                }) ;
        // TODO: 31.07.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

        return integerSingleNewMacAdress.blockingGet();

    }











// TODO: 22.10.2024 end class 
}
