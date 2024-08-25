package com.sous.server.businesslayer.bl_Tests;


import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.TransportDiscoveryData;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.ParcelUuid;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.sous.server.businesslayer.Errors.SubClassErrors;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class GetTest {
    // TODO: 25.08.2024
    Context context;
    long version;

    public @Inject GetTest(@ApplicationContext Context hitcontext) {
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

    @SuppressLint("NewApi")
    public void startingTest(@NonNull BluetoothAdapter bluetoothAdapter) {
        try {


            BluetoothDevice device =
                    bluetoothAdapter.getRemoteDevice("70:5F:A3:C4:D2:6C");

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

            // TODO: 25.08.2024
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }


            // Setting LE advertise
            AdvertiseSettings advertiseSettings = new AdvertiseSettings.Builder()
                    .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
                    .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                    .setTimeout(0)
                    .setConnectable(true)
                    .build();

            // UUIDs
            UUID getPublicUUIDScan = ParcelUuid.fromString("70000007-0000-1000-8000-00805f9b34fb").getUuid();
            ParcelUuid parcelUuid = new ParcelUuid(getPublicUUIDScan);

            // Setting LE advertise data
            AdvertiseData advertiseData = new AdvertiseData.Builder()
                    .setIncludeDeviceName(true)
                    .setIncludeTxPowerLevel(true)
                    .build();
            // Setting LE advertise data
            AdvertiseData scanResponse = new AdvertiseData.Builder()
                    .build();

            bluetoothAdapter.getBluetoothLeAdvertiser().startAdvertising(advertiseSettings, advertiseData,scanResponse,
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





