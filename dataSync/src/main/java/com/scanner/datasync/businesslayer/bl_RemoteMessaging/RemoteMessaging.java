package com.scanner.datasync.businesslayer.bl_RemoteMessaging;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;


@Module
@InstallIn(SingletonComponent.class)
public class RemoteMessaging   implements  RemoteMessaпуInterface{
    private SQLiteDatabase Create_Database_СамаБАзаSQLite;
    private Context context;
    private long version;

    public @Inject  RemoteMessaging(@ApplicationContext Context hiltcontext ) {
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }




/*    public  @Inject    RemoteMessaging(SQLiteDatabase create_Database_СамаБАзаSQLite, Context context, long version) {
        Create_Database_СамаБАзаSQLite = create_Database_СамаБАзаSQLite;
        this.context = context;
        this.version = version;
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }*/




// TODO: 14.08.2024
      @Override
    public Integer startingRemoteMessaging() {

        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

        return Create_Database_СамаБАзаSQLite.getVersion();
    }
}




