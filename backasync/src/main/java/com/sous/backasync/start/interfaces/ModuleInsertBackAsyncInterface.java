package com.sous.backasync.start.interfaces;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;

public interface ModuleInsertBackAsyncInterface {

    // TODO: 28.01.2025

       final String getNameProvider="com.sous.backasync.provider";

    Integer getModuleInsert(@NonNull Bundle bundleModuleBack);
    // TODO: 28.01.2025
    Integer getModuleInsert(@NonNull String Таблица,@NonNull ContentValues contentValuesModuleBackAsync);
}
