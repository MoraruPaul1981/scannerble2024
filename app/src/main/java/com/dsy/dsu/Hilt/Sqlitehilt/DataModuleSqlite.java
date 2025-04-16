
package com.dsy.dsu.Hilt.Sqlitehilt;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dsy.dsu.AllDatabases.SQLTE.GetSQLiteDatabase;
import com.dsy.dsu.Errors.WriteErrorForAll.RecordNewErros;

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
    public SQLiteDatabase metodHiltSqlite (@ApplicationContext Context context) {
        SQLiteDatabase getSQLites=null;
        try{

            getSQLites =  SQLiteDatabase.openDatabase("/data/user/0/com.dsy.dsu/databases/Database DSU-1.db",null, SQLiteDatabase.CREATE_IF_NECESSARY);
            if (getSQLites==null) {
              getSQLites=       new GetSQLiteDatabase(context).getSqliteDatabase();
            }
            // TODO: 17.04.2023
        Log.d(this.getClass().getName(),"\n" + " class FaceAPp "
                + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                " getSQLites.isOpen() " +getSQLites.isOpen());
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(context.getApplicationContext()).recordnewerror(e.toString(),
                this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
        return getSQLites;
    }


}
