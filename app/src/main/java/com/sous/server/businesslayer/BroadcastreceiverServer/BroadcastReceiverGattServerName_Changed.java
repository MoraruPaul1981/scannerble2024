package com.sous.server.businesslayer.BroadcastreceiverServer;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.provider.Settings;
import android.util.Log;

import com.sous.server.businesslayer.BI_Services.BuccesloginForServiceServerScan;
import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.bl_BloadcastReceiver.Bl_BloadcastGatt_getDeviceClentGatt;
import com.sous.server.businesslayer.bl_BloadcastReceiver.Businesslogic_GattReflection;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;


public class BroadcastReceiverGattServerName_Changed extends BroadcastReceiver {
    // TODO: 30.07.2024
    private  Long   version;
    private    AtomicReference<PendingResult> pendingResultAtomicReferenceServer=new AtomicReference<>();

    private SharedPreferences preferencesGatt;
    @SuppressLint({"MissingPermission", "NewApi"})
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        try{
            // TODO: 30.07.2024
            pendingResultAtomicReferenceServer.set(goAsync());

            preferencesGatt =context. getSharedPreferences("MyPrefs", MODE_PRIVATE);

            String ANDROID_ID= Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            // TODO: 31.07.2024 Получаем сам девайс
         final   BluetoothDevice     bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            final   int       rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
            final   String     name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
            final   String     transport = intent.getStringExtra(BluetoothDevice.EXTRA_TRANSPORT);
            intent.putExtra(BluetoothDevice.EXTRA_PAIRING_KEY,555);


            final    PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.getLongVersionCode();
            // TODO: 31.07.2024 рабочий код

            switch (intent.getAction()){
                // TODO: 31.07.2024
                case   BluetoothDevice.ACTION_NAME_CHANGED :
                case   BluetoothDevice.ACTION_CLASS_CHANGED :
                    // TODO: 31.07.2024
                    final Bl_BloadcastGatt_getDeviceClentGatt blBloadcastGattGetDeviceClentGatt=  new Bl_BloadcastGatt_getDeviceClentGatt(context,version);
                    blBloadcastGattGetDeviceClentGatt.startingGetDeviceBLECkient(  intent ,   pendingResultAtomicReferenceServer,bluetoothDevice,preferencesGatt);


                    new Businesslogic_GattReflection(context,version).unpairDevice(bluetoothDevice);

                    // TODO: 31.07.2024
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            "intent.getAction() "+intent.getAction() + " intent.getAction() " +intent.getAction());
                    break;
                // TODO: 07.08.2024

                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    // TODO: 26.08.2024
                    BuccesloginForServiceServerScan startServiceGattServer=  new BuccesloginForServiceServerScan(context);

                    startServiceGattServer. enadleBroadcastManager(version,intent);



                    // TODO: 31.07.2024
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            "intent.getAction() "+intent.getAction() + " intent.getAction() " +intent.getAction());
                    break;

            }

            Log.i(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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