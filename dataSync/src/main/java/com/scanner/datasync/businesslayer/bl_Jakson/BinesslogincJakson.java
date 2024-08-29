package com.scanner.datasync.businesslayer.bl_Jakson;


import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.scanner.datasync.businesslayer.Errors.SubClassErrors;


import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Action;

@Module
@InstallIn(SingletonComponent.class)
public class BinesslogincJakson {
    Context context;
    public @Inject BinesslogincJakson(@ApplicationContext Context hiltcontext) {
        // TODO: 22.08.2024
        // TODO: 21.08.2024
        context = hiltcontext;
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }



    public JsonNode   callJaksonDataSyncService(@NonNull long version,
                                          @NonNull ObjectMapper getHiltJaksonObjectMapper,
                                          @NonNull InputStream inputStreamJaksonByteScanner) {
        // TODO: 28.08.2024
      AtomicReference<JsonNode>  jsonNodeScannerBLE=new AtomicReference<>();
        Completable completable=   Completable.fromAction(()->{
// TODO: 28.08.2024
            final JsonParser jsonParserScanner= getHiltJaksonObjectMapper.createParser(inputStreamJaksonByteScanner);
            if (  !jsonParserScanner.isClosed()) {
                // TODO: 29.08.2024
                jsonNodeScannerBLE.set(jsonParserScanner.readValueAsTree());
                // TODO: 31.07.2024
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                        + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n"+
                         " jsonNodeScannerBLE " +jsonNodeScannerBLE.get());
            }

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

        }).doOnComplete(()->{
            // TODO: 31.07.2024
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                    + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");


        });
        // TODO: 28.08.2024

        if (inputStreamJaksonByteScanner!=null) {
            completable.blockingSubscribe();
        }

        // TODO: 31.07.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");
        // TODO: 28.08.2024
        return  jsonNodeScannerBLE.get();
    }




// TODO: 28.08.2024



    public long updateOperaticallContentResolver(@NonNull long version, @NonNull JsonNode jsonNodeParentMAP){
        // TODO: 28.08.2024
       AtomicReference<Integer>  updateResult=new AtomicReference<>();
        // TODO: 28.08.2024
      Completable.fromAction(()->{
// TODO: 28.08.2024
          String  SQlOperUpdate=  " UPDATE  listMacMastersSous SET     name=?,macadress=?,  plot=?," +
                  " date_update=?,user_update=? , current_table=?,uuid=?      WHERE  uuid=?  ;";
          // TODO: 28.08.2024
          String  SQlOperInsert=  " REPLACE INTO listMacMastersSous VALUES(?,?,?,?,?,?,?,? ); ;";
          // TODO: 28.08.2024
          Integer getStatementResult =setSqliteStatement(  version,   jsonNodeParentMAP,    SQlOperUpdate,SQlOperInsert);
          // TODO: 04.07.2023  UPDARE Organization
          updateResult.set(getStatementResult);
                  // TODO: 31.07.2024
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                    + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

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

        }).doOnComplete(()->{
            // TODO: 31.07.2024
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                    + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");
        }).blockingSubscribe();
        // TODO: 28.08.2024
        return updateResult.get();
    }

// TODO: 28.08.2024


    @SuppressLint("NewApi")
    Integer setSqliteStatement(@NonNull long version, @NonNull JsonNode jsonNodeParentMAP, @NonNull    String SQlOperUpdate,@NonNull String  SQlOperInsert){
        // TODO: 28.08.2024
        AtomicReference<Integer>  getStatementResult=new AtomicReference<>();
        // TODO: 28.08.2024
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                // TODO: 28.08.2024
                Uri uri = Uri.parse("content://com.sous.scanner.prodider/" +"listMacMastersSous" + "");
                ContentResolver resolver = context.getContentResolver();
                Bundle bundleScannerBLEOtServerJBoss=new Bundle();
                bundleScannerBLEOtServerJBoss.putSerializable("jsonNodeParentMAP", (Serializable) jsonNodeParentMAP);
                bundleScannerBLEOtServerJBoss.putSerializable("sqlupdate", SQlOperUpdate);
                bundleScannerBLEOtServerJBoss.putSerializable("sqlinsert", SQlOperInsert);
                bundleScannerBLEOtServerJBoss.putSerializable("nametable", "listMacMastersSous");
                // TODO: 28.08.2024  
                ContentValues contentValues = new ContentValues();

               // Uri    insertData=   resolver.insert(uri, contentValues,bundleScannerBLEOtServerJBoss);
                Bundle insertAndupdateData=   resolver.call(uri,SQlOperUpdate, SQlOperInsert,bundleScannerBLEOtServerJBoss);
                boolean refreshAndupdateData=   resolver.refresh(uri, bundleScannerBLEOtServerJBoss,new CancellationSignal());
                // TODO: 28.08.2024
                getStatementResult.set(Optional.ofNullable(insertAndupdateData.toString().replaceAll("content://","")).stream().mapToInt(m->Integer.parseInt(m)).findAny().orElse(0));

                // TODO: 31.07.2024
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                        + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n"+
                        " getStatementResult.get() "+ getStatementResult.get());
            }
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

        }).doOnComplete(()->{
            // TODO: 31.07.2024
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                    + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");
        }).blockingSubscribe();
        // TODO: 28.08.2024
        return getStatementResult.get();
    }


// TODO: 28.08.2024 END CLASS

}
