package com.sous.backasync.cursor;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.loader.content.CursorLoader;

public class BackasyncCursor {

    Context context;

    private final String getNameProvider = "com.sous.backasync.provider";

    public BackasyncCursor(Context context) {
        this.context = context;
    }

    public Cursor getBackasyncCursor(@NonNull Bundle bundle) throws SQLException {
        Cursor cursor = null;
        CursorLoader cursorLoader = null;
        try {
            cursorLoader = new CursorLoader(context);
            String Таблица = bundle.getString("Таблица");
            String Selection = bundle.getString("СамЗапрос");
            String[] SelectionArgs = bundle.getStringArray("УсловияЗапроса");


            Uri uri = Uri.parse("content://" + getNameProvider + "/" + Таблица + "");
            cursorLoader.setUri(uri);
            cursorLoader.setSelection(Selection);
            cursorLoader.setSelectionArgs(SelectionArgs);//МесяцПростоАнализа
            cursor = cursorLoader.loadInBackground();
            if (cursor.getCount() > 0 && cursor != null) {
                cursor.moveToFirst();
                Log.d(this.getClass().getName(), "cursor.getCount() "
                        + cursor.getCount());
            }
            cursorLoader.commitContentChanged();

            // TODO: 17.04.2023
            Log.d(this.getClass().getName(), "\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " cursorLoader " + cursorLoader);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());


        }
        return cursor;

    }

}
