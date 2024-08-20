package com.scanner.datasync.businesslayer.bl_UUIds;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.scanner.datasync.Errors.SubClassErrors;

import java.math.BigInteger;
import java.util.UUID;

public class GeneratorUUIDs {
    private Context context;

    private long version;




    @androidx.annotation.NonNull
    public Long МетодГенерацииUUID( ) {
        Long getUUID = 0l;
        try {

            UUID uuid = UUID.randomUUID();

            //uuid   .toString().replaceAll("-", "").replaceAll("[a-zA-Z]", "").substring(0, 20);
            ///uuid = uuid.replaceAll("[a-zA-Z]", "");
            //uuid= CharMatcher.any().replaceFrom("[A-Za-z0-9]", "");
            Long fff2 = uuid.getLeastSignificantBits();
            Long fff3 = uuid.getMostSignificantBits();
            BigInteger bigInteger = BigInteger.valueOf(fff3).abs();
            getUUID = bigInteger.longValue();
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
            return getUUID;
        }
        return  getUUID;
    }


}
