package com.sous.server.businesslayer.ContentProvoders;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.serverscan.datasync.datasync_businesslayer.bl_contentproviders.BinesslogicContentProvider;
import com.serverscan.datasync.datasync_businesslayer.bl_contentproviders.ContentProviderCompleteallmacadress;
import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.datalayer.intrefaces.DatabaseIntreface;
import com.sous.server.datalayer.local.CREATE_DATABASEServerScanner;
import com.sous.server.datalayer.local.module.GetDataBaceHilt;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.EntryPoint;
import dagger.hilt.EntryPoints;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Action;


public class ContentProviderServer extends android.content.ContentProvider {
    private   UriMatcher uriMatcherGattServer;
    private SQLiteDatabase Create_Database_СамаБАзаSQLite;

    private Long version=0l;
    private ContentProviderCompleteallmacadress contentProviderCompleteallmacadress;

    private  BinesslogicContentProvider binesslogicContentProvider;



    GetDataBaceHilt getDataBaceHilt;
    public    ContentProviderServer() {
        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +"    Flowable.fromAction(new Action() { "
                +   new Date().toLocaleString());
    }






    @Override
    public boolean onCreate() {
        try{
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            version = pInfo.getLongVersionCode();

            Create_Database_СамаБАзаSQLite=    new GetDataBaceHilt(getContext()).getHiltDataBase(version);
            //Create_Database_СамаБАзаSQLite= new CREATE_DATABASEServerScanner(getContext()).getССылкаНаСозданнуюБазу();
           // Create_Database_СамаБАзаSQLite=      EntryPoints.get(getContext(), DatabaseIntreface.class).getHiltDataBase();
            contentProviderCompleteallmacadress=new ContentProviderCompleteallmacadress(getContext(), version);
            binesslogicContentProvider=new BinesslogicContentProvider(getContext(), version);

                // TODO: 22.08.2024
                uriMatcherGattServer =new UriMatcher(2);
                // TODO: 04.09.2024
                uriMatcherGattServer.addURI("com.sous.servergatt.prodider","errordsu1",0);
                uriMatcherGattServer.addURI("com.sous.servergatt.prodider","scannerserversuccess",1);
                uriMatcherGattServer.addURI("com.sous.servergatt.prodider","gattserverdataversion",2);
                uriMatcherGattServer.addURI("com.sous.servergatt.prodider","completeallmacadressusers",3);

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +"    Flowable.fromAction(new Action() { "
                    +   new Date().toLocaleString());
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


    @Override
    public void attachInfo(Context context, ProviderInfo info) {
        super.attachInfo(context, info);
        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +"    Flowable.fromAction(new Action() { "
                +   new Date().toLocaleString());
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        Bundle insertAndupdateData=new Bundle();
        try {
            // TODO: 28.08.2024  Запись UPDATE
                // TODO: 28.08.2024  Запись INSERT
              Integer  resultInsertGattAllMac=   contentProviderCompleteallmacadress.
                      workerForInsertCompleteallmacadress( extras,Create_Database_СамаБАзаSQLite,version);

            // TODO: 30.10.2021
            insertAndupdateData.putSerializable("resultUpdateOrInsert",resultInsertGattAllMac);

            Log.d(this.getClass().getName(), "\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + "insertAndupdateData"  +insertAndupdateData);


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return insertAndupdateData;

        //return super.call(method, arg, extras);
    }


    @NonNull
    private String МетодОпределяемТаблицу(@NonNull Uri uri) {
        String table = new String();
        try{
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            version = pInfo.getLongVersionCode();
            Log.d(this.getClass().getName(), " uri"+ uri);
            switch (uriMatcherGattServer.match(uri)) {
                case 0:
                    table = "errordsu1";
                    break;
                case 1:
                    table = "scannerserversuccess";
                    break;
                case 2:
                    table = "gattserverdataversion";
                    break;
            }
            Log.i(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " table"+ table+" uri"+ uri);
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

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable Bundle extras) {
        int geturiUpdate = 0;
        try {

            Log.d(this.getClass().getName(), " uri"+uri );
            // TODO: 14.10.2022 метод определения текущней таблицы
            String table = МетодОпределяемТаблицу(uri);

            String  SQlOperUpdate=   extras.getString("sql");

           // geturiUpdate=  binesslogicContentProvider.workerForInsertContentProvider(SQlOperUpdate,values,Create_Database_СамаБАзаSQLite,version);
            geturiUpdate=  binesslogicContentProvider.workerForUpdateContentProvider(SQlOperUpdate,values,Create_Database_СамаБАзаSQLite,version);


            Log.d(getContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " geturiUpdate  " +geturiUpdate);

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
        return geturiUpdate;
        //return super.update(uri, values, extras);
    }


}

