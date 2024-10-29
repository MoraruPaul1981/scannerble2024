package com.sous.server.businesslayer.bl_reversescallback.interfaces;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;

import org.jetbrains.annotations.NotNull;

public interface GetReversesCallBackINt {
    // TODO: 24.10.2024

      void   getReversesCallBackToAndroid(@NotNull BluetoothDevice bluetoothDeviceAndroid );
}
