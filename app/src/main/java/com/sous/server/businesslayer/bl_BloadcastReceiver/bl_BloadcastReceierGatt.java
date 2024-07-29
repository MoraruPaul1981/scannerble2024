package com.sous.server.businesslayer.bl_BloadcastReceiver;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;


import com.sous.server.businesslayer.Errors.SubClassErrors;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class bl_BloadcastReceierGatt {

    private  Context context;


    private  Long version;

    public bl_BloadcastReceierGatt(Context context,   Long version) {
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
