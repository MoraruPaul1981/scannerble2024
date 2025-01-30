package com.sous.backasync.start.interfaces;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;

public interface ModuleQueryBackAsyncInterface {

    // TODO: 28.01.2025

       final String getNameProvider="com.sous.backasync.provider";

    Cursor getModuleQuery(@NonNull Bundle bundleModuleBack);
    // TODO: 28.01.2025
      Cursor getModuleQuery(@NonNull String Таблица,@NonNull String СамЗапрос, @NonNull String []УсловияВыборки);
}
