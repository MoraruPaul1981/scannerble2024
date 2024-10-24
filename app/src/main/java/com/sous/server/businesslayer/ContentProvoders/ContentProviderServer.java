package com.sous.server.businesslayer.ContentProvoders;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;



import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.datalayer.data.CREATE_DATABASEServerScanner;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Action;


public class ContentProviderServer extends android.content.ContentProvider {
    private   UriMatcher uriMatcherДЛяПровайдераКонтентБазаДанных;
    private SQLiteDatabase Create_Database_СамаБАзаSQLite;
    private AsyncTaskLoader<?> asyncTaskLoader;
    private Handler handler;
    private Integer ТекущаяСтрокаПриДОбавлениииURL=0;
    private  CopyOnWriteArrayList<String> ИменаТаблицыОтАндройда;
    private Long version=0l;
    public ContentProviderServer() throws InterruptedException {
        try{
            ИменаТаблицыОтАндройда=new CopyOnWriteArrayList<>();
            ИменаТаблицыОтАндройда.add("errordsu1");
            ИменаТаблицыОтАндройда.add("scannerserversuccess");
            ИменаТаблицыОтАндройда.add("scannerlistdevices");
            Log.d(this.getClass().getName(),  " ContentProviderServer" +uriMatcherДЛяПровайдераКонтентБазаДанных );
            Log.d(this.getClass().getName(), " ИменаТаблицыОтАндройда "+ИменаТаблицыОтАндройда );
            uriMatcherДЛяПровайдераКонтентБазаДанных=new UriMatcher(ИменаТаблицыОтАндройда.size());
            ИменаТаблицыОтАндройда.forEach(new Consumer<String>() {
                @Override
                public void accept(String ЭлементТаблица) {
                    uriMatcherДЛяПровайдераКонтентБазаДанных.addURI("com.sous.server.providerserver",ЭлементТаблица.toString(),ТекущаяСтрокаПриДОбавлениииURL);

                    Log.d(this.getClass().getName(), " ЭлементТаблица "+ЭлементТаблица + " ТекущаяСтрокаПриДОбавлениииURL " +ТекущаяСтрокаПриДОбавлениииURL);
                    ТекущаяСтрокаПриДОбавлениииURL++;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы =version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }
    @Override
    public boolean onCreate() {
        try{
            if (Create_Database_СамаБАзаSQLite==null) {
                Log.w(this.getClass().getName(), "Create_Database_СамаБАзаSQLite " + Create_Database_СамаБАзаSQLite);
                Create_Database_СамаБАзаSQLite=new CREATE_DATABASEServerScanner(getContext()).getССылкаНаСозданнуюБазу();
                Log.w(this.getClass().getName(), "Create_Database_СамаБАзаSQLite " + Create_Database_СамаБАзаSQLite + " getContext()) " +getContext());
            }
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            version = pInfo.getLongVersionCode();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы =version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        if (Create_Database_СамаБАзаSQLite!=null) {
            return true;
        } else {
            return false;
        }

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor = null;
        try{
            if (!Create_Database_СамаБАзаSQLite.inTransaction()) {
                Create_Database_СамаБАзаSQLite.beginTransaction();
            }
            Log.d(this.getClass().getName(), " uri"+uri );
            cursor = Create_Database_СамаБАзаSQLite.rawQuery(s, null);
            cursor.moveToFirst();
            if (Create_Database_СамаБАзаSQLite.inTransaction()) {
                Create_Database_СамаБАзаSQLite.endTransaction();
            }
            Log.d(this.getClass().getName(), " cursor"+cursor );
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            version = pInfo.getLongVersionCode();
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        ContentValues valuesЗаписываемОшибки=new ContentValues();
        valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
        valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
        valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
        valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
        final Object ТекущаяВерсияПрограммы =version;
        Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
        valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
        new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }
        return cursor;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable Bundle queryArgs, @Nullable CancellationSignal cancellationSignal) {
        return super.query(uri, projection, queryArgs, cancellationSignal);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        Uri geturiInsert = null;
        try {
            if (!Create_Database_СамаБАзаSQLite.inTransaction()) {
                Create_Database_СамаБАзаSQLite.beginTransaction();
            }
            Log.d(this.getClass().getName(), " uri"+uri );
            // TODO: 14.10.2022 метод определения текущней таблицы
            String table = МетодОпределяемТаблицу(uri);

            Long   РезультатВставкиДанныхСканирование  = Create_Database_СамаБАзаSQLite.insert(table, null, contentValues);
            // TODO: 30.10.2021
            Log.w(getContext().getClass().getName(), " РезультатВставкиДанных  " + РезультатВставкиДанныхСканирование);/////
            if (РезультатВставкиДанныхСканирование> 0) {
                if (Create_Database_СамаБАзаSQLite.inTransaction()) {
                    Create_Database_СамаБАзаSQLite.setTransactionSuccessful();
                    // TODO: 22.09.2022 увеличивает версию данных
                }
                geturiInsert=Uri.parse(РезультатВставкиДанныхСканирование.toString());
            }
            if (Create_Database_СамаБАзаSQLite.inTransaction()) {
                Create_Database_СамаБАзаSQLite.endTransaction();
            }

            Log.d(getContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " geturiInsert  " +geturiInsert);

            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            version = pInfo.getLongVersionCode();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы =version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return geturiInsert;
    }


    @NonNull
    private String МетодОпределяемТаблицу(Uri uri) {
        String table = new String();
        try{
            Log.d(this.getClass().getName(), " uri"+ uri);
            switch (uriMatcherДЛяПровайдераКонтентБазаДанных.match(uri)) {
                case 0:
                    table = "errordsu1";
                    break;
                case 1:
                    table = "scannerserversuccess";
                    break;
                default:
                    table=    Optional.ofNullable(uri).map(Emmeter->Emmeter.toString().replace("content://com.dsy.dsu.providerdatabase/","")).get();
                    Log.w(getContext().getClass().getName(),
                            " defaluit table  " + table  + " uri " + uri);/////
                    break;
            }
            Log.d(this.getClass().getName(), " table"+ table);
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            version = pInfo.getLongVersionCode();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы =version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return table;
    }


    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        // TODO: Implement this to handle requests to insert a new row.
        final Integer[] ОтветВставкиДанных = {0};
        try {
            if (!Create_Database_СамаБАзаSQLite.inTransaction()) {
                Create_Database_СамаБАзаSQLite.beginTransaction();
            }
            Log.d(this.getClass().getName(), " uri"+uri );
            // TODO: 14.10.2022 метод определения текущней таблицы
            String table = МетодОпределяемТаблицу(uri);

            Flowable.fromArray(values)
                    .onBackpressureBuffer(true)
                    .buffer(50)
                    .doOnNext(new io.reactivex.rxjava3.functions.Consumer<List<ContentValues>>() {
                        @Override
                        public void accept(List<ContentValues> contentValues) throws Throwable {
                            Flowable.fromIterable(contentValues)
                                    .onBackpressureBuffer(true)
                                    .doOnNext(new io.reactivex.rxjava3.functions.Consumer<ContentValues>() {
                                        @Override
                                        public void accept(ContentValues contentValues) throws Throwable {
                                            Long   РезультатВставкиДанныхСканирование  = Create_Database_СамаБАзаSQLite.insert(table, null, contentValues);
                                            // TODO: 30.10.2021
                                            Log.w(getContext().getClass().getName(), " РезультатВставкиДанных  " + РезультатВставкиДанныхСканирование);/////
                                            ОтветВставкиДанных[0] =РезультатВставкиДанныхСканирование.intValue();
                                            if (РезультатВставкиДанныхСканирование> 0) {
                                                if (Create_Database_СамаБАзаSQLite.inTransaction()) {
                                                    Create_Database_СамаБАзаSQLite.setTransactionSuccessful();
                                                    // TODO: 22.09.2022 увеличивает версию данных
                                                }
                                            }
                                            if (Create_Database_СамаБАзаSQLite.inTransaction()) {
                                                Create_Database_СамаБАзаSQLite.endTransaction();
                                            }
                                        }
                                    })
                                    .doOnError(new io.reactivex.rxjava3.functions.Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Throwable {
                                            Log.e(this.getClass().getName(), "Ошибка " + throwable + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
                                            ContentValues valuesЗаписываемОшибки=new ContentValues();
                                            valuesЗаписываемОшибки.put("Error",throwable.toString().toLowerCase());
                                            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
                                            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
                                            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
                                            final Object ТекущаяВерсияПрограммы =version;
                                            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
                                            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
                                            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                                        }
                                    })
                                    .doOnComplete(new Action() {
                                        @Override
                                        public void run() throws Throwable {
                                            // TODO: 30.10.2021
                                            getContext().getContentResolver().notifyChange(uri, null);
                                            Log.w(getContext().getClass().getName(), " doOnComplete   " );/////
                                        }
                                    })
                                    .blockingSubscribe();
                        }
                    }).blockingSubscribe();
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            version = pInfo.getLongVersionCode();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы =version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return ОтветВставкиДанных[0];
    }



    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }


}

