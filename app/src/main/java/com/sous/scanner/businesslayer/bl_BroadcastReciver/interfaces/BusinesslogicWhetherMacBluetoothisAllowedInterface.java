package com.sous.scanner.businesslayer.bl_BroadcastReciver.interfaces;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public interface BusinesslogicWhetherMacBluetoothisAllowedInterface {
    // TODO: 30.10.2024

    Boolean weAreLookingforwhethermacbluetoothisallowed(@NonNull String externalBluetoothDevice, @NonNull SharedPreferences preferences);
}
