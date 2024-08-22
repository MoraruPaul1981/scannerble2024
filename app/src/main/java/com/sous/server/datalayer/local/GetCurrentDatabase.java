package com.sous.server.datalayer.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sous.server.businesslayer.Errors.SubClassErrors;

public class GetCurrentDatabase {


private Context context;

private  long version;

    public GetCurrentDatabase(Context context, long version) {
        this.context = context;
        this.version = version;
    }


    public SQLiteDatabase initingCurrentDatabase() {
        SQLiteDatabase Create_Database_СамаБАзаSQLite=null;
        try {

            Create_Database_СамаБАзаSQLite=new CREATE_DATABASEServerScanner(context).getССылкаНаСозданнуюБазу();

            // CREATE_DATABASEServerScanner createDatabaseServerScanner= new CREATE_DATABASEServerScanner(context);
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber()
                    + "\n"  + " Create_Database_СамаБАзаSQLite " +Create_Database_СамаБАзаSQLite);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return  Create_Database_СамаБАзаSQLite;
    }



}
