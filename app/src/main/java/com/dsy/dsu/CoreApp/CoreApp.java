package com.dsy.dsu.CoreApp;


import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.dsy.dsu.BusinessLogicAll.DeviceName.ModulegetDeviceName;
import com.dsy.dsu.BusinessLogicAll.DeviceName.hilt.QualifiergetDeviceName;
import com.dsy.dsu.BusinessLogicAll.DeviceName.hilt.QualifiergetDeviceVersionBack;
import com.dsy.dsu.Errors.controller.RecordNewErros;
import com.dsy.dsu.Hilt.JbossAdrress.getHiltPortJbossInterface;
import com.dsy.dsu.Hilt.JbossAdrress.qualifiers.QualifierJbossServer3;
import com.sous.backasync.businesslogic.errors.RecordNewErroBack;
import com.sous.backasync.devicename.ModulegetDeviceNameBack;
import com.sous.backasync.launch.ModuleDeleting;
import com.sous.backasync.launch.ModuleInserting;
import com.sous.backasync.launch.ModuleQuety;
import com.sous.backasync.launch.ModuleUpdating;


import java.util.LinkedHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Inject;

import dagger.hilt.EntryPoints;
import dagger.hilt.android.HiltAndroidApp;


@HiltAndroidApp
public class CoreApp extends Application {

    @Inject
  public   SQLiteDatabase getSqlLiteCoreApp;

    @Inject
    CopyOnWriteArrayList<String> getWorkerTablesALl;

    @Inject
    ModuleQuety moduleQuety;

    @Inject
    ModuleInserting moduleInserting;

    @Inject
    ModuleUpdating moduleUpdating;

    @Inject
    RecordNewErros recordNewErros;


    @Inject
    RecordNewErroBack recordNewErroBack;


    @Inject
    ModuleDeleting moduleDeleting;



    @Inject
    @QualifiergetDeviceName
    public  String getDeviceName;






    @Inject
    @QualifiergetDeviceVersionBack
    public  Integer getDeviceVersion;

    @Inject
    ModulegetDeviceNameBack modulegetDeviceNameBack;

    @Inject
    @QualifierJbossServer3
    public LinkedHashMap<Integer,String> getHiltPortJboss;

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

            /*Cursor getbackasyncQueryandWhere=   moduleQuety.backasyncQueryandWhere("errordsu1",
                   " SELECT  *   FROM errordsu1 AS er  WHERE er.ERROR IS  NOT NULL  ORDER BY er.id DESC  "
                   ,null);*/


// TODO: 13.01.2025  Запускаем  
        //   startingBackAsync.backasyncQueryandWhere("errordsu1"," SELECT  *   FROM errordsu1  AS er WHERE er.Error=?  ORDER BY id DESC" ,new String[]{"IS  NOT NULL"});
           //startingBackAsync.backasyncQueryandWhere("fio"," SELECT  *   FROM fio   " ,null);
        //   startingBackAsync.backasyncQueryandWhere("errordsu1"," SELECT  *   FROM errordsu1 WHERE  id=? AND ERROR IS  NOT NULL  ORDER BY id DESC  " ,new String[]{"3"});
          /* startingBackAsync.backasyncQueryandWhere("errordsu1"," SELECT  *   FROM errordsu1  WHERE ERROR IS  NOT NULL  ORDER BY id DESC  " ,null);*/
          //  startingBackAsync.backasyncQueryandWhere("errordsu1"," SELECT  *   FROM errordsu1 AS er  WHERE er.ERROR IS  NOT NULL  ORDER BY er.id DESC  " ,null);
            //moduleQuety.backasyncQueryandWhere("errordsu1"," SELECT  *   FROM errordsu1 AS er  WHERE er.ERROR IS  NOT NULL  ORDER BY er.id DESC  " ,null);
        // TODO: 17.04.2023

            // TODO: 29.01.2025 insert

     // moduleInserting.getModuleInsert("error",new ContentValues());

      /*      String s=null;
            s.length();*/


        ///    recordNewErros.hashCode();

             /*    String s=null;
            s.length();
*/
     //Cursor cursor= moduleQuety.getModuleQueryForceLoad("errordsu1"," SELECT  *   FROM errordsu1 AS er  WHERE er.ERROR IS  NOT NULL  ORDER BY er.id DESC  " ,null);
            // TODO: 17.04.2023
         /* String getDeviceNameBack=  modulegetDeviceNameBack.getDeviceNameBack();
          Integer getDeviceVersionBack=  modulegetDeviceNameBack.getDeviceVersionBack();*/


    // Integer getde= moduleDeleting.getModuleDelete("errordsu1"," SELECT  *   FROM errordsu1 AS er  WHERE er.ERROR IS  NOT NULL  ORDER BY er.id DESC  " ,null);

          LinkedHashMap<Integer,String> getHiltPortJboss=   EntryPoints.get(getApplicationContext(), getHiltPortJbossInterface.class).getHiltPortJboss();


        Log.d(this.getClass().getName(),"\n" + " class CoreApp    " + Thread.currentThread().getStackTrace()[2].getClassName()
                + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                " sqlite " + getSqlLiteCoreApp );

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





