package com.sous.server.businesslayer.bl_Tests;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.ParcelUuid;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import com.sous.server.businesslayer.Errors.SubClassErrors;

import java.util.UUID;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class GetBleAdvertising {
    // TODO: 25.08.2024
    Context context;
    long version;

    public @Inject GetBleAdvertising(@ApplicationContext Context hitcontext) {
        // TODO: 25.08.2024
        context = hitcontext;
        // TODO: 25.08.2024
        PackageInfo pInfo = null;
        try {
            pInfo = hitcontext.getPackageManager().getPackageInfo(hitcontext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        version = pInfo.getLongVersionCode();
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

    }

    @SuppressLint({"NewApi", "MissingPermission"})
    public void staringBleAdvertising(@NonNull BluetoothAdapter bluetoothAdapter) {
        try {




            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

            // TODO: 25.08.2024
            String ANDROID_ID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            bluetoothAdapter.setName(ANDROID_ID);
            // Setting LE advertise
            AdvertiseSettings advertiseSettings = new AdvertiseSettings.Builder()
                    .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
                    .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                    .setTimeout(1000)
                    .setConnectable(true)
                    .build();

            // UUIDs
            UUID getPublicUUIDScan = ParcelUuid.fromString("70000007-0000-1000-8000-00805f9b34fb").getUuid();
            ParcelUuid parcelUuid = new ParcelUuid(getPublicUUIDScan);

            // Setting LE advertise data
            // Setting LE advertise data
            AdvertiseData advertiseData = new AdvertiseData.Builder()
                    .setIncludeDeviceName(true)
                    .setIncludeTxPowerLevel(true)
                    .addServiceUuid(parcelUuid)
                    .build();


            if (bluetoothAdapter!=null) {
                bluetoothAdapter.getBluetoothLeAdvertiser().startAdvertising(advertiseSettings,advertiseData,
                        new AdvertiseCallback() {
                    @Override
                    public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                        super.onStartSuccess(settingsInEffect);
                    }

                    @Override
                    public void onStartFailure(int errorCode) {
                        super.onStartFailure(errorCode);
                    }
                });
            }


        } catch (Exception e) {
             e.printStackTrace();
             Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                     Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                     + Thread.currentThread().getStackTrace()[2].getLineNumber());
             ContentValues valuesЗаписываемОшибки = new ContentValues();
             valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
             valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
             valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
             valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
             final Object ТекущаяВерсияПрограммы = 0;
             Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
             valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
             new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
         }


     }



}





