package com.sous.scanner.businesslayer.Broadcastreceiver;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.sous.scanner.businesslayer.Errors.SubClassErrors;

import java.util.concurrent.atomic.AtomicReference;

public class BroadcastReceiverGattClient extends BroadcastReceiver {

    Long version;
    private AtomicReference<PendingResult> pendingResultAtomicReferenceClient=new AtomicReference<>();
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        try{
            pendingResultAtomicReferenceClient.set(goAsync());

            // TODO: 31.07.2024 Получаем сам девайс
            final   BluetoothDevice     bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            final    PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.getLongVersionCode();
            // TODO: 31.07.2024 рабочий код

            switch (intent.getAction()){
                // TODO: 31.07.2024
                case   BluetoothDevice.ACTION_ACL_CONNECTED :
                case   BluetoothDevice.ACTION_FOUND :
                case   BluetoothDevice.ACTION_UUID :
                case   BluetoothDevice.ACTION_NAME_CHANGED :
                case   BluetoothDevice.ACTION_CLASS_CHANGED :
                    // TODO: 31.07.2024

                    pendingResultAtomicReferenceClient.get();
                    // TODO: 31.07.2024
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            "intent.getAction() "+intent.getAction() + " intent.getAction() " +intent.getAction());
                    break;
                // TODO: 31.07.2024
                // TODO: 31.07.2024
                case   BluetoothDevice.ACTION_ACL_DISCONNECTED :
                    // TODO: 31.07.2024
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            "intent.getAction() "+intent.getAction() + " intent.getAction() " +intent.getAction());
                    break;
                // TODO: 31.07.2024
                // TODO: 31.07.2024
                case  BluetoothDevice.ACTION_BOND_STATE_CHANGED :
                    // TODO: 31.07.2024
                    new bl_BloadcastReceierGatt(context,version).unpairDevice(bluetoothDevice);

                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            "intent.getAction() "+intent.getAction() + " intent.getAction() " +intent.getAction());
                    break;

                // TODO: 31.07.2024
                // TODO: 31.07.2024
                default:{
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            "intent.getAction() "+intent.getAction() + " intent.getAction() " +intent.getAction());
                    break;
                }
            }










            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    "intent.getAction() "+intent.getAction() + " version " +version);













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