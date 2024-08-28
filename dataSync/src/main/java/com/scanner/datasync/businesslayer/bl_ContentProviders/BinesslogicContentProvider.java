package com.scanner.datasync.businesslayer.bl_ContentProviders;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.AtomicDouble;
import com.scanner.datasync.businesslayer.Errors.SubClassErrors;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class BinesslogicContentProvider {

    private Context context;
    private  Long vesion;


    public BinesslogicContentProvider(Context context, Long vesion) {
        this.context = context;
        this.vesion = vesion;
    }


    public Integer workerForUpdateContentProvider(@NonNull Uri uri,
                                                @Nullable Bundle extras,
                                                @Nullable    AtomicReference<Uri> ОтветВставкиДанных,
                                                @Nullable SQLiteDatabase Create_Database_СамаБАзаSQLite,
                                                  @Nullable Long version) {


        // TODO: 28.08.2024
       AtomicReference<Integer>  resultUpdate=new AtomicReference<>();
        // TODO: 28.08.2024
        Completable.fromAction(()->{
                    // TODO: 28.08.2024
                    if (!Create_Database_СамаБАзаSQLite.inTransaction()) {
                        Create_Database_СамаБАзаSQLite.beginTransaction();
                    }
                    Log.d(this.getClass().getName(), " uri"+ uri);
                    JsonNode jsonNodeScannerBLE=  (JsonNode)  extras.getSerializable("jsonNodeParentMAP");
                    String SQlOperUpdate= (String) extras.getSerializable("sqlupdate" );
                    String nametable = (String) extras.getSerializable("nametable");

                    if (jsonNodeScannerBLE.size()>0) {
                        // TODO: 28.08.2024
                        Flowable.fromIterable(jsonNodeScannerBLE)
                                .onBackpressureBuffer().blockingForEach(rowJakson->{
                                    // TODO: 31.07.2024
                                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

                                    // TODO: 28.08.2024
                                    SQLiteStatement sqLiteStatementInsert= Create_Database_СамаБАзаSQLite.compileStatement(SQlOperUpdate);
                                    sqLiteStatementInsert.clearBindings();
                                    // TODO: 04.07.2023 цикл данных
                                    sqLiteStatementInsert.bindString(1,rowJakson.get("name").asText().trim());//"id"
                                    sqLiteStatementInsert.bindString(2,rowJakson.get("macadress").asText().trim());//"name"
                                    sqLiteStatementInsert.bindLong(3,rowJakson.get("plot").intValue());//"fullname"
                                    sqLiteStatementInsert.bindString(4,rowJakson.get("date_update").asText().trim());//"date_update"
                                    sqLiteStatementInsert.bindLong(5,rowJakson.get("user_update").intValue());//"user_update"
                                    sqLiteStatementInsert.bindLong(6,rowJakson.get("current_table").longValue());//"current_table"
                                    sqLiteStatementInsert.bindLong(7,rowJakson.get("uuid").longValue());//"uuid"
                                    // TODO: 07.07.2023 ДЛя Состыковки
                                    sqLiteStatementInsert.bindLong(8,rowJakson.get("uuid").longValue());//"uuid уже для UUID"

                                    resultUpdate.set(sqLiteStatementInsert.executeUpdateDelete());

                                    Log.d(this.getClass().getName(), "\n" + " class " +
                                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                            + sqLiteStatementInsert  + "sqLiteStatementInsert" + " resultUpdate.get() " +resultUpdate.get());

                                    ОтветВставкиДанных.set(Uri.parse("content://" + resultUpdate.toString()));
                                    if (resultUpdate.get()> 0) {
                                        if (Create_Database_СамаБАзаSQLite.inTransaction()) {
                                            Create_Database_СамаБАзаSQLite.setTransactionSuccessful();
                                            // TODO: 22.09.2022 увеличивает версию данных
                                        }
                                    }
                                },jsonNodeScannerBLE.size());
                    }


                    if (Create_Database_СамаБАзаSQLite.inTransaction()) {
                        Create_Database_СамаБАзаSQLite.endTransaction();
                    }   // TODO: 31.07.2024


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

                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");


                }).blockingSubscribe();

        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");
        return  resultUpdate.get();
    }





    // TODO: 28.08.2024 Insert
    public Integer workerForInsertContentProvider(@NonNull Uri uri,
                                                  @Nullable Bundle extras,
                                                  @Nullable    AtomicReference<Uri> ОтветВставкиДанных,
                                                  @Nullable SQLiteDatabase Create_Database_СамаБАзаSQLite,
                                                  @Nullable   Long version) {


        // TODO: 28.08.2024
        AtomicReference<Long>  resultInsert=new AtomicReference<>();
        // TODO: 28.08.2024
        Completable.fromAction(()->{
            // TODO: 28.08.2024

            if (!Create_Database_СамаБАзаSQLite.inTransaction()) {
                Create_Database_СамаБАзаSQLite.beginTransaction();
            }
            Log.d(this.getClass().getName(), " uri"+ uri);
            JsonNode jsonNodeScannerBLE=  (JsonNode)  extras.getSerializable("jsonNodeParentMAP");
            String SQlOperInsert= (String) extras.getSerializable("sqlinsert" );
            String nametable = (String) extras.getSerializable("nametable");

            if (jsonNodeScannerBLE.size()>0) {
                // TODO: 28.08.2024
                Flowable.fromIterable(jsonNodeScannerBLE)
                        .onBackpressureBuffer().blockingForEach(rowJakson->{
                            // TODO: 31.07.2024
                            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                                    + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

                            // TODO: 28.08.2024
                            SQLiteStatement sqLiteStatementInsert= Create_Database_СамаБАзаSQLite.compileStatement(SQlOperInsert);
                            sqLiteStatementInsert.clearBindings();
                            // TODO: 04.07.2023 цикл данных
                            sqLiteStatementInsert.bindString(1,rowJakson.get("name").asText().trim());//"id"
                            sqLiteStatementInsert.bindString(2,rowJakson.get("macadress").asText().trim());//"name"
                            sqLiteStatementInsert.bindLong(3,rowJakson.get("plot").intValue());//"fullname"
                            sqLiteStatementInsert.bindString(4,rowJakson.get("date_update").asText().trim());//"date_update"
                            sqLiteStatementInsert.bindLong(5,rowJakson.get("user_update").intValue());//"user_update"
                            sqLiteStatementInsert.bindLong(6,rowJakson.get("current_table").longValue());//"current_table"
                            sqLiteStatementInsert.bindLong(7,rowJakson.get("uuid").longValue());//"uuid"
                            // TODO: 07.07.2023 ДЛя Состыковки
                            sqLiteStatementInsert.bindLong(8,rowJakson.get("uuid").longValue());//"uuid уже для UUID"

                            resultInsert.set(sqLiteStatementInsert.executeInsert());

                            Log.d(this.getClass().getName(), "\n" + " class " +
                                    Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                    + sqLiteStatementInsert  + "sqLiteStatementInsert" + " resultInsert.get() " +resultInsert.get());

                            ОтветВставкиДанных.set(Uri.parse("content://" + resultInsert.toString()));
                            if (resultInsert.get()> 0) {
                                if (Create_Database_СамаБАзаSQLite.inTransaction()) {
                                    Create_Database_СамаБАзаSQLite.setTransactionSuccessful();
                                    // TODO: 22.09.2022 увеличивает версию данных
                                }
                            }
                        },jsonNodeScannerBLE.size());
            }


            if (Create_Database_СамаБАзаSQLite.inTransaction()) {
                Create_Database_СамаБАзаSQLite.endTransaction();
            }   // TODO: 31.07.2024



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
            // TODO: 28.08.2024
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                    + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");
        }).blockingSubscribe();

        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");
        return  resultInsert.get().intValue();
    }




}
