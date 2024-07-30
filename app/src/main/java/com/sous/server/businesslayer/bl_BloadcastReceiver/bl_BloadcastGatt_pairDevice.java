package com.sous.server.businesslayer.bl_BloadcastReceiver;

import android.bluetooth.BluetoothDevice;
import android.content.Context;


import java.lang.reflect.Method;

public class bl_BloadcastGatt_pairDevice {

    private  Context context;


    private  Long version;

    public bl_BloadcastGatt_pairDevice(Context context, Long version) {
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


}
