package com.sous.scanner.businesslayer.bl_BroadcastReciver;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

public class Businesslogic_GattReflection {

    private  Context context;
    private  Long version;

    public Businesslogic_GattReflection(Context context, Long version) {
        this.context = context;

        this.version = version;
    }


    public void pairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase()+"\n" );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase()+"\n" );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshDeviceCache(BluetoothGatt gatt) {
        try {
            Method localMethod = gatt.getClass().getMethod("refresh");
            if(localMethod != null) {
                localMethod.invoke(gatt);
            }
        } catch(Exception localException) {
            Log.d("Exception", localException.toString());
        }
    }

    @SuppressLint("MissingPermission")
    public   void error133CloseingGatt(BluetoothGatt gatt ) {
        gatt.disconnect();
        gatt.close();

    }

}


