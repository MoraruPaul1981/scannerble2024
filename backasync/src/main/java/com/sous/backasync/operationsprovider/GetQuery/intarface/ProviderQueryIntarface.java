package com.sous.backasync.operationsprovider.GetQuery.intarface;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;

import androidx.annotation.NonNull;

public interface ProviderQueryIntarface {


    Cursor getQuery(@NonNull String table, @NonNull String selection, @NonNull  String[] selectionArgs);

}
