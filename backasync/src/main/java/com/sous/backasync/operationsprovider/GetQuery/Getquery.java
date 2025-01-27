package com.sous.backasync.operationsprovider.GetQuery;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

public class Getquery {


    private Context context;

    private SQLiteDatabase getsqLiteDatabase;


    public Getquery(Context context, SQLiteDatabase getsqLiteDatabase) {
        this.context = context;
        this.getsqLiteDatabase = getsqLiteDatabase;
    }


    public Cursor getQuery(@NonNull String table, @NonNull String selection, @NonNull  String[] selectionArgs){
        Cursor cursor = null;
        try{
            cursor=     getsqLiteDatabase.rawQuery(selection,selectionArgs);

            Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " cursor " + cursor);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return  cursor;
    }







}
