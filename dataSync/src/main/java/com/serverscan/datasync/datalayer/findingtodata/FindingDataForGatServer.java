package com.serverscan.datasync.datalayer.findingtodata;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.serverscan.datasync.businesslayer.Errors.SubClassErrors;

import java.util.Date;

import io.reactivex.rxjava3.annotations.NonNull;

public class FindingDataForGatServer {

    Context context;

            Long version;

    public FindingDataForGatServer(Context context, Long version) {
        this.context = context;
        this.version = version;
    }


    // TODO: 10.02.2023 МЕТОД ВЫБОР ДАННЫХ
    public  Long findVersonGattServer(@NonNull String СамЗапрос, @NonNull String nametable) {
        Long   ВерсияДАнных = 0l;
        try{
            Uri uri = Uri.parse("content://com.sous.servergatt.prodider/"+nametable+"" );
             ContentResolver contentProvider=context.getContentResolver();
            Cursor cursorПолучаемДЛяСевреа = contentProvider.query(uri, null, СамЗапрос, null,null,null);
            if (cursorПолучаемДЛяСевреа.getCount()>0) {
                cursorПолучаемДЛяСевреа.moveToFirst();
                ВерсияДАнных = cursorПолучаемДЛяСевреа.getLong(0);
                Log.i(this.getClass().getName(), "ВерсияДАнных" + ВерсияДАнных);
                // TODO: 10.09.2024  уеличиваем на 1 ++
            }
            Log.w(context.getClass().getName(), " РЕЗУЛЬТАТ insertData  cursorПолучаемДЛяСевреа  " +  cursorПолучаемДЛяСевреа.toString() );
            cursorПолучаемДЛяСевреа.close();
            Log.i(this.getClass().getName(),  "  " +Thread.currentThread().getStackTrace()[2].getMethodName()+ " время " +new Date().toLocaleString() );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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
        }
        return  ВерсияДАнных;
    }


    // TODO: 12.09.2024  END CLASS
}
