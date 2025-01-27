package com.dsy.dsu.CoreApp;


import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.dsy.dsu.BusinessLogicAll.WorkerTables.HiltWorkerTable;
import com.dsy.dsu.BusinessLogicAll.WorkerTables.QualifierWorkerTable;
import com.dsy.dsu.BusinessLogicAll.WorkerTables.SubClassCreatingMainAllTables;
import com.dsy.dsu.Errors.controller.RecordNewErros;
import com.sous.backasync.hill.HiltWorkerTableBarckAync;
import com.sous.backasync.startingbackasync.StartingModuleBackAsync;

import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Inject;

import dagger.hilt.EntryPoints;
import dagger.hilt.android.HiltAndroidApp;


@HiltAndroidApp
public class CoreApp extends Application {

    @Inject
  public   SQLiteDatabase getSqlLiteCoreApp;


    @Inject
    public StartingModuleBackAsync startingModuleBackAsync;


    @Inject
    CopyOnWriteArrayList<String> getWorkerTablesALl;


    @Override
    public void onCreate() {
        super.onCreate();
        try{

          ///  getBinderAsync = EntryPoints.get(getApplicationContext(), HiltInterfaceBinderAsync.class).metodBinderAsync();
            // TODO: 02.09.2023  CREATE get SQLITE
        //    SQLiteDatabase getSQLites =   new GetSqlite().методGetSqlite(getApplicationContext());

                        // TODO: 29.08.2023  CREATE ROOM
      /// new CreateROOM(getApplicationContext()).метоInizROOM();

// Reference to the application graph that is used across the whole app

     // Cursor c= sqlite.rawQuery("select * from fio",null);


    // TODO: 13.01.2025  Запускаем
    //  startingBackAsync.startingBackAsync(getApplicationContext(), getSqlLiteCoreApp);

            // TODO: 17.01.2025
     /*       CopyOnWriteArrayList<String> getWorkerTablesALl=     EntryPoints.get(getApplicationContext(), HiltWorkerTable.class).getWorkerTablesALl();
            CopyOnWriteArrayList<String> getWorkerTablesALlBarckAync=     EntryPoints.get(getApplicationContext(), HiltWorkerTableBarckAync.class).getWorkerTablesALl();
*/

            Cursor getbackasyncQueryandWhere=   startingModuleBackAsync.backasyncQueryandWhere("errordsu1",
                   " SELECT  *   FROM errordsu1 AS er  WHERE er.ERROR IS  NOT NULL  ORDER BY er.id DESC  "
                   ,null);


// TODO: 13.01.2025  Запускаем  
        //   startingBackAsync.backasyncQueryandWhere("errordsu1"," SELECT  *   FROM errordsu1  AS er WHERE er.Error=?  ORDER BY id DESC" ,new String[]{"IS  NOT NULL"});
           //startingBackAsync.backasyncQueryandWhere("fio"," SELECT  *   FROM fio   " ,null);
        //   startingBackAsync.backasyncQueryandWhere("errordsu1"," SELECT  *   FROM errordsu1 WHERE  id=? AND ERROR IS  NOT NULL  ORDER BY id DESC  " ,new String[]{"3"});
          /* startingBackAsync.backasyncQueryandWhere("errordsu1"," SELECT  *   FROM errordsu1  WHERE ERROR IS  NOT NULL  ORDER BY id DESC  " ,null);*/
          //  startingBackAsync.backasyncQueryandWhere("errordsu1"," SELECT  *   FROM errordsu1 AS er  WHERE er.ERROR IS  NOT NULL  ORDER BY er.id DESC  " ,null);
            //startingModuleBackAsync.backasyncQueryandWhere("errordsu1"," SELECT  *   FROM errordsu1 AS er  WHERE er.ERROR IS  NOT NULL  ORDER BY er.id DESC  " ,null);
        // TODO: 17.04.2023
        Log.d(this.getClass().getName(),"\n" + " class CoreApp    " + Thread.currentThread().getStackTrace()[2].getClassName()
                + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                " sqlite " + getSqlLiteCoreApp);

    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }



}




// TODO: 29.08.2023  КЛАСС   БИЗНЕС ЛОГИКУ





