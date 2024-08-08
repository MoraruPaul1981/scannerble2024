package com.sous.server.businesslayer.Errors;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.sous.server.businesslayer.ContentProvoders.ContentProviderServer;

import java.util.Date;
import java.util.Optional;

public class SubClassErrors {
    Context context;
    public SubClassErrors(@NonNull Context context) {
        this.context =context;
    }
    public  void МетодЗаписиОшибок(@NonNull   ContentValues contentValuesДляЗаписиОшибки) {
        try {
            Log.i( context.getClass().getName(), "contentValuesДляЗаписиОшибки  " + contentValuesДляЗаписиОшибки);

            Uri uri = Uri.parse("content://com.sous.server.providerserver/errordsu1" );
            ContentResolver resolver = context. getContentResolver();
        Uri    insertData=   resolver.insert(uri, contentValuesДляЗаписиОшибки);
            Integer РезультатВставки= Optional.ofNullable(insertData.toString().replaceAll("content://","")).map(Integer::new).orElse(0);
            // TODO: 08.08.2024
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +"ERROR ERROR ERROR GATT SERVER  !!!!!!"
                    +"\n"+ " РезультатВставки " +РезультатВставки);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e( context.getClass().getName(), "SubClassErrors ДЛЯ SCANNER error " + e +
                    " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber()+ " date " +new Date().toGMTString());
        }

    }



    public  void МетодЗаписиОшибокИзServerGatt(@NonNull   ContentValues contentValuesДляЗаписиОшибки, ContentProviderServer contentProviderServer) {
        try {
            Log.i( context.getClass().getName(), "contentValuesДляЗаписиОшибки  " + contentValuesДляЗаписиОшибки);
            Uri uri = Uri.parse("content://com.sous.server.providerserver/errordsu1" );
            Uri    insertData=   contentProviderServer.insert(uri, contentValuesДляЗаписиОшибки);
            Log.w(context.getClass().getName(), " РЕЗУЛЬТАТ РезультатВставки ОШИБКА " +  insertData +
                    " contentValuesДляЗаписиОшибки  " +contentValuesДляЗаписиОшибки);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e( context.getClass().getName(), "SubClassErrors ДЛЯ SCANNER error " + e +
                    " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber()+ " date " +new Date().toGMTString());
        }

    }



}
