package com.sous.server.businesslayer.bl_BloadcastReceiver;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.util.Log;


import java.lang.reflect.Method;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

      public void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
            method.invoke(device, (Object[]) null);

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

}
