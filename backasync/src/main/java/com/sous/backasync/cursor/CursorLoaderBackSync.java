package com.sous.backasync.cursor;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.loader.content.CursorLoader;

public class CursorLoaderBackSync {

    Context context;

    private final String getNameProvider = "com.sous.backasync.provider";

    public CursorLoaderBackSync(Context context) {//ModuleBackAsync
        this.context = context;
    }

    public Cursor getBackasyncCursor(@NonNull Bundle bundle) throws SQLException {
        Cursor getBackasyncCursor = null;
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
            getBackasyncCursor = cursorLoader.loadInBackground();
            if (getBackasyncCursor.getCount() > 0 && getBackasyncCursor != null) {
                getBackasyncCursor.moveToFirst();
                Log.d(this.getClass().getName(), "cursor.getCount() "
                        + getBackasyncCursor.getCount());
            }
            cursorLoader.commitContentChanged();
       String s=     cursorLoader.dataToString(getBackasyncCursor);

            // TODO: 17.04.2023
            Log.d(this.getClass().getName(), "\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " cursorLoader " + getBackasyncCursor);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());


        }
        return getBackasyncCursor;

    }

}
