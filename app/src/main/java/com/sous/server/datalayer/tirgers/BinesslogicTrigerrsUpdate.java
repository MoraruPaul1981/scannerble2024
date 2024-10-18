package com.sous.server.datalayer.tirgers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sous.server.businesslayer.Errors.SubClassErrors;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

public class BinesslogicTrigerrsUpdate implements  TrigersCreateIn  {

   private SQLiteDatabase ССылкаНаСозданнуюБазу=null;
    public BinesslogicTrigerrsUpdate(@NotNull   SQLiteDatabase  ССылкаНаСозданнуюБазу) {
        this.ССылкаНаСозданнуюБазу=ССылкаНаСозданнуюБазу;
    }

    @Override
    public void triggergeneration(@NotNull Context context,@NotNull  Long  version) {
     try{

         // TODO: 29.08.2023 UPDATE TRIGGER
         ССылкаНаСозданнуюБазу.execSQL(" drop TRIGGER  if exists   trigger_updatedate_update_gattserverdataversion ");
         ССылкаНаСозданнуюБазу.execSQL("    CREATE TRIGGER  IF NOT EXISTS  trigger_updatedate_update_gattserverdataversion AFTER UPDATE  \n" +
                 "ON completeallmacadressusers \n" +
                 "BEGIN\n" +
                 "  UPDATE  gattserverdataversion  SET  date_updatelocal=(SELECT MAX(date_update)  FROM completeallmacadressusers )  WHERE id=1 ;"+
                 "END; ");

         ССылкаНаСозданнуюБазу.execSQL(" drop TRIGGER  if exists   trigger_updatecurrent_table_gattserverdataversion ");
         ССылкаНаСозданнуюБазу.execSQL("    CREATE TRIGGER  IF NOT EXISTS  trigger_updatecurrent_table_gattserverdataversion  AFTER UPDATE  \n" +
                 "ON completeallmacadressusers \n" +
                 "BEGIN\n" +
                 "  UPDATE  gattserverdataversion  SET  versionlocal=(SELECT MAX(current_table)  FROM completeallmacadressusers ) WHERE id=1 ;"+
                 "END; ");

         // TODO: 25.08.2024
    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

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
}
