package com.sous.backasync.operationsprovider.GetQuery.intarface;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;

import androidx.annotation.NonNull;

public interface LoaderBackSyncInterface {


      final String getNameProvider = "com.sous.backasync.provider";


    Cursor getBackasyncCursor(@NonNull Bundle bundle) throws SQLException ;

}
