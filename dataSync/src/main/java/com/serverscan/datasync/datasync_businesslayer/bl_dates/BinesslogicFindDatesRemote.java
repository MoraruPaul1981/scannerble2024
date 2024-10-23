package com.serverscan.datasync.datasync_businesslayer.bl_dates;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;


import com.serverscan.datasync.Errors.SubClassErrors;
import com.serverscan.datasync.datasync_businesslayer.bl_dates.interfaces.GetDateIn;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class BinesslogicFindDatesRemote implements GetDateIn {


    Context context;

    String getNameTabkeGet;

    public BinesslogicFindDatesRemote(Context context) {
        this.context = context;
    }


    @Override
    public   String getDates(  @NotNull Long version  ){
        String getDateupdate=null;
        try{
            getNameTabkeGet="date_update";

            ContentResolver contentProviderNewVersion=context.getContentResolver();

            Uri uri = Uri.parse("content://com.sous.servergatt.prodider/gattserverdataversion" );
            Cursor cursorNewVesionGattServer = contentProviderNewVersion.query(uri, null,
                    "  SELECT   *   FROM gattserverdataversion  WHERE "+getNameTabkeGet+"  IS NOT  NULL  ",
                    null,null,null);
            // TODO: 09.09.2024
            if (cursorNewVesionGattServer.getCount()>0){
                cursorNewVesionGattServer.moveToFirst();
            }

            // TODO: 29.08.2024 время
            getDateupdate=new BinesslogicParserDates(context,version).prossecingBremy(cursorNewVesionGattServer);

            Log.d(context.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " getDateupdate " +getDateupdate);
            // TODO: 19.07.2024 closing
            cursorNewVesionGattServer.close();

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e +
                    " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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
        return  getDateupdate;
    }



}
