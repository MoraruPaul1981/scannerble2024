package com.sous.backasync.start.interfaces;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ModuleUpdatetingBackAsyncInterface {

    // TODO: 28.01.2025

       final String getNameProvider="com.sous.backasync.provider";


    @SuppressLint("NewApi")
    Integer getModuleUpdate(@NonNull String Таблица, @NonNull  ContentValues contentValuesModuleBack ,  @NonNull String selection,  @NonNull String[] selectionArgs);

    @SuppressLint("NewApi")
    Integer getModuleUpdate(@NonNull String Таблица,@NonNull ContentValues contentValuesModuleBackAsync);
}
