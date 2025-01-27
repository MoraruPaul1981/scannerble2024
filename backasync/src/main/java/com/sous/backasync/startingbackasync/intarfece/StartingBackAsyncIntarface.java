package com.sous.backasync.startingbackasync.intarfece;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;

public interface StartingBackAsyncIntarface {

      String getNameProvider = "com.sous.backasync.provider";

    Uri uri = Uri.parse("content://" + getNameProvider + "/");

      Cursor backasyncQueryandWhere(@NonNull String Таблица, @NonNull  String   СамЗапрос , @NonNull   String[]УсловияЗапроса);

        Cursor backasyncQuery(@NonNull String Таблица, @NonNull  String   СамЗапрос  );

}
