package com.serverscan.datasync.businesslayer;

import android.database.sqlite.SQLiteDatabase;

import androidx.work.multiprocess.RemoteWorkManager;

import java.util.concurrent.ConcurrentSkipListSet;

public class RemoteMessaging {

    private SQLiteDatabase Create_Database_СамаБАзаSQLite;
    public ConcurrentSkipListSet concurrentSkipListSet=new ConcurrentSkipListSet();

    public RemoteMessaging(SQLiteDatabase create_Database_СамаБАзаSQLite) {
        Create_Database_СамаБАзаSQLite = create_Database_СамаБАзаSQLite;
    }


    // TODO: 14.08.2024

  public   Integer startingRemoteMessaging(){


        return Create_Database_СамаБАзаSQLite.getVersion();
    }


    public  void initWorkmanager(){
        RemoteWorkManager.getInstance()

    }

}
