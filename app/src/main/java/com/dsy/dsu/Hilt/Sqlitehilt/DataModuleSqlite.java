
package com.dsy.dsu.Hilt.Sqlitehilt;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dsy.dsu.AllDatabases.SQLTE.GetSQLiteDatabase;
import com.dsy.dsu.Errors.WriteErrorForAll.RecordNewErros;
import com.google.common.util.concurrent.AtomicDouble;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@QualifiertEnd
@Module
@InstallIn(SingletonComponent.class)
public class DataModuleSqlite {
    @Singleton
    @Provides
public SQLiteDatabase metodHiltSqlite (@ApplicationContext Context context){
       AtomicReference<SQLiteDatabase>  getSQLites=new AtomicReference<>();
        try{
          File fileDatabeseOpenParametrs = new File("/data/user/0/com.dsy.dsu/databases", "Database DSU-1.db");

  if(fileDatabeseOpenParametrs.exists()){
      //getSQLites =  SQLiteDatabase.openDatabase("/data/user/0/com.dsy.dsu/databases/Database DSU-1.db",null, SQLiteDatabase.CREATE_IF_NECESSARY);
      getSQLites.getAndSet( SQLiteDatabase.openDatabase(fileDatabeseOpenParametrs.getAbsolutePath(),null,
              SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY));
      // TODO: 16.04.2025
      if (!getSQLites.get().isOpen()) {
          GetSQLiteDatabase getSQLiteDatabase=new GetSQLiteDatabase(context);
          getSQLites.getAndSet(       getSQLiteDatabase.getSqliteDatabase());
      }

  }else {
         GetSQLiteDatabase getSQLiteDatabase=new GetSQLiteDatabase(context);
      getSQLites.getAndSet(        getSQLiteDatabase.getSqliteDatabase());
  }

            // TODO: 17.04.2023
        Log.d(this.getClass().getName(),"\n" + " class FaceAPp "
                + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                " getSQLites.get().isOpen() " +getSQLites.get().isOpen());
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(context.getApplicationContext()).recordnewerror(e.toString(),
                this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
        return getSQLites.get();
    }


}
