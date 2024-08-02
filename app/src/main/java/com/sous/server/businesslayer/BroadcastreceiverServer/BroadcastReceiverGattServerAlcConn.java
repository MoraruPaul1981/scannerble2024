package com.sous.server.businesslayer.BroadcastreceiverServer;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.ParcelUuid;
import android.util.Log;

import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.bl_BloadcastReceiver.Bl_BloadcastGatt_getDeviceClentGatt;

import java.util.concurrent.atomic.AtomicReference;


public class BroadcastReceiverGattServerAlcConn extends BroadcastReceiver {
    // TODO: 30.07.2024
    private  Long   version;
    private    AtomicReference<PendingResult> pendingResultAtomicReferenceServer=new AtomicReference<>();
    @SuppressLint({"MissingPermission", "NewApi"})
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        try{
            // TODO: 30.07.2024
            pendingResultAtomicReferenceServer.set(goAsync());

            // TODO: 31.07.2024 Получаем сам девайс
         final   BluetoothDevice     bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
         final   String     bluetoothDeviceNAMe = intent.getParcelableExtra(BluetoothDevice.EXTRA_NAME);
         final   Long     bluetoothDeviceRSSI = intent.getLongExtra(BluetoothDevice.EXTRA_RSSI,0);
           final   Long     bluetoothDeviceUUID = intent.getLongExtra(BluetoothDevice.EXTRA_UUID,0);

            final    PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.getLongVersionCode();
            // TODO: 31.07.2024 рабочий код

            switch (intent.getAction()){
                // TODO: 31.07.2024
                case   BluetoothDevice.ACTION_ACL_CONNECTED :
                    // TODO: 31.07.2024

                    final Bl_BloadcastGatt_getDeviceClentGatt blBloadcastGattGetDeviceClentGatt=  new Bl_BloadcastGatt_getDeviceClentGatt(context,version);

                    blBloadcastGattGetDeviceClentGatt.startingGetDeviceBLECkient(  intent ,   pendingResultAtomicReferenceServer,bluetoothDevice);
                    // TODO: 30.07.2024

                    ParcelUuid[] uuidlist=         bluetoothDevice.getUuids();

                    // TODO: 31.07.2024
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            "intent.getAction() "+intent.getAction() + " intent.getAction() " +intent.getAction()+
                            "  uuidlist " +  uuidlist);
                    break;

            }

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