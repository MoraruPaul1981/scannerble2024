package com.serverscan.datasync.datasync_businesslayer.bl_contentproviders;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.serverscan.datasync.datasync_businesslayer.bl_Jakson.model.CompleteallmacadressusersEntityDeserial;
import com.serverscan.datasync.datasync_businesslayer.bl_dates.BinesslogicParserDates;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class ContentProviderCompleteallmacadress {
    private Context context;
    private  Long vesion;


    public ContentProviderCompleteallmacadress(Context context, Long vesion) {
        this.context = context;
        this.vesion = vesion;
    }



    public Integer workerForInsertCompleteallmacadress(@NonNull Bundle extras,
                                                       @NotNull SQLiteDatabase Create_Database_СамаБАзаSQLite,
                                                       @NotNull Long version,
                                                       @NotNull String Sqloperations) {


        // TODO: 28.08.2024
        AtomicInteger longAtomicReferenceInsertNewMac=new AtomicInteger();
        // TODO: 28.08.2024
        Completable.fromAction(()->{
                    // TODO: 28.08.2024
                    if (!Create_Database_СамаБАзаSQLite.inTransaction()) {
                        Create_Database_СамаБАзаSQLite.beginTransaction();
                    }

                    if (extras.size()>0) {

                        CopyOnWriteArrayList<CompleteallmacadressusersEntityDeserial> completeallmacadressusersEntityDeserials
                                = (CopyOnWriteArrayList<CompleteallmacadressusersEntityDeserial>) extras.getSerializable("completeallmac" );


                        Flowable.fromIterable(completeallmacadressusersEntityDeserials)
                                .onBackpressureBuffer()
                                .blockingForEach(row->{


                            // TODO: 31.07.2024
                            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                                    + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

                            // TODO: 28.08.2024
                            SQLiteStatement sqLiteStatementInsert= Create_Database_СамаБАзаSQLite.compileStatement(Sqloperations);
                            sqLiteStatementInsert.clearBindings();
                            // TODO: 04.07.2023 цикл данных
                                    if (row.getFio()!=null) {
                                        sqLiteStatementInsert.bindString(1,row.getFio());//"uuid уже для UUID"
                                    } else {
                                        sqLiteStatementInsert.bindNull(1 );//"id"
                                    }
                                    if (row.getMac()!=null) {
                                        sqLiteStatementInsert.bindString(2,row.getMac());//"uuid уже для UUID"
                                    } else {
                                        sqLiteStatementInsert.bindNull(2);//"id"
                                    }
                                    if (row.getDateUpdate()!=null) {
                                        // TODO: 22.10.2024

                                String getDateUpdate=  new BinesslogicParserDates(context,version).prossecingDateToString(row.getDateUpdate());
                                        sqLiteStatementInsert.bindString(3,getDateUpdate);//"uuid уже для UUID"
                                    } else {
                                        sqLiteStatementInsert.bindNull(3);//"id"
                                    }
                                    if (row.getCurrentTable()!=null) {
                                        sqLiteStatementInsert.bindLong(4,row.getCurrentTable().longValue());//"uuid уже для UUID"
                                    } else {
                                        sqLiteStatementInsert.bindNull(4);//"id"
                                    }
                                    if (row.getUuid()!=null) {
                                        sqLiteStatementInsert.bindLong(5,row.getUuid().longValue());//"uuid уже для UUID"
                                    } else {
                                        sqLiteStatementInsert.bindNull(5);//"id"
                                    }

                                    // TODO: 28.08.2024
                                  long insertNewMacAdress=   sqLiteStatementInsert.executeInsert() ;
                                    if(insertNewMacAdress>0){
                                        // TODO: 22.10.2024
                                        longAtomicReferenceInsertNewMac.incrementAndGet();
                                    }
                                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n" +
                                             " longAtomicReferenceInsertNewMac.get() " +longAtomicReferenceInsertNewMac.get());

                        });
                        // TODO: 28.08.2024
                        Log.d(this.getClass().getName(), "\n" + " class " +
                                Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                + " longAtomicReferenceInsertNewMac.get() " +longAtomicReferenceInsertNewMac.get());
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
                    new com.serverscan.datasync.datasync_businesslayer.Errors.SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);


                })
                .doOnComplete(()->{
// TODO: 28.08.2024
                    if (longAtomicReferenceInsertNewMac.get()> 0) {
                            Create_Database_СамаБАзаSQLite.setTransactionSuccessful();
                            // TODO: 22.09.2022 увеличивает версию данных
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
        return  longAtomicReferenceInsertNewMac.get() ;
    }








    // TODO: 22.10.2024 end class
}
