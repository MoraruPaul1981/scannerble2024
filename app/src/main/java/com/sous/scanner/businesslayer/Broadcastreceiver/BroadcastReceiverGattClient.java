package com.sous.scanner.businesslayer.Broadcastreceiver;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.sous.scanner.businesslayer.Errors.SubClassErrors;

public class BroadcastReceiverGattClient extends BroadcastReceiver {

    private bl_BloadcastReceierGatt blBloadcastReceierGatt;
    Long version;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        try{
            BluetoothDevice bluetoothDevice = null;
            // TODO: 22.07.2024  Код Брадкаста ресивера
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
               version = pInfo.getLongVersionCode();
            if (intent.getAction().equals("android.bluetooth.device.action.PAIRING_REQUEST") ||
                    intent.getAction().equals("android.bluetooth.device.action.PAIRING_CANCEL") ) {
                bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            /*    blBloadcastReceierGatt = new bl_BloadcastReceierGatt(context, version);
                blBloadcastReceierGatt.unpairDevice(bluetoothDevice);*/

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        "Bintent.getAction() "+intent.getAction() + " bluetoothDevice " +bluetoothDevice);

            }else {
                bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        "Bintent.getAction() "+intent.getAction() + " bluetoothDevice " +bluetoothDevice);
            }

        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                "Bintent.getAction() "+intent.getAction() + " bluetoothDevice " +bluetoothDevice);

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