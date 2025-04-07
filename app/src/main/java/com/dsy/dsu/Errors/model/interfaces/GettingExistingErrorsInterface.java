package com.dsy.dsu.Errors.model.interfaces;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.sous.backasync.launch.ModuleQuety;

public interface GettingExistingErrorsInterface {
    String fileName = "Sous-Avtodor-ERROR.txt";
    String patchFileName="SousAvtoFile";
    StringBuffer gettingExistingErrors(@NonNull Context context, ModuleQuety moduleQuety, SQLiteDatabase sqLiteDatabase_error);
}
