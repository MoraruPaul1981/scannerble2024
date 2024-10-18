package com.sous.server.datalayer.local;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.bl_preferences.BussenloginSaredPreferense;
import com.sous.server.datalayer.tirgers.TriggersGatt;

import org.jetbrains.annotations.NotNull;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;


//этот класс создает базу данных SQLite
public class CREATE_DATABASEServerScanner extends SQLiteOpenHelper{ ///SQLiteOpenHelper

   private   Context context;


    private static  AtomicReference<SQLiteDatabase> atomicstoredEntities = new AtomicReference<>();
   // private static     SQLiteDatabase ССылкаНаСозданнуюБазу;
    private static final int DATABASE_VERSION = 31;
    private Long version=0l;
    private SharedPreferences preferencesGatt;


    public static SQLiteDatabase getССылкаНаСозданнуюБазу() {
        System.out.println( "atomicstoredEntities "+atomicstoredEntities.toString());
        return atomicstoredEntities.get();
    }

    public CREATE_DATABASEServerScanner(@NotNull Context context) {/////КОНСТРУКТОР КЛАССА ПО СОЗДАНИЮ БАЗЫ ДАННЫХ
        super(context, "DatabaseScannerServer.db", null, DATABASE_VERSION ); // определяем имя базы данных  и ее версию
        try{
            this.context =context;
            if (atomicstoredEntities.get()==null) {
                atomicstoredEntities.set(this.getWritableDatabase());
            }
                Log.d(this.getClass().getName()," БАЗА  ДАННЫХ   ДСУ-1 ОТКРЫВАЕМ  ССылкаНаСозданнуюБазу==null   " +atomicstoredEntities);

            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.getLongVersionCode();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);

        }
    }
    //  Cоздание ТАблиц
    @Override
    public void onCreate(SQLiteDatabase ССылкаНаСозданнуюБазу) {
        try {
            preferencesGatt =context. getSharedPreferences("MyPrefs", MODE_PRIVATE);
            // TODO: 13.08.2024
            new BussenloginSaredPreferense(preferencesGatt,context,version).sharedPreferencesClear();


            // TODO: 27.09.2024  Удаление
            // TODO: 06.12.2022 удаление старых таблиц
            deletingunnecessarytables(ССылкаНаСозданнуюБазу);

            // TODO: 03.06.2022  таблиц
            МетодСозданиеТаблицError(ССылкаНаСозданнуюБазу);

            МетодСозданиеТаблицДляЗаписиСканирования(ССылкаНаСозданнуюБазу);


            МетодСозданиеТаблицДляСостыковкиФИОсостовная(ССылкаНаСозданнуюБазу);

            МетодСозданиеТаблицДляВерсияДанных(ССылкаНаСозданнуюБазу);

// TODO: 18.10.2024 создание тригеров
            МетодСозданиеТригеров(ССылкаНаСозданнуюБазу);

            // TODO: 03.06.2022
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }

    private   void deletingunnecessarytables(SQLiteDatabase ССылкаНаСозданнуюБазу) {
        // TODO: 27.09.2024
        ССылкаНаСозданнуюБазу.execSQL("drop table  if exists connectionmaсwithfullname ");//ТАБЛИЦА ГЕНЕРАЦИИ ОШИБОК
        // TODO: 03.06.2022
        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase ССылкаНаСозданнуюБазу, int oldVersion, int newVersion) {
        try{
            Log.d(this.getClass().getName(), " после СЛУЖБА  содание базы newVersion==  652   (например)   " + new Date() + " newVersion " + newVersion);
            if (newVersion > oldVersion) {
                   // TODO: 08.06.2021 создание Базы Данных

                switch (newVersion){
                    case 26 :
                        // TODO: 18.10.2024
                        МетодСозданиеТаблицДляСостыковкиФИОсостовная(ССылкаНаСозданнуюБазу);
                        Log.d(context.getClass().getName(), "\n" + " class " +
                                Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + " newVersion " + newVersion);
                        break;

                    case 29 :
                        // TODO: 18.10.2024
                        МетодСозданиеТаблицДляСостыковкиФИОсостовная(ССылкаНаСозданнуюБазу);
                        // TODO: 18.10.2024
                        МетодСозданиеТаблицДляВерсияДанных(ССылкаНаСозданнуюБазу);

                        // TODO: 18.10.2024 создание тригеров
                        МетодСозданиеТригеров(ССылкаНаСозданнуюБазу);


                        Log.d(context.getClass().getName(), "\n" + " class " +
                                Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + " newVersion " + newVersion);
                        break;

                    default:
                        onCreate(ССылкаНаСозданнуюБазу);
                        // TODO: 18.10.2024

                        Log.d(context.getClass().getName(), "\n" + " class " +
                                Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + " newVersion " + newVersion);
                        break;

                }




                Log.d(context.getClass().getName(), "\n" + " class " +
                        Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + " newVersion " + newVersion);
               }
            Log.i(this.getClass().getName(), "  Создана/Изменена База Данных !!! "+new Date());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }
    @Override
    public void onDowngrade(SQLiteDatabase ССылкаНаСозданнуюБазу, int oldVersion, int newVersion) {
        onCreate(ССылкаНаСозданнуюБазу);
    }





    private void МетодСозданиеТаблицError(@NotNull SQLiteDatabase ССылкаНаСозданнуюБазу) {//BEFORE   INSERT , UPDATE , DELETE
        try{
            // TODO: 06.12.2022 удаление старых таблиц
            ССылкаНаСозданнуюБазу.execSQL("drop table  if exists errordsu1 ");//ТАБЛИЦА ГЕНЕРАЦИИ ОШИБОК
            // TODO: 30.11.2022 создаени таблицы ошибок
            ССылкаНаСозданнуюБазу.execSQL("drop table  if exists errordsu1 ");//ТАБЛИЦА ГЕНЕРАЦИИ ОШИБОК
            ССылкаНаСозданнуюБазу.execSQL("Create table if not exists errordsu1 (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT  ," +
                    " Error TEXT      ," +
                    "Klass TEXT  ," +
                    "Metod TEXT ," +
                    "LineError INTEGER ," +
                    "date_update NUMERIC ," +
                    "user_update INTEGER , " +
                    "current_table NUMERIC ,  " +
                    " whose_error INT ," +
                    " uuid  NUMERIC  )");
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }


    private void МетодСозданиеТаблицДляЗаписиСканирования(@NotNull SQLiteDatabase ССылкаНаСозданнуюБазу) {//BEFORE   INSERT , UPDATE , DELETE
        try{
                        ССылкаНаСозданнуюБазу.execSQL("drop table  if exists   "+"scannerserversuccess"+"");//test
                        ССылкаНаСозданнуюБазу.execSQL("Create table if not exists   "+"scannerserversuccess"+" (" +
                                "id  INTEGER  PRIMARY KEY AUTOINCREMENT    ," +
                                " operations  TEXT ," +
                                " completedwork TEXT  ," +
                                " namedevice TEXT  ," +
                                " macdevice TEXT ," +
                                " gps1  NUMERIC ," +
                                " gps2  NUMERIC ," +
                                " fio TEXT ," +
                                " adress TEXT ," +
                                " city TEXT ," +
                                " date_update NUMERIC   ," +
                                " uuid  NUMERIC UNIQUE DEFAULT 0  ,"+
                                " version  NUMERIC ," +
                                " sim  INTEGER ," +
                                " iemi  TEXT ," +
                                " current_table   NUMERIC UNIQUE DEFAULT 0,"+
                                " getstatusrow   INT )");
                        Log.d(this.getClass().getName(), " сработала ...  создание таблицы   НазваниеТаблицыДляТригера   "+"scannerserversuccess" );
                        //TODO INSERT

                        // TODO: 03.06.2022
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
                    // TODO: 30.11.2022 Тригеры для Сканироваение
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }

    private void МетодСозданиеТаблицДляВерсияДанных(@NotNull SQLiteDatabase ССылкаНаСозданнуюБазу) {//BEFORE   INSERT , UPDATE , DELETE
        try{
                    ССылкаНаСозданнуюБазу.execSQL("drop table  if exists   "+"gattserverdataversion"+"");//test
                    ССылкаНаСозданнуюБазу.execSQL("Create table if not exists   "+"gattserverdataversion"+" (" +
                            " id  INTEGER  PRIMARY KEY AUTOINCREMENT    ," +
                            "versionlocal NUMERIC ," +
                            "versionremote NUMERIC  ," +
                            "date_update TEXT ," +
                            "date_updatelocal TEXT  )");
                    Log.d(this.getClass().getName(), " сработала ...  создание таблицы   НазваниеТаблицыДляТригера   "+"scannerserversuccess" );
                    //TODO INSERT
            ССылкаНаСозданнуюБазу.execSQL("INSERT INTO gattserverdataversion  (id,versionlocal,versionremote) VALUES('1','0','0');");//test


                    // TODO: 03.06.2022
                    Log.d(this.getClass().getName(), " сработала ...  создание таблицы   NOT EXISTS ScannerTableINSERT   "+"scannerserversuccess" );
                    //TODO INSERT
                   /*     ССылкаНаСозданнуюБазу.execSQL("drop TRIGGER  if exists   ScannerTableINSERT ");//test
                        ССылкаНаСозданнуюБазу.execSQL(" CREATE TRIGGER IF NOT EXISTS ScannerTableINSERT" + НазваниеТаблицыДляТригера + "" +
                                "  AFTER INSERT   ON " + НазваниеТаблицыДляТригера +
                                " BEGIN " +
                                 " UPDATE "+НазваниеТаблицыДляТригера+" SET  current_table= (SELECT MAX(current_table)+1 FROM  " + НазваниеТаблицыДляТригера + ")" +
                                " WHERE  current_table  in( SELECT current_table  FROM "+НазваниеТаблицыДляТригера+" ORDER BY current_table DESC LIMIT 1)  " + "; "
                                + " END ;");//test*/
                    // TODO: 03.06.2022
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }



    private void МетодСозданиеТаблицДляСостыковкиФИОсостовная(@NotNull SQLiteDatabase ССылкаНаСозданнуюБазу) {//BEFORE   INSERT , UPDATE , DELETE
        try{
            ССылкаНаСозданнуюБазу.execSQL("drop table  if exists   "+"completeallmacadressusers"+"");//test
            ССылкаНаСозданнуюБазу.execSQL("Create table if not exists   "+"completeallmacadressusers"+" (" +
                    " id  INTEGER  PRIMARY KEY AUTOINCREMENT      ," +
                    " fio  TEXT ," +
                    " mac TEXT  ," +
                    " date_update NUMERIC   ," +
                    " current_table   NUMERIC UNIQUE DEFAULT 0 ,"+
                    " uuid  NUMERIC UNIQUE DEFAULT 0 ) ");
            Log.d(this.getClass().getName(), " сработала ...  создание таблицы   НазваниеТаблицыДляТригера   "+"scannerserversuccess" );
            //TODO INSERT

            // TODO: 03.06.2022
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
            // TODO: 30.11.2022 Тригеры для Сканироваение
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }


    private void МетодСозданиеТригеров(@NotNull SQLiteDatabase ССылкаНаСозданнуюБазу) {//BEFORE   INSERT , UPDATE , DELETE
        try{


            // TODO: 18.10.2024  запускаем создание тригеров для gatt server
          new TriggersGatt(context).triggersGatt(version,   ССылкаНаСозданнуюБазу);

            // TODO: 03.06.2022
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
            // TODO: 30.11.2022 Тригеры для Сканироваение
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }


    // TODO: 11.09.2024 END classs

}// todo конец класса для создание таблицы для Scanner



































































































