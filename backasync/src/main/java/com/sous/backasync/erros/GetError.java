package com.sous.backasync.erros;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

public class GetError {

    private Context context;

    private SQLiteDatabase getsqLiteDatabase;

    public GetError(Context context, SQLiteDatabase getsqLiteDatabase) {
        this.context = context;
        this.getsqLiteDatabase = getsqLiteDatabase;
    }




}
