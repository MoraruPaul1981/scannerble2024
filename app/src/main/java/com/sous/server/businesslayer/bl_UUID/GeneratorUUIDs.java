package com.sous.server.businesslayer.bl_UUID;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.sous.server.businesslayer.ContentProvoders.ContentProviderServer;
import com.sous.server.businesslayer.Errors.SubClassErrors;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import io.reactivex.rxjava3.core.Completable;

public class GeneratorUUIDs {


    private Context context;

    private long version;




    @androidx.annotation.NonNull
    public Long МетодГенерацииUUID( ) {
        Long getUUID = 0l;
        try{

            LocalDateTime futureDate = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm.ss.SSS");
            String uuid=   dtf.format(futureDate) ;
            String finaluuid=   uuid.replaceAll("[^0-9]","");
            // uuid = uuid.replaceAll("^[a-zA-Z]", "");
            //uuid= CharMatcher.any().replaceFrom("[A-Za-z0-9]", "");
            BigInteger result = new BigInteger(finaluuid);
            getUUID = result.longValue();
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " uuid " + uuid);

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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки );
        }
        return getUUID;
    }





}
