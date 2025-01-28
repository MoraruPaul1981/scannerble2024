package com.sous.backasync.operationsprovider.GetQuery;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.sous.backasync.operationsprovider.GetQuery.intarface.ProviderQueryIntarface;

public class ProviderQuery implements ProviderQueryIntarface {


    private Context context;

    private SQLiteDatabase getsqLiteDatabase;


    public ProviderQuery(Context context, SQLiteDatabase getsqLiteDatabase) {
        this.context = context;
        this.getsqLiteDatabase = getsqLiteDatabase;
    }




    @Override
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
