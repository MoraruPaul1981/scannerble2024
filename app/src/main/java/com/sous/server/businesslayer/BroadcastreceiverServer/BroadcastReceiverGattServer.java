package com.sous.server.businesslayer.BroadcastreceiverServer;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.bl_BloadcastReceiver.Bl_BloadcastGatt_getDeviceClentGatt;

import java.util.concurrent.atomic.AtomicReference;


public class BroadcastReceiverGattServer extends BroadcastReceiver {
    // TODO: 30.07.2024
    private  Long   version;
    private    AtomicReference<PendingResult> pendingResultAtomicReference=new AtomicReference<>();
    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        try{
            // TODO: 30.07.2024
            pendingResultAtomicReference.set(goAsync());


            final    PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
               version = pInfo.getLongVersionCode();

            final Bl_BloadcastGatt_getDeviceClentGatt blBloadcastGattGetDeviceClentGatt=  new Bl_BloadcastGatt_getDeviceClentGatt(context,version);

            blBloadcastGattGetDeviceClentGatt.startingGetDeviceBLECkient(  intent ,   pendingResultAtomicReference);
            // TODO: 30.07.2024


            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    "intent.getAction() "+intent.getAction() + " version " +version);

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







    // TODO: 29.07.2024 end class
}