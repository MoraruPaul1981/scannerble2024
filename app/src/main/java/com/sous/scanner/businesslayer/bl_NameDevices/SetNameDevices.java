package com.sous.scanner.businesslayer.bl_NameDevices;




import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.AdvertisingSetParameters;
import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.os.ParcelUuid;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;


import com.sous.scanner.businesslayer.Errors.SubClassErrors;

import java.util.UUID;

public class SetNameDevices {

    // TODO: 26.09.2024
    private Context context;
    private  Long version;

    public SetNameDevices(Context context, Long version) {
        this.context = context;
        this.version = version;
    }


    public String setingNameDevice( ) {
        String setingNameDevice = new String();
        try {
            String ANDROID_ID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            String ANDROID_NAME=Settings.Global.getString(context.getContentResolver(),Settings.Global.DEVICE_NAME);
            String btMac = android.provider.Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
            // TODO: 01.09.2024
            String model=  android.os.Build.MODEL;
            String company=     android.os.Build.MANUFACTURER;
            String product=  Build.BRAND;

            setingNameDevice=company+" "+product+" "+model;


            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " setingNameDevice " +setingNameDevice);
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

        return  setingNameDevice;
    }


}
