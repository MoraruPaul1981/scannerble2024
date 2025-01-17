package com.sous.backasync.startingbackasync.intarfece;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;

public interface StartingBackAsyncIntarface {

      Cursor backasyncQueryandWhere(@NonNull String Таблица, @NonNull  String   СамЗапрос , @NonNull   String[]УсловияЗапроса);

        Cursor backasyncQuery(@NonNull String Таблица, @NonNull  String   СамЗапрос  );

}
