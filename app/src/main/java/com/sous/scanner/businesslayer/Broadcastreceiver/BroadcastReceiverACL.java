package com.sous.scanner.businesslayer.Broadcastreceiver;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.businesslayer.bl_BroadcastReciver.Businesslogic_GattClinetSuccessLocalBroadcastManager;
import com.sous.scanner.businesslayer.bl_BroadcastReciver.Businesslogic_GattReflection;
import com.sous.scanner.businesslayer.bl_LocalBroadcastManagers.BussenloginSaredPreferense;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class BroadcastReceiverACL extends BroadcastReceiver {

    private Long version;
    private AtomicReference<PendingResult> pendingResultAtomicReferenceClient=new AtomicReference<>();

    private   SharedPreferences preferences;

    @SuppressLint({"MissingPermission", "NewApi"})
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        try{
            pendingResultAtomicReferenceClient.set(goAsync());
            // TODO: 13.08.2024
            preferences = PreferenceManager.getDefaultSharedPreferences(context);

            // TODO: 31.07.2024 Получаем сам девайс
            final   BluetoothDevice     bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            final   int       rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
            final   int       key = intent.getShortExtra(BluetoothDevice.EXTRA_PAIRING_KEY,Short.MIN_VALUE);
            final   String     name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
            final   String     transport = intent.getStringExtra(BluetoothDevice.EXTRA_TRANSPORT);
            intent.putExtra(BluetoothDevice.EXTRA_PAIRING_KEY,9977);



            // TODO: 25.08.2024
            final    PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.getLongVersionCode();
            // TODO: 31.07.2024 рабочий код

            switch (intent.getAction()){
                // TODO: 31.07.2024
                case   BluetoothDevice.ACTION_ACL_CONNECTED :
                    // TODO: 02.08.2024
                    // TODO: 07.08.2024  Успешное Событие в нутри BroadCasr Recuver
               Set<String> bluetoothDeviceScanInnersysntem=     preferences.getStringSet(bluetoothDevice.getAddress(), new HashSet<>());
                boolean EmptyDevideCall=    bluetoothDeviceScanInnersysntem.contains(bluetoothDevice.getAddress().toString());

                    if (EmptyDevideCall==true) {
                            // TODO: 13.08.2024
                            new Businesslogic_GattClinetSuccessLocalBroadcastManager(context,version).
                                      successLocalBroadcastManager(intent, bluetoothDevice,  pendingResultAtomicReferenceClient);

                          new Businesslogic_GattReflection(context,version).unpairDevice(bluetoothDevice);

                      new BussenloginSaredPreferense(preferences,context,version).  workerSharedPreferences(bluetoothDevice);

                        }

                    Log.i(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            "intent.getAction() "+intent.getAction() + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase()+"\n"+
                            " bluetoothDevice.getName() " +bluetoothDevice.getName()+"\n"+
                            " bluetoothDeviceScanInnersysntem " +bluetoothDeviceScanInnersysntem
                            +"\n"+
                            " bluetoothDevice.getAddress().toString()) " +bluetoothDevice.getAddress().toString());
                    break;
                // TODO: 31.07.2024
                // TODO: 31.07.2024
                case   BluetoothDevice.ACTION_ACL_DISCONNECTED :
                    // TODO: 31.07.2024
                    new Businesslogic_GattReflection(context,version).unpairDevice(bluetoothDevice);
/*
                    // TODO: 07.08.2024  Успешное Событие в нутри BroadCasr Recuver
              new Businesslogic_GattClinetSuccessLocalBroadcastManager(context,version).
                      successLocalBroadcastManager(intent, bluetoothDevice,  pendingResultAtomicReferenceClient);

                    Log.i(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            "intent.getAction() "+intent.getAction() + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase()+"\n");*/
                    break;
                // TODO: 31.07.2024
                // TODO: 31.07.2024
                case  BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED :
                    // TODO: 31.07.2024
                    new Businesslogic_GattReflection(context,version).unpairDevice(bluetoothDevice);
                    // TODO: 07.08.2024  Успешное Событие в нутри BroadCasr Recuver
               /*     // TODO: 07.08.2024  Успешное Событие в нутри BroadCasr Recuver
                    new Businesslogic_GattClinetSuccessLocalBroadcastManager(context,version).
                            successLocalBroadcastManager(intent, bluetoothDevice,  pendingResultAtomicReferenceClient);

                    Log.i(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            "intent.getAction() "+intent.getAction() + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase()+"\n");*/
                    break;
                // TODO: 31.07.2024

                default:{
                    Log.i(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            "intent.getAction() "+intent.getAction() + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase()+"\n");
                    break;
                }
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



}