package com.sous.backasync.cursor;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.loader.content.CursorLoader;

import com.sous.backasync.cursor.intarface.LoaderBackSyncInterface;

public class LoaderBackSync implements LoaderBackSyncInterface {

    Context context;



    public LoaderBackSync(Context context) {//StartingModuleBackAsync
        this.context = context;
    }

    @Override
    public Cursor getBackasyncCursor(@NonNull Bundle bundle)   {
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
            cursorLoader.forceLoad() ;
            getBackasyncCursor = cursorLoader.loadInBackground();
            // TODO: 17.01.2025
            if (getBackasyncCursor != null) {
                if (getBackasyncCursor.getCount() > 0) {
                    getBackasyncCursor.moveToFirst();
                    // TODO: 17.04.2023
                    Log.d(this.getClass().getName(), "\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName()
                            + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " cursorLoader " + getBackasyncCursor);
                }
            }
            cursorLoader.commitContentChanged();
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
