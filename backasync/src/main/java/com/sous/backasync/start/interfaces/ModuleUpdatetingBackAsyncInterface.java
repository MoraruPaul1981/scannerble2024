package com.sous.backasync.start.interfaces;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.os.Bundle;

import androidx.annotation.NonNull;

public interface ModuleUpdatetingBackAsyncInterface {

    // TODO: 28.01.2025

       final String getNameProvider="com.sous.backasync.provider";


    @SuppressLint("NewApi")
    Integer getModuleUpdate(@NonNull String Таблица,@NonNull Bundle bundleModuleBack);

    @SuppressLint("NewApi")
    Integer getModuleUpdate(@NonNull String Таблица,@NonNull ContentValues contentValuesModuleBackAsync);
}
