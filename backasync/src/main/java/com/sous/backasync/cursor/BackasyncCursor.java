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

    private  final String getNameProvider="com.sous.backasync.provider";

    public Cursor getBackasyncCursor(@NonNull Context context, @NonNull Bundle bundle) throws SQLException {
        Cursor cursor=null;
        CursorLoader cursorLoader=null;
        try{
            cursorLoader=new CursorLoader(context);
            String[] SelectionArgs=      bundle.getStringArray("УсловияВыборки");
            String  Selection=      bundle.getString("СамЗапрос");
            String  Таблица=      bundle.getString("Таблица");
            Uri uri = Uri.parse("content://"+getNameProvider+"/" + Таблица + "");
            cursorLoader.setUri(uri);
            cursorLoader.setSelection(Selection);
            cursorLoader.setSelectionArgs(SelectionArgs);//МесяцПростоАнализа
            cursor=    cursorLoader.loadInBackground();
            if (cursor.getCount() > 0 && cursor!=null) {
                cursor.moveToFirst();
                Log.d(this.getClass().getName(), "cursor.getCount() "
                        + cursor.getCount());
                cursorLoader.reset();
            }
        } catch ( Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }finally {
            cursorLoader.commitContentChanged();
        }
        return  cursor;
    }


}
