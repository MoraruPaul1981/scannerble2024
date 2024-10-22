package com.sous.server.businesslayer.BI_presentationlayer.bl_FragmentServerbleRecyclerViewSimpleScan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;

import com.serverscan.datasync.datasync_businesslayer.bl_databases.BusinesslogicGetCursor;
import com.sous.server.businesslayer.BI_presentationlayer.bl_FragmentServerRecyreView.CommunicationviaMacAddress;
import com.sous.server.businesslayer.Errors.SubClassErrors;

import javax.inject.Inject;

public class BinesslogicCommunicationviaMacAddress implements CommunicationviaMacAddress {

private  Context context;
private  Long version;


    @Inject
    public BusinesslogicGetCursor businesslogicGetCursor;

    BinesslogicCommunicationviaMacAddress(@NonNull Context context, @NonNull Long version){
        this.context=context;
        this.version=version;
    }

    @Override
    public String   communicationviamacaddress(@NonNull String macdevice) {
        String fiodevice=new String();
        try{
            BusinesslogicGetCursor businesslogicGetCursor=new BusinesslogicGetCursor(context);

            businesslogicGetCursor.getClass();

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                     " macdevice " +macdevice);
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
    return  fiodevice;

    }
}
