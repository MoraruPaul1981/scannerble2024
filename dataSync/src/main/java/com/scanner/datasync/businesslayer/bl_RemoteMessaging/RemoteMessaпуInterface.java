package com.scanner.datasync.businesslayer.bl_RemoteMessaging;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

public interface RemoteMessaпуInterface {
    Integer startingRemoteMessaging(@NonNull SQLiteDatabase Create_Database_СамаБАзаSQLite, @NonNull  Long version );

   void startingServicedataSync(@NonNull Context context, @NonNull  Long version );

}


