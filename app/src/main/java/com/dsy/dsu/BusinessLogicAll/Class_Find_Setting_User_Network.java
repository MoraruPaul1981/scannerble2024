package com.dsy.dsu.BusinessLogicAll;

import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dsy.dsu.AllDatabases.SQLTE.GetSQLiteDatabase;
import com.dsy.dsu.CnangeServers.PUBLIC_CONTENT;
import com.dsy.dsu.Errors.Class_Generation_Errors;


public class Class_Find_Setting_User_Network {

    Context context;
    /////


    private SQLiteDatabase sqLiteDatabase ;

    public Class_Find_Setting_User_Network(Context context) {

        this.context =context;

        sqLiteDatabase=    GetSQLiteDatabase.SqliteDatabase();
    }
    //функция получающая время операции ДАННАЯ ФУНКЦИЯ ВРЕМЯ ПРИМЕНЯЕТЬСЯ ВО ВСЕЙ


    public boolean МетодПроветяетКакуюУстановкуВыбралПользовательСети() {
        boolean getUsersingNeworktype = false;
        String РежимСетиВыбранныйПользователем = new String();
        try {

     РежимСетиВыбранныйПользователем = new Class_Type_Connenction_Tel(context).МетодОпределяемКакойТипПодключениеWIFIилиMobile();

     Class_GRUD_SQL_Operations operationNewrorUsesing = new Class_GRUD_SQL_Operations(context);

     operationNewrorUsesing.concurrentHashMapНабор.put("НазваниеОбрабоатываемойТаблицы", "SuccessLogin");
     ///////
     operationNewrorUsesing.concurrentHashMapНабор.put("СтолбцыОбработки", "mode_connection");

            PUBLIC_CONTENT Class_Engine_SQLГдеНаходитьсяМенеджерПотоков = new PUBLIC_CONTENT(context);

     SQLiteCursor getCursorNetworkUser = (SQLiteCursor) operationNewrorUsesing.
             new GetData(context).getdata(operationNewrorUsesing.
                     concurrentHashMapНабор,
             Class_Engine_SQLГдеНаходитьсяМенеджерПотоков.МенеджерПотоков,sqLiteDatabase);

     String setingNwtorkUser = new String();

            // TODO: 08.10.2024
            if (getCursorNetworkUser!=null) {
                if (getCursorNetworkUser.getCount() > 0) {
                    getCursorNetworkUser.moveToFirst();
                    setingNwtorkUser = getCursorNetworkUser.getString(0);

                    if (setingNwtorkUser.
                            equalsIgnoreCase(РежимСетиВыбранныйПользователем) ||
                            setingNwtorkUser.trim().equalsIgnoreCase("Mobile") ) {
                        // TODO: 29.09.2021  надо синхрониазциии
                        getUsersingNeworktype=true;
                    }else{
                        getUsersingNeworktype=false;
                    }
                } else {
                    Log.d(this.getClass().getName(), "getCursorNetworkUser " + getCursorNetworkUser);
                  getUsersingNeworktype=true;
                }
                }else {

                Log.d(this.getClass().getName(), "getCursorNetworkUser " + getCursorNetworkUser);
                getUsersingNeworktype=true;
            }

        } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
        return getUsersingNeworktype;
    }

}
