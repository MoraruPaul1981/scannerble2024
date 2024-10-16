package com.serverscan.datasync.datasync_businesslayer.bl_contentproviders;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.serverscan.datasync.datasync_businesslayer.Errors.SubClassErrors;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.core.Completable;

public class BinesslogicContentProvider {

    private Context context;
    private  Long vesion;


    public BinesslogicContentProvider(Context context, Long vesion) {
        this.context = context;
        this.vesion = vesion;
    }


    public Integer workerForInsertContentProvider(@NonNull String SQlOperUpdate,
                                                  @Nullable ContentValues  contentValues,
                                                  @Nullable SQLiteDatabase Create_Database_СамаБАзаSQLite,
                                                  @Nullable Long version) {


        // TODO: 28.08.2024
        AtomicReference<Integer>  resultInsert=new AtomicReference<>(0);
        // TODO: 28.08.2024
        Completable.fromAction(()->{
                    // TODO: 28.08.2024
                    if (!Create_Database_СамаБАзаSQLite.inTransaction()) {
                        Create_Database_СамаБАзаSQLite.beginTransaction();
                    }

                    if (contentValues.size()>0) {

                        // TODO: 31.07.2024
                        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

                        // TODO: 28.08.2024
                        SQLiteStatement sqLiteStatementInsert= Create_Database_СамаБАзаSQLite.compileStatement(SQlOperUpdate);
                        sqLiteStatementInsert.clearBindings();
                        // TODO: 04.07.2023 цикл данных
                        sqLiteStatementInsert.bindNull(1 );//"id"
                        sqLiteStatementInsert.bindNull(2);//"uuid уже для UUID"
                        sqLiteStatementInsert.bindLong(3,contentValues.getAsLong("current_table"));//"uuid уже для UUID"
                        sqLiteStatementInsert.bindString(4,contentValues.getAsString("date_update"));//"uuid уже для UUID"

                        // TODO: 28.08.2024
                        resultInsert.set(sqLiteStatementInsert.executeUpdateDelete());
                        // TODO: 28.08.2024
                        Log.d(this.getClass().getName(), "\n" + " class " +
                                Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                + sqLiteStatementInsert  + "sqLiteStatementInsert" + " resultInsert.get() " +resultInsert.get());




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


                })
                .doOnComplete(()->{
// TODO: 28.08.2024
                    if (resultInsert.get()> 0) {
                        if (Create_Database_СамаБАзаSQLite.inTransaction()) {
                            Create_Database_СамаБАзаSQLite.setTransactionSuccessful();
                            // TODO: 22.09.2022 увеличивает версию данных
                        }
                    }
                    if (Create_Database_СамаБАзаSQLite.inTransaction()) {
                        Create_Database_СамаБАзаSQLite.endTransaction();
                    }   // TODO: 31.07.2024

                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");


                }).blockingSubscribe();

        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");
        return  resultInsert.get();
    }





    // TODO: 28.08.2024 Insert
    public Integer workerForUpdateContentProvider(@NonNull String SqlUpdate,
                                                   @Nullable ContentValues  contentValues,
                                                  @Nullable SQLiteDatabase Create_Database_СамаБАзаSQLite,
                                                  @Nullable Long version) {


        // TODO: 28.08.2024
        AtomicReference<Integer>  resultUpdate=new AtomicReference<>(0);
        // TODO: 28.08.2024
        Completable.fromAction(()->{
                    // TODO: 28.08.2024
                    if (!Create_Database_СамаБАзаSQLite.inTransaction()) {
                        Create_Database_СамаБАзаSQLite.beginTransaction();
                    }

                    if (contentValues.size()>0) {

                        // TODO: 31.07.2024
                        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

                        // TODO: 28.08.2024
                        SQLiteStatement sqLiteStatementInsert= Create_Database_СамаБАзаSQLite.compileStatement(SqlUpdate);
                        sqLiteStatementInsert.clearBindings();
                        // TODO: 04.07.2023 цикл данных
                        sqLiteStatementInsert.bindLong(1,contentValues.getAsLong("versionremote"));//"uuid уже для UUID"
                        sqLiteStatementInsert.bindString(2,contentValues.getAsString("date_update"));//"uuid уже для UUID"
                        // TODO: 07.07.2023 ДЛя Состыковки
                        sqLiteStatementInsert.bindLong(3,contentValues.getAsLong("id"));//"uuid уже для UUID"
                        // TODO: 28.08.2024
                        resultUpdate.set(sqLiteStatementInsert.executeUpdateDelete());
                        // TODO: 28.08.2024
                        Log.d(this.getClass().getName(), "\n" + " class " +
                                Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                + sqLiteStatementInsert  + "sqLiteStatementInsert" + " resultUpdate.get() " +resultUpdate.get());
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


                })
                .doOnComplete(()->{
// TODO: 28.08.2024
                    if (resultUpdate.get()> 0) {
                        if (Create_Database_СамаБАзаSQLite.inTransaction()) {
                            Create_Database_СамаБАзаSQLite.setTransactionSuccessful();
                            // TODO: 22.09.2022 увеличивает версию данных
                        }
                    }
                    if (Create_Database_СамаБАзаSQLite.inTransaction()) {
                        Create_Database_СамаБАзаSQLite.endTransaction();
                    }   // TODO: 31.07.2024

                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");


                }).blockingSubscribe();


        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");
        return  resultUpdate.get() ;
    }




}
