package com.sous.scanner.businesslayer.bl_forServices;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothGatt;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.sous.scanner.businesslayer.Errors.SubClassErrors;

import java.util.Date;

public class Bl_froSetviceBLE {
    Long version;
    Context context;

    public Bl_froSetviceBLE(Long version, Context context) {
        this.version = version;
        this.context = context;
    }


    @SuppressLint("MissingPermission")
    public void disaibleGattServer(@NonNull BluetoothGatt gatt  ) {
        try{
            if (gatt!=null) {
                gatt.disconnect();
                gatt.close();
                Log.i(this.getClass().getName(),  "  " +Thread.currentThread().getStackTrace()[2].getMethodName()+ " время "
                        +new Date().toLocaleString() + " gatt " +gatt);}
            Log.i(this.getClass().getName(), "GATT CLIENT Proccessing from GATT server.SERVER#SousAvtoEXIT " +
                    new Date().toLocaleString() + gatt
                    + " gatt "+gatt);
            //TODO
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
    }

}
