package com.sous.scanner.businesslayer.ContentProdiders;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;


import com.fasterxml.jackson.databind.JsonNode;
import com.scanner.datasync.businesslayer.bl_ContentProviders.BinesslogicContentProvider;
import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.datalayer.local.CREATE_DATABASEScanner;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;


public class ContentProviderScanner extends ContentProvider {
    private   UriMatcher uriMatcherДЛяПровайдераКонтентБазаДанных;
    private SQLiteDatabase Create_Database_СамаБАзаSQLite;
    private AsyncTaskLoader<?> asyncTaskLoader;
    private Handler handler;
    private Integer ТекущаяСтрокаПриДОбавлениииURL=0;
    private     Long  version=0l;

    private BinesslogicContentProvider binesslogicContentProvider;

    public @Inject ContentProviderScanner()   {

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

            Create_Database_СамаБАзаSQLite=new CREATE_DATABASEScanner(getContext()).getССылкаНаСозданнуюБазу();

            binesslogicContentProvider=new BinesslogicContentProvider(getContext(), version);


            if (Create_Database_СамаБАзаSQLite!=null) {
                // TODO: 22.08.2024
                uriMatcherДЛяПровайдераКонтентБазаДанных=new UriMatcher(1);
                uriMatcherДЛяПровайдераКонтентБазаДанных.addURI("com.sous.scanner.prodider","errordsu1",0);
                uriMatcherДЛяПровайдераКонтентБазаДанных.addURI("com.sous.scanner.prodider","listMacMastersSous",1);

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +"    Flowable.fromAction(new Action() { "
                        +   new Date().toLocaleString());
            }

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +"    Flowable.fromAction(new Action() { "
                    +   new Date().toLocaleString() + " Create_Database_СамаБАзаSQLite " +Create_Database_СамаБАзаSQLite);
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
        return true;
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
        //return super.call(method, arg, extras);
        Bundle insertAndupdateData=new Bundle();
        // TODO: 28.08.2024
        try {
            Uri uri = Uri.parse("content://com.sous.scanner.prodider/" +"listMacMastersSous" + "");
            // TODO: 28.08.2024  Запись UPDATE
            Integer resultUpdate=   binesslogicContentProvider.  workerForUpdateContentProvider(uri, extras,Create_Database_СамаБАзаSQLite,version);

            // TODO: 28.08.2024  Запись неосредвствено в базу из Провайдера
            if (resultUpdate==0) {
                // TODO: 28.08.2024  Запись INSERT
                resultUpdate=   binesslogicContentProvider.  workerForInsertContentProvider(uri, extras,Create_Database_СамаБАзаSQLite,version);
            }

            // TODO: 30.10.2021
            getContext().getContentResolver().notifyChange(uri, null);
            // TODO: 28.08.2024


            insertAndupdateData.putSerializable("resultUpdateOrInsert",resultUpdate);

            Log.d(this.getClass().getName(), "\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + "resultUpdate"  +resultUpdate);


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
                    table = "listMacMastersSous";
                    break;

            }
            Log.d(this.getClass().getName(), " table"+ table);
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
        return table;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        Uri  ОтветВставкиДанных = null;
        try {
            if (!Create_Database_СамаБАзаSQLite.inTransaction()) {
                Create_Database_СамаБАзаSQLite.beginTransaction();
            }
            Log.d(this.getClass().getName(), " uri"+uri );
            // TODO: 14.10.2022 метод определения текущней таблицы
            String table = МетодОпределяемТаблицу(uri);

            Long   РезультатВставкиДанныхСканирование  = Create_Database_СамаБАзаSQLite.insert(table, null, values);
            // TODO: 30.10.2021
            Log.w(getContext().getClass().getName(), " РезультатВставкиДанных  " + РезультатВставкиДанныхСканирование);/////

            ОтветВставкиДанных  = Uri.parse("content://"+РезультатВставкиДанныхСканирование.toString());
            if (РезультатВставкиДанныхСканирование> 0) {
                if (Create_Database_СамаБАзаSQLite.inTransaction()) {
                    Create_Database_СамаБАзаSQLite.setTransactionSuccessful();
                    // TODO: 22.09.2022 увеличивает версию данных
                }
            }
            if (Create_Database_СамаБАзаSQLite.inTransaction()) {
                Create_Database_СамаБАзаSQLite.endTransaction();
            }
            // TODO: 30.10.2021
            getContext().getContentResolver().notifyChange(uri, null);
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
        return ОтветВставкиДанных;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values, @Nullable Bundle extras) {
        // TODO: Implement this to handle requests to insert a new row.
        return super.insert(uri, values, extras);
    }







    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Integer РезультатСменыСтатусаВыбраногоМатериала=0;
        try{
            if (!Create_Database_СамаБАзаSQLite.inTransaction()) {
                Create_Database_СамаБАзаSQLite.beginTransaction();
            }
            Log.d(this.getClass().getName(), " uri"+uri );
            // TODO: 14.10.2022 метод определения текущней таблицы
            String table = МетодОпределяемТаблицу(uri);
            if (table!=null) {
                Integer РезультатУдаления  = Create_Database_СамаБАзаSQLite.update(table,values, selection, selectionArgs);
                // TODO: 30.10.2021
                Log.w(getContext().getClass().getName(), " РезультатУдаления  " + РезультатУдаления);/////
                Uri ОтветВставкиДанных  = Uri.parse("content://"+РезультатУдаления.toString());
                String ответОперцииВставки=    Optional.ofNullable(ОтветВставкиДанных).map(Emmeter->Emmeter.toString().replace("content://","")).get();
                РезультатСменыСтатусаВыбраногоМатериала= Integer.parseInt(ответОперцииВставки);
                if (РезультатУдаления> 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
            }else {
                Log.w(getContext().getClass().getName(), " table  " + table);/////
            }
            if (Create_Database_СамаБАзаSQLite.inTransaction()) {
                Create_Database_СамаБАзаSQLite.setTransactionSuccessful();
            }
            if (Create_Database_СамаБАзаSQLite.inTransaction()) {
                Create_Database_СамаБАзаSQLite.endTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("НазваниеОбрабоатываемойТаблицы","ErrorDSU1");
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return РезультатСменыСтатусаВыбраногоМатериала;
    }


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

    @Override
    public boolean refresh(Uri uri, @Nullable Bundle extras, @Nullable CancellationSignal cancellationSignal) {
        //return super.refresh(uri, extras, cancellationSignal);
        //return super.call(method, arg, extras);
        Boolean insertAndupdateData=false;
        try {
            // TODO: 28.08.2024
            AtomicReference<Uri> ОтветВставкиДанных = new AtomicReference<>();
            // TODO: 28.08.2024  Запись UPDATE
            Integer resultUpdate=   binesslogicContentProvider.  workerForUpdateContentProvider(uri, extras,Create_Database_СамаБАзаSQLite,version);

            // TODO: 28.08.2024  Запись неосредвствено в базу из Провайдера
            if (resultUpdate==0) {
                // TODO: 28.08.2024  Запись INSERT
                resultUpdate=   binesslogicContentProvider.  workerForInsertContentProvider(uri, extras,Create_Database_СамаБАзаSQLite,version);
                // TODO: 28.08.2024
                insertAndupdateData=true;
            }
            // TODO: 30.10.2021
            getContext().getContentResolver().notifyChange(ОтветВставкиДанных.get(), null);
            // TODO: 28.08.2024
            ОтветВставкиДанных.set( Uri.parse("content://"+resultUpdate.toString()));


            Log.d(this.getClass().getName(), "\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + "resultUpdate"  +resultUpdate + " insertAndupdateData " +insertAndupdateData);


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
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

