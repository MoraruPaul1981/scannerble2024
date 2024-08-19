package com.sous.scanner.datalayer.bl_DataBase;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.sous.scanner.businesslayer.Errors.SubClassErrors;

public class BusinesslogicDatabase {

    // TODO: 19.08.2024
private Context context;
private  Long version;

    public BusinesslogicDatabase(Context context, Long version) {
        this.context = context;
        this.version = version;
    }


    public  Cursor getingCursor(@NonNull String СамЗапрос) {
        AsyncTaskLoader<Cursor> asyncTaskLoaderMacAdressGAtt=null;
        try{
        asyncTaskLoaderMacAdressGAtt=new AsyncTaskLoader(context) {
            @Nullable
            @Override
            public Object loadInBackground() {

                Uri uri = Uri.parse("content://com.sous.scanner.prodider/errordsu1" );
                ContentResolver resolver = context. getContentResolver();
                Cursor    cursor = resolver.query(uri, null, СамЗапрос, null,null,null);
                if (cursor.getCount()>0) {
                    cursor.moveToFirst();
                }
                Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + "cursor " +cursor);

                return cursor;
            }
        };
            asyncTaskLoaderMacAdressGAtt.forceLoad();

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + "asyncTaskLoaderMacAdressGAtt.isStarted() " +asyncTaskLoaderMacAdressGAtt.isStarted());
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return   asyncTaskLoaderMacAdressGAtt.loadInBackground();
    }




}
